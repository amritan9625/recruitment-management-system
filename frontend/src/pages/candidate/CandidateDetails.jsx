import { useEffect, useState } from "react";
import { getCandidateById } from "../../services/candidateService";

function CandidateDetails({ candidateId, onBack }) {
  const [candidate, setCandidate] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchCandidate = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await getCandidateById(candidateId);

        setCandidate(response.data);
      } catch (err) {
        setError(err.message || "Failed to load candidate details.");
      } finally {
        setLoading(false);
      }
    };

    fetchCandidate();
  }, [candidateId]);

  if (loading) {
    return (
      <div className="candidate-page">
        <h1>Candidate Details</h1>
        <p>Loading candidate details...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="candidate-page">
        <h1>Candidate Details</h1>

        <p className="candidate-error">{error}</p>

        <button onClick={onBack}>Back to Candidates</button>
      </div>
    );
  }

  if (!candidate) {
    return (
      <div className="candidate-page">
        <h1>Candidate Details</h1>

        <p>Candidate not found.</p>

        <button onClick={onBack}>Back to Candidates</button>
      </div>
    );
  }

  return (
    <div className="candidate-page">
      <div className="candidate-page-header">
        <div>
          <h1>Candidate Details</h1>
          <p>View complete candidate information.</p>
        </div>

        <button onClick={onBack}>Back to Candidates</button>
      </div>

      <div className="candidate-details">
        <p>
          <strong>Candidate ID:</strong> {candidate.id}
        </p>

        <p>
          <strong>Name:</strong> {candidate.firstName} {candidate.lastName}
        </p>

        <p>
          <strong>Email:</strong> {candidate.email}
        </p>

        <p>
          <strong>Phone:</strong> {candidate.phone}
        </p>

        <p>
          <strong>Skills:</strong> {candidate.skills || "N/A"}
        </p>

        <p>
          <strong>Experience:</strong> {candidate.experience} years
        </p>

        <p>
          <strong>Status:</strong> {candidate.status}
        </p>

        <p>
          <strong>Resume:</strong>{" "}
          {candidate.resumeUrl ? (
            <a
              href={candidate.resumeUrl}
              target="_blank"
              rel="noopener noreferrer"
            >
              View Resume
            </a>
          ) : (
            "Not Available"
          )}
        </p>
      </div>
    </div>
  );
}

export default CandidateDetails;
