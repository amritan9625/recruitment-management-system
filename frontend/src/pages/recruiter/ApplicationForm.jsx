import { useEffect, useState } from "react";
import {
  createApplication,
  updateApplication,
} from "../../services/applicationService";
import ErrorMessage from "../../components/common/ErrorMessage";

function ApplicationForm({ application = null, onSuccess, onCancel }) {
  const [formData, setFormData] = useState({
    candidateId: "",
    jobId: "",
    status: "APPLIED",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const isEditMode = Boolean(application);

  useEffect(() => {
    if (application) {
      setFormData({
        candidateId: application.candidateId ?? "",
        jobId: application.jobId ?? "",
        status: application.status || "APPLIED",
      });
    }
  }, [application]);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!formData.candidateId) {
      setError("Candidate ID is required.");
      return;
    }

    if (!formData.jobId) {
      setError("Job ID is required.");
      return;
    }

    try {
      setLoading(true);
      setError("");

      const applicationData = {
        candidateId: Number(formData.candidateId),
        jobId: Number(formData.jobId),
        status: formData.status,
      };

      if (isEditMode) {
        await updateApplication(application.id, applicationData);
      } else {
        await createApplication(applicationData);
      }

      onSuccess();
    } catch (err) {
      setError(
        err.message ||
          `Failed to ${isEditMode ? "update" : "create"} application.`,
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="application-form-container">
      <h2>{isEditMode ? "Edit Application" : "Add Application"}</h2>

      {error && <ErrorMessage message={error} />}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Candidate ID</label>

          <input
            type="number"
            name="candidateId"
            value={formData.candidateId}
            onChange={handleChange}
            min="1"
            required
          />
        </div>

        <div className="form-group">
          <label>Job ID</label>

          <input
            type="number"
            name="jobId"
            value={formData.jobId}
            onChange={handleChange}
            min="1"
            required
          />
        </div>

        <div className="form-group">
          <label>Status</label>

          <select name="status" value={formData.status} onChange={handleChange}>
            <option value="APPLIED">APPLIED</option>

            <option value="SHORTLISTED">SHORTLISTED</option>

            <option value="INTERVIEW_SCHEDULED">INTERVIEW SCHEDULED</option>

            <option value="SELECTED">SELECTED</option>

            <option value="REJECTED">REJECTED</option>
          </select>
        </div>

        <div className="application-form-actions">
          <button type="submit" disabled={loading}>
            {loading
              ? "Saving..."
              : isEditMode
                ? "Update Application"
                : "Save Application"}
          </button>

          <button type="button" onClick={onCancel} disabled={loading}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}

export default ApplicationForm;
