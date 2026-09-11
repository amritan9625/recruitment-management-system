import { useEffect, useState } from "react";
import {
  deleteInterview,
  getAllInterviews,
  getInterviewById,
  updateInterview,
  getInterviewsByStatus,
  searchInterviewsByInterviewer,
} from "../../services/interviewService";
import InterviewForm from "./InterviewForm";
import InterviewDetails from "./InterviewDetails";
import Pagination from "../../components/Pagination";
import SearchFilterBar from "../../components/SearchFilterBar";
import { ROLES } from "../../utils/permissions";
import { useAuth } from "../../context/AuthContext";

function InterviewList() {
  const { role } = useAuth();
  const canManageInterviews =
    role === ROLES.ADMIN ||
    role === ROLES.RECRUITER ||
    role === ROLES.INTERVIEWER;

  const isCandidate = role === ROLES.CANDIDATE;

  const [interviews, setInterviews] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showForm, setShowForm] = useState(false);
  const [viewingInterviewId, setViewingInterviewId] = useState(null);
  const [editingInterview, setEditingInterview] = useState(null);
  const [updatingStatusId, setUpdatingStatusId] = useState(null);

  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [sortBy, setSortBy] = useState("id");
  const [sortDir, setSortDir] = useState("asc");

  const [interviewerSearch, setInterviewerSearch] = useState("");
  const [submittedInterviewerSearch, setSubmittedInterviewerSearch] =
    useState("");

  const [statusFilter, setStatusFilter] = useState("");

  const fetchInterviews = async () => {
    try {
      setLoading(true);
      setError("");

      let response;

      if (submittedInterviewerSearch.trim()) {
        response = await searchInterviewsByInterviewer(
          submittedInterviewerSearch.trim(),
          pageNo,
          pageSize,
        );
      } else if (statusFilter) {
        response = await getInterviewsByStatus(statusFilter, pageNo, pageSize);
      } else {
        response = await getAllInterviews(pageNo, pageSize, sortBy, sortDir);
      }

      const pageData = response.data;

      setInterviews(pageData?.content || []);
      setTotalPages(pageData?.totalPages || 0);
      setTotalElements(pageData?.totalElements || 0);
    } catch (err) {
      setError(err.message || "Failed to load interviews.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchInterviews();
  }, [
    pageNo,
    pageSize,
    sortBy,
    sortDir,
    submittedInterviewerSearch,
    statusFilter,
  ]);

  const handleSearch = () => {
    setPageNo(0);
    setSubmittedInterviewerSearch(interviewerSearch.trim());
    setStatusFilter("");
  };

  const handleReset = () => {
    setInterviewerSearch("");
    setSubmittedInterviewerSearch("");

    setStatusFilter("");

    setSortBy("id");
    setSortDir("asc");

    setPageSize(10);
    setPageNo(0);
  };

  const handleStatusFilterChange = (event) => {
    setPageNo(0);

    setStatusFilter(event.target.value);

    setInterviewerSearch("");
    setSubmittedInterviewerSearch("");
  };

  const handleSortChange = (event) => {
    setPageNo(0);
    setSortBy(event.target.value);
  };

  const handleSortDirectionChange = (event) => {
    setPageNo(0);
    setSortDir(event.target.value);
  };

  const handlePageChange = (newPageNo) => {
    setPageNo(newPageNo);
  };

  const handlePageSizeChange = (newPageSize) => {
    setPageSize(newPageSize);
    setPageNo(0);
  };

  const handleEdit = async (id) => {
    try {
      setError("");

      const response = await getInterviewById(id);
      setEditingInterview(response.data);
      setShowForm(true);
    } catch (err) {
      setError(err.message || "Failed to load interview details.");
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this interview?",
    );

    if (!confirmed) return;

    try {
      setError("");

      await deleteInterview(id);
      await fetchInterviews();
    } catch (err) {
      setError(err.message || "Failed to delete interview.");
    }
  };

  const handleStatusChange = async (interview, status) => {
    try {
      setError("");
      setUpdatingStatusId(interview.id);

      const interviewData = {
        interviewDate: interview.interviewDate,
        interviewer: interview.interviewer,
        mode: interview.mode,
        applicationId: interview.applicationId,
        status,
      };

      await updateInterview(interview.id, interviewData);

      await fetchInterviews();
    } catch (err) {
      setError(err.message || "Failed to update interview status.");
    } finally {
      setUpdatingStatusId(null);
    }
  };

  if (viewingInterviewId) {
    return (
      <InterviewDetails
        interviewId={viewingInterviewId}
        onBack={() => setViewingInterviewId(null)}
      />
    );
  }

  if (loading) {
    return (
      <div className="interview-page">
        <h1>Interviews</h1>
        <p>Loading interviews...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="interview-page">
        <h1>Interviews</h1>
        <p className="interview-error">{error}</p>
      </div>
    );
  }

  return (
    <div className="interview-page">
      <div className="interview-page-header">
        <div>
          <h1>Interviews</h1>
          <p>
            {isCandidate
              ? "View your interviews."
              : "Manage interviews in the recruitment system."}
          </p>
        </div>

        {canManageInterviews && (
          <button
            className="interview-add-button"
            onClick={() => {
              setEditingInterview(null);
              setShowForm(true);
            }}
          >
            Add Interview
          </button>
        )}
      </div>

      {!isCandidate && (
        <SearchFilterBar onReset={handleReset}>
          <div className="search-filter-group">
            <label>Interviewer</label>
            <input
              type="text"
              placeholder="Search interviewer..."
              value={interviewerSearch}
              onChange={(event) => setInterviewerSearch(event.target.value)}
              onKeyDown={(event) => {
                if (event.key === "Enter") {
                  handleSearch();
                }
              }}
            />

            <button type="button" onClick={handleSearch}>
              Search
            </button>
          </div>

          <div className="search-filter-group">
            <label>Status</label>
            <select value={statusFilter} onChange={handleStatusFilterChange}>
              <option value="">All Statuses</option>
              <option value="SCHEDULED">SCHEDULED</option>
              <option value="COMPLETED">COMPLETED</option>
              <option value="CANCELLED">CANCELLED</option>
            </select>
          </div>

          <div className="search-filter-group">
            <label>Sort By</label>

            <select value={sortBy} onChange={handleSortChange}>
              <option value="id">ID</option>
              <option value="interviewDate">Interview Date</option>
              <option value="interviewer">Interviewer</option>
              <option value="mode">Mode</option>
              <option value="status">Status</option>
            </select>
          </div>
          <div className="search-filter-group">
            <label>Direction</label>

            <select value={sortDir} onChange={handleSortDirectionChange}>
              <option value="asc">Ascending</option>
              <option value="desc">Descending</option>
            </select>
          </div>
        </SearchFilterBar>
      )}

      {showForm && (
        <InterviewForm
          interview={editingInterview}
          onSuccess={() => {
            setShowForm(false);
            setEditingInterview(null);
            fetchInterviews();
          }}
          onCancel={() => {
            setShowForm(false);
            setEditingInterview(null);
          }}
        />
      )}

      {interviews.length === 0 ? (
        <div className="interview-empty">No interviews found.</div>
      ) : (
        <>
          <div className="interview-table-container">
            <table className="interview-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Interview Date</th>
                  <th>Interviewer</th>
                  <th>Mode</th>
                  <th>Application ID</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {interviews.map((interview) => (
                  <tr key={interview.id}>
                    <td>{interview.id}</td>

                    <td>{interview.interviewDate}</td>

                    <td>{interview.interviewer}</td>

                    <td>{interview.mode}</td>

                    <td>{interview.applicationId}</td>

                    <td>
                      {canManageInterviews ? (
                        <select
                          value={interview.status || "SCHEDULED"}
                          onChange={(event) =>
                            handleStatusChange(interview, event.target.value)
                          }
                          disabled={updatingStatusId === interview.id}
                        >
                          <option value="SCHEDULED">SCHEDULED</option>
                          <option value="COMPLETED">COMPLETED</option>
                          <option value="CANCELLED">CANCELLED</option>
                        </select>
                      ) : (
                        interview.status
                      )}
                    </td>

                    <td>
                      <button
                        type="button"
                        onClick={() => setViewingInterviewId(interview.id)}
                      >
                        View
                      </button>

                      {canManageInterviews && (
                        <>
                          <button
                            type="button"
                            onClick={() => handleEdit(interview.id)}
                          >
                            Edit
                          </button>

                          <button
                            type="button"
                            onClick={() => handleDelete(interview.id)}
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

          <Pagination
            pageNo={pageNo}
            pageSize={pageSize}
            totalPages={totalPages}
            totalElements={totalElements}
            onPageChange={handlePageChange}
            onPageSizeChange={handlePageSizeChange}
          />
        </>
      )}
    </div>
  );
}

export default InterviewList;
