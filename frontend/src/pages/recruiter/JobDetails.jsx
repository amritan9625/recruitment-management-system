import { useEffect, useState } from "react";
import { getJobById } from "../../services/jobService";

function JobDetails({ jobId, onBack }) {
  const [job, setJob] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchJob = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await getJobById(jobId);

        setJob(response.data);
      } catch (err) {
        setError(err.message || "Failed to load job details.");
      } finally {
        setLoading(false);
      }
    };

    fetchJob();
  }, [jobId]);

  if (loading) {
    return (
      <div className="job-page">
        <h1>Job Details</h1>
        <p>Loading job details...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="job-page">
        <h1>Job Details</h1>

        <p className="job-error">{error}</p>

        <button onClick={onBack}>Back to Jobs</button>
      </div>
    );
  }

  if (!job) {
    return (
      <div className="job-page">
        <h1>Job Details</h1>
        <p>Job not found.</p>

        <button onClick={onBack}>Back to Jobs</button>
      </div>
    );
  }

  return (
    <div className="job-page">
      <div className="job-page-header">
        <div>
          <h1>Job Details</h1>
          <p>View complete job information.</p>
        </div>
      </div>

      <div className="job-details">
        <p>
          <strong>Job Title:</strong> {job.title}
        </p>

        <p>
          <strong>Description:</strong> {job.description}
        </p>

        <p>
          <strong>Location:</strong> {job.location}
        </p>

        <p>
          <strong>Salary:</strong> {job.salary}
        </p>

        <p>
          <strong>Job Type:</strong> {job.jobType}
        </p>

        <p>
          <strong>Status:</strong> {job.status}
        </p>
      </div>

      <button onClick={onBack}>Back to Jobs</button>
    </div>
  );
}

export default JobDetails;
