import { useEffect, useState } from "react";
import {
  getAllApplications,
  getApplicationById,
  deleteApplication,
  updateApplicationStatus,
} from "../../services/applicationService";
import ApplicationForm from "./ApplicationForm";
import ApplicationDetails from "./ApplicationDetails";

function ApplicationList() {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [viewingApplicationId, setViewingApplicationId] = useState(null);
  const [editingApplication, setEditingApplication] = useState(null);

  const fetchApplications = async () => {
    try {
      setLoading(true);
      setError("");

      const response = await getAllApplications();

      setApplications(response.data?.content || []);
    } catch (err) {
      setError(err.message || "Failed to load applications.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchApplications();
  }, []);

  const handleEdit = async (id) => {
    try {
      setError("");

      const response = await getApplicationById(id);

      setEditingApplication(response.data);
      setShowForm(true);
    } catch (err) {
      setError(err.message || "Failed to load application details.");
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this application?",
    );

    if (!confirmed) {
      return;
    }

    try {
      setError("");

      await deleteApplication(id);

      await fetchApplications();
    } catch (err) {
      setError(err.message || "Failed to delete application.");
    }
  };

  const handleStatusChange = async (id, status) => {
    try {
      setError("");

      await updateApplicationStatus(id, status);

      await fetchApplications();
    } catch (err) {
      setError(err.message || "Failed to update application status.");
    }
  };

  if (loading) {
    return (
      <div className="application-page">
        <h1>Applications</h1>
        <p>Loading applications...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="application-page">
        <h1>Applications</h1>

        <p className="application-error">{error}</p>
      </div>
    );
  }

  if (viewingApplicationId) {
    return (
      <ApplicationDetails
        applicationId={viewingApplicationId}
        onBack={() => setViewingApplicationId(null)}
      />
    );
  }

  return (
    <div className="application-page">
      <div className="application-page-header">
        <div>
          <h1>Applications</h1>

          <p>Manage job applications in the recruitment system.</p>
        </div>

        <button
          className="application-add-button"
          onClick={() => {
            setEditingApplication(null);
            setShowForm(true);
          }}
        >
          Add Application
        </button>
      </div>

      {showForm && (
        <ApplicationForm
          application={editingApplication}
          onSuccess={() => {
            setShowForm(false);
            setEditingApplication(null);
            fetchApplications();
          }}
          onCancel={() => {
            setShowForm(false);
            setEditingApplication(null);
          }}
        />
      )}

      {applications.length === 0 ? (
        <div className="application-empty">No applications found.</div>
      ) : (
        <div className="application-table-container">
          <table className="application-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Candidate ID</th>
                <th>Job ID</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {applications.map((application) => (
                <tr key={application.id}>
                  <td>{application.id}</td>

                  <td>{application.candidateId}</td>

                  <td>{application.jobId}</td>

                  <td>
                    <select
                      value={application.status}
                      onChange={(event) =>
                        handleStatusChange(application.id, event.target.value)
                      }
                    >
                      <option value="APPLIED">APPLIED</option>

                      <option value="SHORTLISTED">SHORTLISTED</option>

                      <option value="INTERVIEW_SCHEDULED">
                        INTERVIEW SCHEDULED
                      </option>

                      <option value="SELECTED">SELECTED</option>

                      <option value="REJECTED">REJECTED</option>
                    </select>
                  </td>

                  <td>
                    <button
                      onClick={() => setViewingApplicationId(application.id)}
                    >
                      View
                    </button>

                    <button onClick={() => handleEdit(application.id)}>
                      Edit
                    </button>

                    <button onClick={() => handleDelete(application.id)}>
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default ApplicationList;
