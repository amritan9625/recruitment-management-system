import { useEffect, useState } from "react";
import {
  getAllApplications,
  getMyApplications,
  getApplicationById,
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
import Pagination from "../../components/Pagination";
import SearchFilterBar from "../../components/SearchFilterBar";
import { useAuth } from "../../context/AuthContext";
import { ROLES } from "../../utils/permissions";

function ApplicationList() {
  const { role } = useAuth();
  const canManageApplications =
    role === ROLES.ADMIN || role === ROLES.RECRUITER;
  const isCandidate = role === ROLES.CANDIDATE;

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

  const [candidateId, setCandidateId] = useState("");
  const [jobId, setJobId] = useState("");
  const [statusFilter, setStatusFilter] = useState("");

  const [candidateName, setCandidateName] = useState("");
  const [submittedCandidateName, setSubmittedCandidateName] = useState("");

  const [jobTitle, setJobTitle] = useState("");
  const [submittedJobTitle, setSubmittedJobTitle] = useState("");

  const fetchApplications = async () => {
    try {
      setLoading(true);
      setError("");

      let response;
      //  Search/filter priority:
      //  1. Candidate name
      //  2. Job title
      //  3. Candidate ID
      //  4. Job ID
      //  5. Status
      //  6. Normal list

      if (isCandidate) {
        response = await getMyApplications(pageNo, pageSize);
      } else if (submittedCandidateName.trim()) {
        response = await getApplicationsByCandidateName(
          submittedCandidateName.trim(),
          pageNo,
          pageSize,
        );
      } else if (submittedJobTitle.trim()) {
        response = await getApplicationsByJobTitle(
          submittedJobTitle.trim(),
          pageNo,
          pageSize,
        );
      } else if (candidateId.trim()) {
        response = await getApplicationsByCandidateId(
          candidateId.trim(),
          pageNo,
          pageSize,
        );
      } else if (jobId.trim()) {
        response = await getApplicationsByJobId(jobId.trim(), pageNo, pageSize);
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
    candidateId,
    jobId,
    statusFilter,
    submittedCandidateName,
    submittedJobTitle,
  ]);

  const handleCandidateNameSearch = () => {
    setPageNo(0);
    setSubmittedCandidateName(candidateName);
    setSubmittedJobTitle("");
    setCandidateId("");
    setJobId("");
    setStatusFilter("");
  };

  const handleJobTitleSearch = () => {
    setPageNo(0);
    setSubmittedJobTitle(jobTitle);
    setSubmittedCandidateName("");
    setCandidateId("");
    setJobId("");
    setStatusFilter("");
  };

  const handleCandidateIdChange = (value) => {
    setPageNo(0);
    setCandidateId(value);

    setSubmittedCandidateName("");
    setSubmittedJobTitle("");
    setCandidateName("");
    setJobTitle("");

    if (value) {
      setJobId("");
      setStatusFilter("");
    }
  };

  const handleJobIdChange = (value) => {
    setPageNo(0);
    setJobId(value);

    setSubmittedCandidateName("");
    setSubmittedJobTitle("");
    setCandidateName("");
    setJobTitle("");

    if (value) {
      setCandidateId("");
      setStatusFilter("");
    }
  };

  const handleStatusChange = (value) => {
    setPageNo(0);
    setStatusFilter(value);

    setSubmittedCandidateName("");
    setSubmittedJobTitle("");
    setCandidateName("");
    setJobTitle("");

    if (value) {
      setCandidateId("");
      setJobId("");
    }
  };

  const handleSortChange = (value) => {
    setPageNo(0);
    setSortBy(value);
  };

  const handleSortDirectionChange = (value) => {
    setPageNo(0);
    setSortDir(value);
  };

  const handlePageChange = (newPageNo) => {
    setPageNo(newPageNo);
  };

  const handlePageSizeChange = (newPageSize) => {
    setPageSize(newPageSize);
    setPageNo(0);
  };

  const handleReset = () => {
    setCandidateName("");
    setSubmittedCandidateName("");

    setJobTitle("");
    setSubmittedJobTitle("");

    setCandidateId("");
    setJobId("");
    setStatusFilter("");

    setSortBy("id");
    setSortDir("asc");

    setPageSize(10);
    setPageNo(0);
  };

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

  const handleStatusUpdate = async (id, status) => {
    try {
      setError("");

      await updateApplicationStatus(id, status);
      await fetchApplications();
    } catch (err) {
      setError(err.message || "Failed to update application status.");
    }
  };

  if (viewingApplicationId) {
    return (
      <ApplicationDetails
        applicationId={viewingApplicationId}
        onBack={() => setViewingApplicationId(null)}
      />
    );
  }

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
        <button type="button" onClick={fetchApplications}>
          Try Again
        </button>
      </div>
    );
  }

  return (
    <div className="application-page">
      <div className="application-page-header">
        <div>
          <h1>Applications</h1>

          <p>
            {isCandidate
              ? "View your job applications."
              : "Manage job applications in the recruitment system."}
          </p>
        </div>

        {canManageApplications && (
          <button
            className="application-add-button"
            onClick={() => {
              setEditingApplication(null);
              setShowForm(true);
            }}
          >
            Add Application
          </button>
        )}
      </div>

      {!isCandidate && (
        <SearchFilterBar onReset={handleReset}>
          <div className="search-filter-group">
            <label>Candidate Name</label>

            <input
              type="text"
              value={candidateName}
              placeholder="Search candidate"
              onChange={(event) => setCandidateName(event.target.value)}
              onKeyDown={(event) => {
                if (event.key === "Enter") {
                  handleCandidateNameSearch();
                }
              }}
            />

            <button type="button" onClick={handleCandidateNameSearch}>
              Search
            </button>
          </div>

          <div className="search-filter-group">
            <label>Job Title</label>

            <input
              type="text"
              value={jobTitle}
              placeholder="Search job"
              onChange={(event) => setJobTitle(event.target.value)}
              onKeyDown={(event) => {
                if (event.key === "Enter") {
                  handleJobTitleSearch();
                }
              }}
            />

            <button type="button" onClick={handleJobTitleSearch}>
              Search
            </button>
          </div>

          <div className="search-filter-group">
            <label>Candidate ID</label>

            <input
              type="number"
              min="1"
              value={candidateId}
              placeholder="Candidate ID"
              onChange={(event) => handleCandidateIdChange(event.target.value)}
            />
          </div>

          <div className="search-filter-group">
            <label>Job ID</label>

            <input
              type="number"
              min="1"
              value={jobId}
              placeholder="Job ID"
              onChange={(event) => handleJobIdChange(event.target.value)}
            />
          </div>

          <div className="search-filter-group">
            <label>Status</label>

            <select
              value={statusFilter}
              onChange={(event) => handleStatusChange(event.target.value)}
            >
              <option value="">All Statuses</option>
              <option value="APPLIED">APPLIED</option>
              <option value="SHORTLISTED">SHORTLISTED</option>
              <option value="INTERVIEW_SCHEDULED">INTERVIEW SCHEDULED</option>
              <option value="SELECTED">SELECTED</option>
              <option value="REJECTED">REJECTED</option>
            </select>
          </div>

          <div className="search-filter-group">
            <label>Sort By</label>

            <select
              value={sortBy}
              onChange={(event) => handleSortChange(event.target.value)}
            >
              <option value="id">ID</option>
              <option value="candidateId">Candidate ID</option>
              <option value="jobId">Job ID</option>
              <option value="status">Status</option>
            </select>
          </div>

          <div className="search-filter-group">
            <label>Direction</label>

            <select
              value={sortDir}
              onChange={(event) =>
                handleSortDirectionChange(event.target.value)
              }
            >
              <option value="asc">Ascending</option>
              <option value="desc">Descending</option>
            </select>
          </div>
        </SearchFilterBar>
      )}

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
                    {canManageApplications ? (
                      <select
                        value={application.status}
                        onChange={(event) =>
                          handleStatusUpdate(application.id, event.target.value)
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
                    ) : (
                      application.status
                    )}
                  </td>

                  <td>
                    <button
                      type="button"
                      onClick={() => setViewingApplicationId(application.id)}
                    >
                      View
                    </button>

                    {canManageApplications && (
                      <>
                        <button
                          type="button"
                          onClick={() => handleEdit(application.id)}
                        >
                          Edit
                        </button>

                        <button
                          type="button"
                          onClick={() => handleDelete(application.id)}
                        >
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
      )}

      <Pagination
        pageNo={pageNo}
        pageSize={pageSize}
        totalPages={totalPages}
        totalElements={totalElements}
        onPageChange={handlePageChange}
        onPageSizeChange={handlePageSizeChange}
      />
    </div>
  );
}

export default ApplicationList;
