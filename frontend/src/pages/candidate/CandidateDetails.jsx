function CandidateDetails({ candidate, onClose }) {
  if (!candidate) {
    return null;
  }

  return (
    <div className="candidate-details">
      <div className="candidate-details-header">
        <h2>Candidate Details</h2>

        <button onClick={onClose}>Close</button>
      </div>

      <div className="candidate-details-content">
        <div className="candidate-detail-item">
          <strong>Name</strong>
          <span>
            {candidate.firstName} {candidate.lastName}
          </span>
        </div>

        <div className="candidate-detail-item">
          <strong>Email</strong>
          <span>{candidate.email}</span>
        </div>

        <div className="candidate-detail-item">
          <strong>Phone</strong>
          <span>{candidate.phone}</span>
        </div>

        <div className="candidate-detail-item">
          <strong>Skills</strong>
          <span>{candidate.skills || "N/A"}</span>
        </div>

        <div className="candidate-detail-item">
          <strong>Experience</strong>
          <span>{candidate.experience} years</span>
        </div>

        <div className="candidate-detail-item">
          <strong>Status</strong>
          <span>{candidate.status}</span>
        </div>

        <div className="candidate-detail-item">
          <strong>Resume</strong>

          {candidate.resumeUrl ? (
            <a
              href={candidate.resumeUrl}
              target="_blank"
              rel="noopener noreferrer"
            >
              View Resume
            </a>
          ) : (
            <span>Not Available</span>
          )}
        </div>
      </div>
    </div>
  );
}

export default CandidateDetails;
