import { useEffect, useState } from "react";
import { getInterviewById } from "../../services/interviewService";

function InterviewDetails({ interviewId, onBack }) {
  const [interview, setInterview] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchInterview = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await getInterviewById(interviewId);

        setInterview(response.data);
      } catch (err) {
        setError(err.message || "Failed to load interview details.");
      } finally {
        setLoading(false);
      }
    };

    fetchInterview();
  }, [interviewId]);

  if (loading) {
    return (
      <div className="interview-page">
        <h1>Interview Details</h1>
        <p>Loading interview details...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="interview-page">
        <h1>Interview Details</h1>

        <p className="interview-error">{error}</p>

        <button onClick={onBack}>Back to Interviews</button>
      </div>
    );
  }

  if (!interview) {
    return (
      <div className="interview-page">
        <h1>Interview Details</h1>

        <p>Interview not found.</p>

        <button onClick={onBack}>Back to Interviews</button>
      </div>
    );
  }

  return (
    <div className="interview-page">
      <div className="interview-page-header">
        <div>
          <h1>Interview Details</h1>

          <p>View complete interview information.</p>
        </div>

        <button onClick={onBack}>Back to Interviews</button>
      </div>

      <div className="interview-details">
        <p>
          <strong>Interview ID:</strong> {interview.id}
        </p>

        <p>
          <strong>Interview Date:</strong> {interview.interviewDate}
        </p>

        <p>
          <strong>Interviewer:</strong> {interview.interviewer}
        </p>

        <p>
          <strong>Mode:</strong> {interview.mode}
        </p>

        <p>
          <strong>Application ID:</strong> {interview.applicationId}
        </p>

        <p>
          <strong>Status:</strong> {interview.status}
        </p>
      </div>
    </div>
  );
}

export default InterviewDetails;
