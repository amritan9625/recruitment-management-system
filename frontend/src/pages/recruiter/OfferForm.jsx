import { useEffect, useState } from "react";
import {
  createOffer,
  updateOffer,
} from "../../services/offerService";
import ErrorMessage from "../../components/common/ErrorMessage";

function OfferForm({ offer = null, onSuccess, onCancel }) {
  const [formData, setFormData] = useState({
    salary: "",
    joiningDate: "",
    status: "PENDING",
    candidateId: "",
    jobId: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const isEditMode = Boolean(offer);

  useEffect(() => {
    if (offer) {
      setFormData({
        salary: offer.salary ?? "",
        joiningDate: offer.joiningDate || "",
        status: offer.status || "PENDING",
        candidateId: offer.candidateId ?? "",
        jobId: offer.jobId ?? "",
      });
    }
  }, [offer]);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!formData.salary) {
      setError("Salary is required.");
      return;
    }

    if (Number(formData.salary) <= 0) {
      setError("Salary must be positive.");
      return;
    }

    if (!formData.joiningDate) {
      setError("Joining date is required.");
      return;
    }

    if (!formData.candidateId) {
      setError("Candidate ID is required.");
      return;
    }

    if (Number(formData.candidateId) <= 0) {
      setError("Candidate ID must be positive.");
      return;
    }

    if (!formData.jobId) {
      setError("Job ID is required.");
      return;
    }

    if (Number(formData.jobId) <= 0) {
      setError("Job ID must be positive.");
      return;
    }

    try {
      setLoading(true);
      setError("");

      const offerData = {
        salary: Number(formData.salary),
        joiningDate: formData.joiningDate,
        status: formData.status,
        candidateId: Number(formData.candidateId),
        jobId: Number(formData.jobId),
      };

      if (isEditMode) {
        await updateOffer(offer.id, offerData);
      } else {
        await createOffer(offerData);
      }

      onSuccess();
    } catch (err) {
      setError(
        err.message ||
          `Failed to ${isEditMode ? "update" : "create"} offer.`,
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="offer-form-container">
      <h2>{isEditMode ? "Edit Offer" : "Add Offer"}</h2>

      {error && <ErrorMessage message={error} />}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Salary</label>
          <input
            type="number"
            name="salary"
            value={formData.salary}
            onChange={handleChange}
            placeholder="Enter salary"
            min="1"
            step="0.01"
            required
          />
        </div>

        <div className="form-group">
          <label>Joining Date</label>
          <input
            type="date"
            name="joiningDate"
            value={formData.joiningDate}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label>Candidate ID</label>
          <input
            type="number"
            name="candidateId"
            value={formData.candidateId}
            onChange={handleChange}
            placeholder="Enter candidate ID"
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
            placeholder="Enter job ID"
            min="1"
            required
          />
        </div>

        <div className="form-group">
          <label>Status</label>

          <select
            name="status"
            value={formData.status}
            onChange={handleChange}
          >
            <option value="PENDING">PENDING</option>
            <option value="ACCEPTED">ACCEPTED</option>
            <option value="REJECTED">REJECTED</option>
          </select>
        </div>

        <div className="offer-form-actions">
          <button type="submit" disabled={loading}>
            {loading
              ? "Saving..."
              : isEditMode
                ? "Update Offer"
                : "Save Offer"}
          </button>

          <button
            type="button"
            onClick={onCancel}
            disabled={loading}
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}

export default OfferForm;