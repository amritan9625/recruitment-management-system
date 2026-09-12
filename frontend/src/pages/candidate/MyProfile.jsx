import { useEffect, useState } from "react";
import {
  getMyCandidateProfile,
  saveMyCandidateProfile,
} from "../../services/candidateService";
import Loading from "../../components/common/Loading";
import ErrorMessage from "../../components/common/ErrorMessage";

const emptyProfile = {
  firstName: "",
  lastName: "",
  email: "",
  phone: "",
  skills: "",
  experience: "",
  resumeUrl: "",
  status: "APPLIED",
};

function MyProfile() {
  const [formData, setFormData] = useState(emptyProfile);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const loadProfile = async () => {
    setLoading(true);
    setError("");

    try {
      const response = await getMyCandidateProfile();
      setFormData({
        ...emptyProfile,
        ...(response.data || {}),
        experience: response.data?.experience ?? "",
      });
    } catch (err) {
      // A missing profile is expected for a newly registered candidate.
      if (err.message?.toLowerCase().includes("profile not found")) {
        setFormData(emptyProfile);
      } else {
        setError(err.message || "Failed to load your profile.");
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProfile();
  }, []);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((previous) => ({ ...previous, [name]: value }));
    setSuccess("");
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");
    setSuccess("");

    if (formData.experience === "" || Number(formData.experience) < 0) {
      setError("Enter valid experience (0 or more years).");
      return;
    }

    if (formData.phone.trim().length < 10) {
      setError("Phone number must contain at least 10 characters.");
      return;
    }

    setSaving(true);

    try {
      const payload = {
        ...formData,
        experience: Number(formData.experience),
        status: formData.status || "APPLIED",
      };

      const response = await saveMyCandidateProfile(payload);

      setFormData({
        ...emptyProfile,
        ...(response.data || payload),
        experience: response.data?.experience ?? payload.experience,
      });
      setSuccess("Your profile has been saved.");
    } catch (err) {
      setError(err.message || "Failed to save your profile.");
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <div className="page-container">
        <h1>My Profile</h1>
        <Loading />
      </div>
    );
  }

  return (
    <div className="page-container">
      <div className="page-header">
        <div>
          <h1>My Profile</h1>
          <p>Keep your candidate details up to date.</p>
        </div>
      </div>

      {error && <ErrorMessage message={error} onRetry={loadProfile} />}
      {success && (
        <p className="success-message" role="status">
          {success}
        </p>
      )}

      <form className="card" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="firstName">First name</label>
          <input
            id="firstName"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="lastName">Last name</label>
          <input
            id="lastName"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Account email</label>
          <input
            id="email"
            type="email"
            name="email"
            value={formData.email}
            readOnly
            placeholder="Your registered email"
          />
          <small>Your account email is managed by the system.</small>
        </div>

        <div className="form-group">
          <label htmlFor="phone">Phone</label>
          <input
            id="phone"
            type="tel"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
            minLength="10"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="skills">Skills</label>
          <input
            id="skills"
            name="skills"
            value={formData.skills}
            onChange={handleChange}
            placeholder="Java, Spring Boot, React"
          />
        </div>

        <div className="form-group">
          <label htmlFor="experience">Experience (years)</label>
          <input
            id="experience"
            type="number"
            name="experience"
            value={formData.experience}
            onChange={handleChange}
            min="0"
            step="0.1"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="resumeUrl">Resume URL</label>
          <input
            id="resumeUrl"
            type="url"
            name="resumeUrl"
            value={formData.resumeUrl}
            onChange={handleChange}
            placeholder="https://..."
          />
        </div>

        <div className="action-buttons">
          <button type="submit" disabled={saving}>
            {saving ? "Saving..." : "Save Profile"}
          </button>
        </div>
      </form>
    </div>
  );
}

export default MyProfile;
