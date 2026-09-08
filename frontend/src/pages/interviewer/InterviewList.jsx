import { useEffect, useState } from "react";
import {
  deleteInterview,
  getAllInterviews,
  getInterviewById,
  updateInterview,
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

  const fetchInterviews = async () => {
    try {
      setLoading(true);
      setError("");

      const response = await getAllInterviews();

      setInterviews(response.data?.content || []);
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

  useEffect(() => {
    fetchInterviews();
  }, []);

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
                    <button onClick={() => setViewingInterviewId(interview.id)}>
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
      )}
    </div>
  );
}

export default InterviewList;
