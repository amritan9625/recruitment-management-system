import { useEffect, useState } from "react";
import {
  getAllJobs,
  getJobById,
  deleteJob,
  getJobsByLocation,
  getJobsByStatus,
  updateJob,
} from "../../services/jobService";
import JobForm from "./JobForm";
import JobDetails from "./JobDetails";

function JobList() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [editingJob, setEditingJob] = useState(null);
  const [viewingJobId, setViewingJobId] = useState(null);
  const [updatingStatusId, setUpdatingStatusId] = useState(null);

  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [sortBy, setSortBy] = useState("id");
  const [sortDir, setSortDir] = useState("asc");

  const [statusFilter, setStatusFilter] = useState("");
  const [locationFilter, setLocationFilter] = useState("");
  const [submittedLocationFilter, setSubmittedLocationFilter] = useState("");

  const fetchJobs = async () => {
    try {
      setLoading(true);
      setError("");

      let response;

      if (statusFilter) {
        response = await getJobsByStatus(statusFilter, pageNo, pageSize);
      } else if (submittedLocationFilter) {
        response = await getJobsByLocation(
          submittedLocationFilter,
          pageNo,
          pageSize,
        );
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

  useEffect(() => {
    fetchJobs();
  }, [pageNo, pageSize, sortBy, sortDir, statusFilter, submittedLocationFilter]);

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

  const handleStatusChange = async (job, status) => {
    try {
      setError("");
      setUpdatingStatusId(job.id);

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
    } finally {
      setUpdatingStatusId(null);
    }
  };

  return (
    <div className="job-page">
      <div className="job-page-header">
        <div>
          <h1>Jobs</h1>
          <p>Manage jobs in the recruitment system.</p>
        </div>

        <button className="job-add-button" onClick={() => setShowForm(true)}>
          Add Job
        </button>
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

      {jobs.length === 0 ? (
        <div className="job-empty">No jobs found.</div>
      ) : (
        <div className="job-table-container">
          {/* filter */}
          <div className="job-filters">
            <input
              type="text"
              placeholder="Filter by location..."
              value={locationFilter}
              onChange={(e) => {
                setLocationFilter(e.target.value);
              }}
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  setPageNo(0);
                  setSubmittedLocationFilter(locationFilter.trim());
                }
              }}
            />

            <button
              type="button"
              onClick={() => {
                setPageNo(0);
                setSubmittedLocationFilter(locationFilter.trim());
              }}
            >
              Search
            </button>

            <select
              value={statusFilter}
              onChange={(e) => {
                setStatusFilter(e.target.value);
                setPageNo(0);
              }}
            >
              <option value="">All Statuses</option>
              <option value="OPEN">OPEN</option>
              <option value="CLOSED">CLOSED</option>
            </select>

            <button
              type="button"
              onClick={() => {
                setLocationFilter("");
                setSubmittedLocationFilter("");
                setStatusFilter("");
                setPageNo(0);
              }}
            >
              Reset
            </button>
          </div>

          {/* sorting */}
          <div className="job-sorting">
            <label>Sort by: </label>

            <select
              value={sortBy}
              onChange={(e) => {
                setSortBy(e.target.value);
                setPageNo(0);
              }}
            >
              <option value="id">ID</option>
              <option value="title">Title</option>
              <option value="location">Location</option>
              <option value="salary">Salary</option>
              <option value="jobType">Job Type</option>
              <option value="status">Status</option>
            </select>

            <select
              value={sortDir}
              onChange={(e) => {
                setSortDir(e.target.value);
                setPageNo(0);
              }}
            >
              <option value="asc">Ascending</option>
              <option value="desc">Descending</option>
            </select>
          </div>

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
                    <select
                      value={job.status || "OPEN"}
                      onChange={(event) =>
                        handleStatusChange(job, event.target.value)
                      }
                      disabled={updatingStatusId === job.id}
                    >
                      <option value="OPEN">OPEN</option>
                      <option value="CLOSED">CLOSED</option>
                    </select>
                  </td>

                  <td>
                    <button onClick={() => setViewingJobId(job.id)}>
                      View
                    </button>

                    <button onClick={() => handleEdit(job.id)}>Edit</button>

                    <button onClick={() => handleDelete(job.id)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
      <div className="job-pagination">
        <div className="job-page-size">
          <label>Rows per page: </label>

          <select
            value={pageSize}
            onChange={(e) => {
              setPageSize(Number(e.target.value));
              setPageNo(0);
            }}
          >
            <option value={5}>5</option>
            <option value={10}>10</option>
            <option value={20}>20</option>
          </select>
        </div>

        <span className="job-total-records">Total Jobs: {totalElements}</span>

        <div className="job-page-navigation">
          <button
            onClick={() => setPageNo((prev) => prev - 1)}
            disabled={pageNo === 0}
          >
            Previous
          </button>

          <span>
            Page {pageNo + 1} of {totalPages}
          </span>

          <button
            onClick={() => setPageNo((prev) => prev + 1)}
            disabled={pageNo >= totalPages - 1 || totalPages === 0}
          >
            Next
          </button>
        </div>
      </div>
    </div>
  );
}

export default JobList;
