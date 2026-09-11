import { useEffect, useState } from "react";
import {
  createInterview,
  updateInterview,
} from "../../services/interviewService";
import ErrorMessage from "../../components/common/ErrorMessage";

function InterviewForm({ interview = null, onSuccess, onCancel }) {
  const [formData, setFormData] = useState({
    interviewDate: "",
    interviewer: "",
    mode: "",
    applicationId: "",
    status: "SCHEDULED",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const isEditMode = Boolean(interview);

  useEffect(() => {
    if (interview) {
      setFormData({
        interviewDate: interview.interviewDate
          ? interview.interviewDate.slice(0, 16)
          : "",
        interviewer: interview.interviewer || "",
        mode: interview.mode || "",
        applicationId: interview.applicationId ?? "",
        status: interview.status || "SCHEDULED",
      });
    }
  }, [interview]);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!formData.interviewDate) {
      setError("Interview date is required.");
      return;
    }

    if (!formData.interviewer.trim()) {
      setError("Interviewer name is required.");
      return;
    }

    if (!formData.mode.trim()) {
      setError("Interview mode is required.");
      return;
    }

    if (!formData.applicationId) {
      setError("Application ID is required.");
      return;
    }

    if (Number(formData.applicationId) <= 0) {
      setError("Application ID must be positive.");
      return;
    }

    try {
      setLoading(true);
      setError("");

      const interviewData = {
        interviewDate: formData.interviewDate,
        interviewer: formData.interviewer.trim(),
        mode: formData.mode.trim(),
        applicationId: Number(formData.applicationId),
        status: formData.status,
      };

      if (isEditMode) {
        await updateInterview(interview.id, interviewData);
      } else {
        await createInterview(interviewData);
      }

      onSuccess();
    } catch (err) {
      setError(
        err.message ||
          `Failed to ${isEditMode ? "update" : "create"} interview.`,
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="interview-form-container">
      <h2>{isEditMode ? "Edit Interview" : "Add Interview"}</h2>

      {error && <ErrorMessage message={error} />}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Interview Date</label>

          <input
            type="datetime-local"
            name="interviewDate"
            value={formData.interviewDate}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>Interviewer</label>

          <input
            type="text"
            name="interviewer"
            value={formData.interviewer}
            onChange={handleChange}
            placeholder="Enter interviewer name"
            required
          />
        </div>

        <div className="form-group">
          <label>Interview Mode</label>

          <input
            type="text"
            name="mode"
            value={formData.mode}
            onChange={handleChange}
            placeholder="e.g. Online, Offline, Phone"
            required
          />
        </div>

        <div className="form-group">
          <label>Application ID</label>

          <input
            type="number"
            name="applicationId"
            value={formData.applicationId}
            onChange={handleChange}
            min="1"
            required
          />
        </div>

        <div className="form-group">
          <label>Status</label>

          <select name="status" value={formData.status} onChange={handleChange}>
            <option value="SCHEDULED">SCHEDULED</option>
            <option value="COMPLETED">COMPLETED</option>
            <option value="CANCELLED">CANCELLED</option>
          </select>
        </div>

        <div className="interview-form-actions">
          <button type="submit" disabled={loading}>
            {loading
              ? "Saving..."
              : isEditMode
                ? "Update Interview"
                : "Save Interview"}
          </button>

          <button type="button" onClick={onCancel} disabled={loading}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}

export default InterviewForm;
