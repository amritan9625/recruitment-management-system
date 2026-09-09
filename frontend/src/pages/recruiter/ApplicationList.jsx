import { useEffect, useState } from "react";
import {
  getAllApplications,
  getApplicationById,
  updateApplication,
  deleteApplication,
  updateApplicationStatus,
  getApplicationsByCandidateId,
  getApplicationsByJobId,
  getApplicationsByStatus,
  getApplicationsByCandidateName,
  getApplicationsByJobTitle,
} from "../../services/applicationService";
import ApplicationForm from "./ApplicationForm";
import ApplicationDetails from "./ApplicationDetails";

function ApplicationList() {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [viewingApplicationId, setViewingApplicationId] = useState(null);
  const [editingApplication, setEditingApplication] = useState(null);

  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [sortBy, setSortBy] = useState("id");
  const [sortDir, setSortDir] = useState("asc");

  const [candidateIdFilter, setCandidateIdFilter] = useState("");
  const [jobIdFilter, setJobIdFilter] = useState("");
  const [statusFilter, setStatusFilter] = useState("");

  const [candidateNameSearch, setCandidateNameSearch] = useState("");
  const [submittedCandidateName, setSubmittedCandidateName] = useState("");

  const [jobTitleSearch, setJobTitleSearch] = useState("");
  const [submittedJobTitle, setSubmittedJobTitle] = useState("");

  const fetchApplications = async () => {
    try {
      setLoading(true);
      setError("");

      let response;

      if (submittedCandidateName) {
        response = await getApplicationsByCandidateName(
          candidateNameSearch.trim(),
          pageNo,
          pageSize,
        );
      } else if (submittedJobTitle) {
        response = await getApplicationsByJobTitle(
          jobTitleSearch.trim(),
          pageNo,
          pageSize,
        );
      } else if (candidateIdFilter) {
        response = await getApplicationsByCandidateId(
          candidateIdFilter,
          pageNo,
          pageSize,
        );
      } else if (jobIdFilter) {
        response = await getApplicationsByJobId(jobIdFilter, pageNo, pageSize);
      } else if (statusFilter) {
        response = await getApplicationsByStatus(
          statusFilter,
          pageNo,
          pageSize,
        );
      } else {
        response = await getAllApplications(pageNo, pageSize, sortBy, sortDir);
      }

      setApplications(response.data?.content || []);
      setTotalPages(response.data?.totalPages || 0);
      setTotalElements(response.data?.totalElements || 0);
    } catch (err) {
      setError(err.message || "Failed to load applications.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchApplications();
  }, [
    pageNo,
    pageSize,
    sortBy,
    sortDir,
    candidateIdFilter,
    jobIdFilter,
    statusFilter,
    submittedCandidateName,
    submittedJobTitle,
  ]);

  const handleEdit = async (id) => {
    try {
      setError("");

      const response = await getApplicationById(id);

      setEditingApplication(response.data);
      setShowForm(true);
    } catch (err) {
      setError(err.message || "Failed to load application details.");
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this application?",
    );

    if (!confirmed) {
      return;
    }

    try {
      setError("");

      await deleteApplication(id);

      await fetchApplications();
    } catch (err) {
      setError(err.message || "Failed to delete application.");
    }
  };

  const handleStatusChange = async (id, status) => {
    try {
      setError("");

      await updateApplicationStatus(id, status);

      await fetchApplications();
    } catch (err) {
      setError(err.message || "Failed to update application status.");
    }
  };

  if (loading) {
    return (
      <div className="application-page">
        <h1>Applications</h1>
        <p>Loading applications...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="application-page">
        <h1>Applications</h1>

        <p className="application-error">{error}</p>
      </div>
    );
  }

  if (viewingApplicationId) {
    return (
      <ApplicationDetails
        applicationId={viewingApplicationId}
        onBack={() => setViewingApplicationId(null)}
      />
    );
  }

  return (
    <div className="application-page">
      <div className="application-page-header">
        <div>
          <h1>Applications</h1>

          <p>Manage job applications in the recruitment system.</p>
        </div>

        <button
          className="application-add-button"
          onClick={() => {
            setEditingApplication(null);
            setShowForm(true);
          }}
        >
          Add Application
        </button>
      </div>

      {showForm && (
        <ApplicationForm
          application={editingApplication}
          onSuccess={() => {
            setShowForm(false);
            setEditingApplication(null);
            fetchApplications();
          }}
          onCancel={() => {
            setShowForm(false);
            setEditingApplication(null);
          }}
        />
      )}

      {applications.length === 0 ? (
        <div className="application-empty">No applications found.</div>
      ) : (
        <div className="application-table-container">
          {/* filter */}
          <div className="application-filters">
            {/* search candidate input */}
            <input
              type="text"
              placeholder="Search candidate name..."
              value={candidateNameSearch}
              onChange={(e) => {
                setCandidateNameSearch(e.target.value);
              }}
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  setPageNo(0);
                  setSubmittedCandidateName(candidateNameSearch.trim());
                }
              }}
            />

            <button
              type="button"
              onClick={() => {
                setPageNo(0);
                setSubmittedCandidateName(candidateNameSearch.trim());
              }}
            >
              Search
            </button>

            {/* Search job title input */}
            <input
              type="text"
              placeholder="Search job title..."
              value={jobTitleSearch}
              onChange={(e) => {
                setJobTitleSearch(e.target.value);
              }}
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  setPageNo(0);
                  setSubmittedJobTitle(jobTitleSearch.trim());
                }
              }}
            />

            <button
              type="button"
              onClick={() => {
                setPageNo(0);
                setSubmittedJobTitle(jobTitleSearch.trim());
              }}
            >
              Search
            </button>

            <input
              type="number"
              placeholder="Candidate ID"
              value={candidateIdFilter}
              onChange={(e) => {
                setCandidateIdFilter(e.target.value);
                setPageNo(0);
              }}
            />

            <input
              type="number"
              placeholder="Job ID"
              value={jobIdFilter}
              onChange={(e) => {
                setJobIdFilter(e.target.value);
                setPageNo(0);
              }}
            />

            <select
              value={statusFilter}
              onChange={(e) => {
                setStatusFilter(e.target.value);
                setPageNo(0);
              }}
            >
              <option value="">All Statuses</option>
              <option value="APPLIED">APPLIED</option>
              <option value="SHORTLISTED">SHORTLISTED</option>
              <option value="INTERVIEW_SCHEDULED">INTERVIEW_SCHEDULED</option>
              <option value="SELECTED">SELECTED</option>
              <option value="REJECTED">REJECTED</option>
            </select>

            <button
              type="button"
              onClick={() => {
                setCandidateNameSearch("");
                setSubmittedCandidateName("");

                setJobTitleSearch("");
                setSubmittedJobTitle("");

                setCandidateIdFilter("");
                setJobIdFilter("");
                setStatusFilter("");
                
                setPageNo(0);
              }}
            >
              Reset
            </button>
          </div>

          {/* sorting */}
          <div className="application-sorting">
            <label>Sort by: </label>

            <select
              value={sortBy}
              onChange={(e) => {
                setSortBy(e.target.value);
                setPageNo(0);
              }}
            >
              <option value="id">ID</option>
              <option value="candidateId">Candidate ID</option>
              <option value="jobId">Job ID</option>
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

          <table className="application-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Candidate ID</th>
                <th>Job ID</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {applications.map((application) => (
                <tr key={application.id}>
                  <td>{application.id}</td>

                  <td>{application.candidateId}</td>

                  <td>{application.jobId}</td>

                  <td>
                    <select
                      value={application.status}
                      onChange={(event) =>
                        handleStatusChange(application.id, event.target.value)
                      }
                    >
                      <option value="APPLIED">APPLIED</option>

                      <option value="SHORTLISTED">SHORTLISTED</option>

                      <option value="INTERVIEW_SCHEDULED">
                        INTERVIEW SCHEDULED
                      </option>

                      <option value="SELECTED">SELECTED</option>

                      <option value="REJECTED">REJECTED</option>
                    </select>
                  </td>

                  <td>
                    <button
                      onClick={() => setViewingApplicationId(application.id)}
                    >
                      View
                    </button>

                    <button onClick={() => handleEdit(application.id)}>
                      Edit
                    </button>

                    <button onClick={() => handleDelete(application.id)}>
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
      <div className="application-pagination">
        <div className="application-page-size">
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

        <span className="application-total-records">
          Total Applications: {totalElements}
        </span>

        <div className="application-page-navigation">
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

export default ApplicationList;
