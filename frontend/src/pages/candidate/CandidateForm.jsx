import { useEffect, useState } from "react";
import {
  createCandidate,
  updateCandidate,
} from "../../services/candidateService";

function CandidateForm({ candidate = null, onSuccess, onCancel }) {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    skills: "",
    experience: "",
    resumeUrl: "",
    status: "APPLIED",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const isEditMode = Boolean(candidate);

  useEffect(() => {
    if (candidate) {
      setFormData({
        firstName: candidate.firstName || "",
        lastName: candidate.lastName || "",
        email: candidate.email || "",
        phone: candidate.phone || "",
        skills: candidate.skills || "",
        experience: candidate.experience ?? "",
        resumeUrl: candidate.resumeUrl || "",
        status: candidate.status || "APPLIED",
      });
    }
  }, [candidate]);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (formData.experience === "") {
        setError("Experience is required.");
        return;
    }
    if (Number(formData.experience) < 0) {
        setError("Experience cannot be negative.");
        return;
    }
    if (formData.phone.length < 10) {
        setError("Phone number must contain at least 10 digits.");
        return;
    }

    try {
      setLoading(true);
      setError("");

      const candidateData = {
        ...formData,
        experience: Number(formData.experience),
      };

      if (isEditMode) {
        await updateCandidate(candidate.id, candidateData);
      } else {
        await createCandidate(candidateData);
      }

      onSuccess();
    } catch (err) {
      setError(
        err.message ||
          `Failed to ${isEditMode ? "update" : "create"} candidate.`,
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="candidate-form-container">
      <h2>{isEditMode ? "Edit Candidate" : "Add Candidate"}</h2>

      {error && <p className="candidate-error">{error}</p>}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>First Name</label>
          <input
            type="text"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>Last Name</label>
          <input
            type="text"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Email</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>Phone</label>
          <input
            type="tel"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
            minLength="10"
            required
          />
        </div>

        <div className="form-group">
          <label>Skills</label>
          <input
            type="text"
            name="skills"
            value={formData.skills}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Experience (Years)</label>
          <input
            type="number"
            name="experience"
            value={formData.experience}
            onChange={handleChange}
            min="0"
            required
          />
        </div>

        <div className="form-group">
          <label>Resume URL</label>
          <input
            type="url"
            name="resumeUrl"
            value={formData.resumeUrl}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Status</label>

          <select name="status" value={formData.status} onChange={handleChange}>
            <option value="APPLIED">APPLIED</option>
            <option value="SCREENING">SCREENING</option>
            <option value="SHORTLISTED">SHORTLISTED</option>
            <option value="INTERVIEW_SCHEDULED">INTERVIEW SCHEDULED</option>
            <option value="OFFERED">OFFERED</option>
            <option value="REJECTED">REJECTED</option>
          </select>
        </div>

        <div className="candidate-form-actions">
          <button type="submit" disabled={loading}>
            {loading
              ? "Saving..."
              : isEditMode
                ? "Update Candidate"
                : "Save Candidate"}
          </button>

          <button type="button" onClick={onCancel} disabled={loading}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}

export default CandidateForm;
