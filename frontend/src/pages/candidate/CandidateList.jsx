import { useEffect, useState } from "react";
import {
  getAllCandidates,
  deleteCandidate,
  updateCandidate,
} from "../../services/candidateService";
import CandidateForm from "./CandidateForm";
import CandidateDetails from "./CandidateDetails";

function CandidateList() {
  const [candidates, setCandidates] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [editingCandidate, setEditingCandidate] = useState(null);
  const [viewingCandidateId, setViewingCandidateId] = useState(null);
  const [deletingId, setDeletingId] = useState(null);
  const [updatingStatusId, setUpdatingStatusId] = useState(null);

  const fetchCandidates = async () => {
    try {
      setLoading(true);
      setError("");

      const response = await getAllCandidates();

      setCandidates(response.data?.content || []);
    } catch (err) {
      setError(err.message || "Failed to load candidates.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCandidates();
  }, []);

  if (viewingCandidateId) {
    return (
      <CandidateDetails
        candidateId={viewingCandidateId}
        onBack={() => setViewingCandidateId(null)}
      />
    );
  }

  if (loading) {
    return (
      <div className="candidate-page">
        <h1>Candidates</h1>
        <p>Loading candidates...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="candidate-page">
        <h1>Candidates</h1>
        <p className="candidate-error">{error}</p>
      </div>
    );
  }

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this candidate?",
    );

    if (!confirmed) {
      return;
    }

    try {
      setError("");
      setDeletingId(id);

      await deleteCandidate(id);

      await fetchCandidates();
    } catch (err) {
      setError(err.message || "Failed to delete candidate.");
    } finally {
      setDeletingId(null);
    }
  };

  const handleStatusChange = async (candidate, status) => {
    try {
      setError("");
      setUpdatingStatusId(candidate.id);

      const candidateData = {
        firstName: candidate.firstName,
        lastName: candidate.lastName,
        email: candidate.email,
        phone: candidate.phone,
        skills: candidate.skills,
        experience: candidate.experience,
        resumeUrl: candidate.resumeUrl,
        status,
      };

      await updateCandidate(candidate.id, candidateData);

      await fetchCandidates();
    } catch (err) {
      setError(err.message || "Failed to update candidate status.");
    } finally {
      setUpdatingStatusId(null);
    }
  };

  return (
    <div className="candidate-page">
      <div className="candidate-page-header">
        <div>
          <h1>Candidates</h1>
          <p>Manage candidates in the recruitment system.</p>
        </div>
        <button
          className="candidate-add-button"
          onClick={() => {
            setEditingCandidate(null);
            setShowForm(true);
          }}
        >
          Add Candidate
        </button>
      </div>

      {showForm && (
        <CandidateForm
          candidate={editingCandidate}
          onSuccess={() => {
            setShowForm(false);
            setEditingCandidate(null);
            fetchCandidates();
          }}
          onCancel={() => {
            setShowForm(false);
            setEditingCandidate(null);
          }}
        />
      )}

      {candidates.length === 0 ? (
        <div className="candidate-empty">No candidates found.</div>
      ) : (
        <div className="candidate-table-container">
          <table className="candidate-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Skills</th>
                <th>Experience</th>
                <th>Resume</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {candidates.map((candidate) => (
                <tr key={candidate.id}>
                  <td>
                    {candidate.firstName} {candidate.lastName}
                  </td>

                  <td>{candidate.email}</td>

                  <td>{candidate.phone}</td>

                  <td>{candidate.skills}</td>

                  <td>{candidate.experience}</td>

                  <td>
                    {candidate.resumeUrl ? (
                      <a
                        href={candidate.resumeUrl}
                        target="_blank"
                        rel="noopener noreferrer"
                      >
                        View Resume
                      </a>
                    ) : (
                      "Not Available"
                    )}
                  </td>

                  <td>
                    <select
                      value={candidate.status || "APPLIED"}
                      onChange={(event) =>
                        handleStatusChange(candidate, event.target.value)
                      }
                      disabled={updatingStatusId === candidate.id}
                    >
                      <option value="APPLIED">APPLIED</option>
                      <option value="SCREENING">SCREENING</option>
                      <option value="SHORTLISTED">SHORTLISTED</option>
                      <option value="INTERVIEW_SCHEDULED">
                        INTERVIEW SCHEDULED
                      </option>
                      <option value="INTERVIEWED">INTERVIEWED</option>
                      <option value="OFFERED">OFFERED</option>
                      <option value="HIRED">HIRED</option>
                      <option value="REJECTED">REJECTED</option>
                    </select>
                  </td>

                  <td>
                    <button onClick={() => setViewingCandidateId(candidate.id)}>
                      View
                    </button>

                    <button
                      onClick={() => {
                        setEditingCandidate(candidate);
                        setShowForm(true);
                      }}
                    >
                      Edit
                    </button>

                    <button
                      onClick={() => handleDelete(candidate.id)}
                      disabled={deletingId === candidate.id}
                    >
                      {deletingId === candidate.id ? "Deleting..." : "Delete"}
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

export default CandidateList;
