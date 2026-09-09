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

function InterviewList() {
  const [interviews, setInterviews] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showForm, setShowForm] = useState(false);
  const [viewingInterviewId, setViewingInterviewId] = useState(null);
  const [editingInterview, setEditingInterview] = useState(null);
  const [updatingStatusId, setUpdatingStatusId] = useState(null);

  // Pagination
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  // Sorting
  const [sortBy, setSortBy] = useState("id");
  const [sortDir, setSortDir] = useState("asc");

  // Search
  const [interviewerSearch, setInterviewerSearch] = useState("");
  const [submittedInterviewerSearch, setSubmittedInterviewerSearch] =
    useState("");

  // Filter
  const [statusFilter, setStatusFilter] = useState("");

  const fetchInterviews = async () => {
    try {
      setLoading(true);
      setError("");

      let response;
      
      if (submittedInterviewerSearch) {
        response = await searchInterviewsByInterviewer(
          submittedInterviewerSearch,
          pageNo,
          pageSize,
        );
      }else if (statusFilter) {
        response = await getInterviewsByStatus(statusFilter, pageNo, pageSize);
      }else {
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

  const handleSearch = () => {
    setPageNo(0);
    setSubmittedInterviewerSearch(interviewerSearch.trim());
  };

  const handleReset = () => {
    setInterviewerSearch("");
    setSubmittedInterviewerSearch("");
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

  const handlePageSizeChange = (event) => {
    setPageNo(0);
    setPageSize(Number(event.target.value));
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

  if (viewingInterviewId) {
    return (
      <InterviewDetails
        interviewId={viewingInterviewId}
        onBack={() => setViewingInterviewId(null)}
      />
    );
  }

  return (
    <div className="interview-page">
      <div className="interview-page-header">
        <div>
          <h1>Interviews</h1>
          <p>Manage interviews in the recruitment system.</p>
        </div>

        <button
          className="interview-add-button"
          onClick={() => setShowForm(true)}
        >
          Add Interview
        </button>
      </div>

      {/* Search and Filters */}
      <div className="interview-controls">
        <div>
          <input
            type="text"
            placeholder="Search interviewer..."
            value={interviewerSearch}
            onChange={(event) => {
              setInterviewerSearch(event.target.value);
            }}
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

        <div>
          <select value={statusFilter} onChange={handleStatusFilterChange}>
            <option value="">All Statuses</option>
            <option value="SCHEDULED">SCHEDULED</option>
            <option value="COMPLETED">COMPLETED</option>
            <option value="CANCELLED">CANCELLED</option>
          </select>
        </div>

        <div>
          <select value={sortBy} onChange={handleSortChange}>
            <option value="id">ID</option>
            <option value="interviewDate">Interview Date</option>
            <option value="interviewer">Interviewer</option>
            <option value="mode">Mode</option>
            <option value="status">Status</option>
          </select>

          <select value={sortDir} onChange={handleSortDirectionChange}>
            <option value="asc">Ascending</option>
            <option value="desc">Descending</option>
          </select>
        </div>

        <button type="button" onClick={handleReset}>
          Reset
        </button>
      </div>

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

      {/* Interview Table */}
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
                    </td>

                    <td>
                      <button
                        onClick={() => setViewingInterviewId(interview.id)}
                      >
                        View
                      </button>

                      <button onClick={() => handleEdit(interview.id)}>
                        Edit
                      </button>

                      <button onClick={() => handleDelete(interview.id)}>
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Pagination */}
          <div className="interview-pagination">
            <div>
              <label>Rows per page: </label>

              <select value={pageSize} onChange={handlePageSizeChange}>
                <option value={5}>5</option>
                <option value={10}>10</option>
                <option value={20}>20</option>
              </select>
            </div>

            <div>
              <span>Total Interviews: {totalElements}</span>
            </div>

            <div>
              <button
                type="button"
                disabled={pageNo === 0}
                onClick={() => setPageNo(pageNo - 1)}
              >
                Previous
              </button>

              <span>
                Page {totalPages === 0 ? 0 : pageNo + 1} of {totalPages}
              </span>

              <button
                type="button"
                disabled={totalPages === 0 || pageNo >= totalPages - 1}
                onClick={() => setPageNo(pageNo + 1)}
              >
                Next
              </button>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default InterviewList;
