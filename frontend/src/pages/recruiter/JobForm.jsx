import { useEffect, useState } from "react";
import { createJob, updateJob } from "../../services/jobService";
import ErrorMessage from "../../components/common/ErrorMessage";

function JobForm({ job = null, onSuccess, onCancel }) {
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    location: "",
    salary: "",
    jobType: "FULL_TIME",
    status: "OPEN",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const isEditMode = Boolean(job);

  useEffect(() => {
    if (job) {
      setFormData({
        title: job.title || "",
        description: job.description || "",
        location: job.location || "",
        salary: job.salary ?? "",
        jobType: job.jobType || "FULL_TIME",
        status: job.status || "OPEN",
      });
    }
  }, [job]);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (!formData.title.trim()) {
      setError("Job title is required.");
      return;
    }
    if (!formData.location.trim()) {
      setError("Location is required.");
      return;
    }
    if (formData.salary !== "" && Number(formData.salary) <= 0) {
      setError("Salary must be greater than 0.");
      return;
    }
    if (formData.description.trim().length < 10) {
      setError("Description must be at least 10 characters.");
      return;
    }

    try {
      setLoading(true);
      setError("");

      const jobData = {
        ...formData,
        salary: formData.salary === "" ? null : Number(formData.salary),
      };

      if (isEditMode) {
        await updateJob(job.id, jobData);
      } else {
        await createJob(jobData);
      }

      onSuccess();
    } catch (err) {
      setError(
        err.message || `Failed to ${isEditMode ? "update" : "create"} job.`,
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="job-form-container">
      <h2>{isEditMode ? "Edit Job" : "Add Job"}</h2>

      {error && <ErrorMessage message={error} />}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Job Title</label>

          <input
            type="text"
            name="title"
            value={formData.title}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>Description</label>

          <textarea
            name="description"
            value={formData.description}
            onChange={handleChange}
            rows="3"
            minLength="10"
            maxLength="100"
            required
          />
        </div>

        <div className="form-group">
          <label>Location</label>

          <input
            type="text"
            name="location"
            value={formData.location}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>Salary</label>

          <input
            type="number"
            name="salary"
            value={formData.salary}
            onChange={handleChange}
            min="1"
            required
          />
        </div>

        <div className="form-group">
          <label>Job Type</label>

          <select
            name="jobType"
            value={formData.jobType}
            onChange={handleChange}
          >
            <option value="FULL_TIME">FULL TIME</option>

            <option value="PART_TIME">PART TIME</option>

            <option value="INTERNSHIP">INTERNSHIP</option>

            <option value="CONTRACT">CONTRACT</option>
          </select>
        </div>

        <div className="form-group">
          <label>Status</label>

          <select name="status" value={formData.status} onChange={handleChange}>
            <option value="OPEN">OPEN</option>

            <option value="CLOSED">CLOSED</option>
          </select>
        </div>

        <div className="job-form-actions">
          <button type="submit" disabled={loading}>
            {loading ? "Saving..." : isEditMode ? "Update Job" : "Save Job"}
          </button>

          <button type="button" onClick={onCancel} disabled={loading}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}

export default JobForm;
