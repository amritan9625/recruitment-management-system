import { useEffect, useState } from "react";
import {
  getAllJobs,
  getJobById,
  deleteJob,
  updateJob,
  getJobsByStatus,
  getJobsByLocation,
} from "../../services/jobService";
import JobForm from "./JobForm";
import JobDetails from "./JobDetails";
import Pagination from "../../components/Pagination";
import SearchFilterBar from "../../components/SearchFilterBar";
import { useAuth } from "../../context/AuthContext";
import { ROLES } from "../../utils/permissions";

function JobList() {
  const { role } = useAuth();
  const canManageJobs = role === ROLES.ADMIN || role === ROLES.RECRUITER;

  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [editingJob, setEditingJob] = useState(null);
  const [viewingJobId, setViewingJobId] = useState(null);

  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [sortBy, setSortBy] = useState("id");
  const [sortDir, setSortDir] = useState("asc");

  const [locationFilter, setLocationFilter] = useState("");
  const [submittedLocationFilter, setSubmittedLocationFilter] = useState("");

  const [statusFilter, setStatusFilter] = useState("");

  const fetchJobs = async () => {
    try {
      setLoading(true);
      setError("");

      let response;

      if (submittedLocationFilter) {
        response = await getJobsByLocation(
          submittedLocationFilter,
          pageNo,
          pageSize,
        );
      } else if (statusFilter) {
        response = await getJobsByStatus(statusFilter, pageNo, pageSize);
      } else {
        response = await getAllJobs(pageNo, pageSize, sortBy, sortDir);
      }

      setJobs(response.data?.content || []);
      setTotalPages(response.data?.totalPages || 0);
      setTotalElements(response.data?.totalElements || 0);
    } catch (err) {
      setError(err.message || "Failed to load jobs.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchJobs();
  }, [
    pageNo,
    pageSize,
    sortBy,
    sortDir,
    submittedLocationFilter,
    statusFilter,
  ]);

  const handleLocationSearch = () => {
    setPageNo(0);
    setSubmittedLocationFilter(locationFilter.trim());
  };

  const handleReset = () => {
    setLocationFilter("");
    setSubmittedLocationFilter("");

    setStatusFilter("");

    setSortBy("id");
    setSortDir("asc");

    setPageNo(0);
  };

  const handleStatusFilterChange = (event) => {
    setPageNo(0);
    setStatusFilter(event.target.value);
  };

  const handleSortChange = (event) => {
    setPageNo(0);
    setSortBy(event.target.value);
  };

  const handleSortDirectionChange = (event) => {
    setPageNo(0);
    setSortDir(event.target.value);
  };

  const handlePageSizeChange = (size) => {
    setPageNo(0);
    setPageSize(size);
  };

  const handleEdit = async (id) => {
    try {
      setError("");

      const response = await getJobById(id);

      setEditingJob(response.data);
      setShowForm(true);
    } catch (err) {
      setError(err.message || "Failed to load job details.");
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this job?",
    );
    if (!confirmed) return;

    try {
      setError("");
      await deleteJob(id);
      await fetchJobs();
    } catch (err) {
      setError(err.message || "Failed to delete job.");
    }
  };

  const handleStatusChange = async (job, status) => {
    try {
      setError("");

      const jobData = {
        title: job.title,
        description: job.description,
        location: job.location,
        salary: job.salary,
        jobType: job.jobType,
        status,
      };

      await updateJob(job.id, jobData);

      await fetchJobs();
    } catch (err) {
      setError(err.message || "Failed to update job status.");
    }
  };

  if (viewingJobId) {
    return (
      <JobDetails jobId={viewingJobId} onBack={() => setViewingJobId(null)} />
    );
  }

  if (loading) {
    return (
      <div className="job-page">
        <h1>Jobs</h1>
        <p>Loading jobs...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="job-page">
        <h1>Jobs</h1>
        <p className="job-error">{error}</p>
      </div>
    );
  }

  return (
    <div className="job-page">
      <div className="job-page-header">
        <div>
          <h1>Jobs</h1>
          <p>Manage jobs in the recruitment system.</p>
        </div>

        {canManageJobs && (
          <button
            className="job-add-button"
            onClick={() => {
              setEditingJob(null);
              setShowForm(true);
            }}
          >
            Add Job
          </button>
        )}
      </div>

      {showForm && (
        <JobForm
          job={editingJob}
          onSuccess={() => {
            setShowForm(false);
            setEditingJob(null);
            fetchJobs();
          }}
          onCancel={() => {
            setShowForm(false);
            setEditingJob(null);
          }}
        />
      )}

      {/* Search + Filter + Sort */}
      <SearchFilterBar onReset={handleReset}>
        <div>
          <input
            type="text"
            placeholder="Search by location..."
            value={locationFilter}
            onChange={(event) => {
              setLocationFilter(event.target.value);
            }}
            onKeyDown={(event) => {
              if (event.key === "Enter") {
                handleLocationSearch();
              }
            }}
          />

          <button type="button" onClick={handleLocationSearch}>
            Search
          </button>
        </div>

        <select value={statusFilter} onChange={handleStatusFilterChange}>
          <option value="">All Statuses</option>
          <option value="OPEN">OPEN</option>
          <option value="CLOSED">CLOSED</option>
        </select>

        <select value={sortBy} onChange={handleSortChange}>
          <option value="id">ID</option>
          <option value="title">Title</option>
          <option value="location">Location</option>
          <option value="salary">Salary</option>
          <option value="jobType">Job Type</option>
          <option value="status">Status</option>
        </select>

        <select value={sortDir} onChange={handleSortDirectionChange}>
          <option value="asc">Ascending</option>
          <option value="desc">Descending</option>
        </select>
      </SearchFilterBar>

      {jobs.length === 0 ? (
        <div className="job-empty">No jobs found.</div>
      ) : (
        <>
          <div className="job-table-container">
            <table className="job-table">
              <thead>
                <tr>
                  <th>Title</th>
                  <th>Description</th>
                  <th>Location</th>
                  <th>Salary</th>
                  <th>Job Type</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {jobs.map((job) => (
                  <tr key={job.id}>
                    <td>{job.title}</td>

                    <td>{job.description}</td>

                    <td>{job.location}</td>

                    <td>{job.salary}</td>

                    <td>{job.jobType}</td>

                    <td>
                      {canManageJobs ? (
                        <select
                          value={job.status || "OPEN"}
                          onChange={(event) =>
                            handleStatusChange(job, event.target.value)
                          }
                        >
                          <option value="OPEN">OPEN</option>
                          <option value="CLOSED">CLOSED</option>
                        </select>
                      ) : (
                        job.status
                      )}
                    </td>

                    <td>
                      <button onClick={() => setViewingJobId(job.id)}>
                        View
                      </button>

                      {canManageJobs && (
                        <>
                          <button onClick={() => handleEdit(job.id)}>
                            Edit
                          </button>

                          <button onClick={() => handleDelete(job.id)}>
                            Delete
                          </button>
                        </>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <Pagination
            pageNo={pageNo}
            pageSize={pageSize}
            totalPages={totalPages}
            totalElements={totalElements}
            onPageChange={setPageNo}
            onPageSizeChange={handlePageSizeChange}
          />
        </>
      )}
    </div>
  );
}

export default JobList;
