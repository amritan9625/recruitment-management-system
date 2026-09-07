import { useEffect, useState } from "react";
import { getApplicationById } from "../../services/applicationService";

function ApplicationDetails({ applicationId, onBack }) {
  const [application, setApplication] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchApplication = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await getApplicationById(applicationId);

        setApplication(response.data);
      } catch (err) {
        setError(err.message || "Failed to load application details.");
      } finally {
        setLoading(false);
      }
    };

    fetchApplication();
  }, [applicationId]);

  if (loading) {
    return (
      <div className="application-page">
        <h1>Application Details</h1>
        <p>Loading application details...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="application-page">
        <h1>Application Details</h1>

        <p className="application-error">{error}</p>

        <button onClick={onBack}>Back to Applications</button>
      </div>
    );
  }

  if (!application) {
    return (
      <div className="application-page">
        <h1>Application Details</h1>

        <p>Application not found.</p>

        <button onClick={onBack}>Back to Applications</button>
      </div>
    );
  }

  return (
    <div className="application-page">
      <div className="application-page-header">
        <div>
          <h1>Application Details</h1>

          <p>View complete application information.</p>
        </div>

        <button onClick={onBack}>Back to Applications</button>
      </div>

      <div className="application-details">
        <p>
          <strong>Application ID:</strong> {application.id}
        </p>

        <p>
          <strong>Candidate ID:</strong> {application.candidateId}
        </p>

        <p>
          <strong>Job ID:</strong> {application.jobId}
        </p>

        <p>
          <strong>Status:</strong> {application.status}
        </p>
      </div>
    </div>
  );
}

export default ApplicationDetails;
