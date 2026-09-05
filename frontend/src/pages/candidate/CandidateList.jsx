import { useEffect, useState } from "react";
import {
  getAllCandidates,
  deleteCandidate,
} from "../../services/candidateService";
import CandidateForm from "./CandidateForm";

function CandidateList() {
  const [candidates, setCandidates] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [editingCandidate, setEditingCandidate] = useState(null);

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

      await deleteCandidate(id);

      await fetchCandidates();
    } catch (err) {
      setError(err.message || "Failed to delete candidate.");
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

                  <td>{candidate.status}</td>

                  <td>
                    <button>View</button>

                    <button
                      onClick={() => {
                        setEditingCandidate(candidate);
                        setShowForm(true);
                      }}
                    >
                      Edit
                    </button>

                    <button onClick={() => handleDelete(candidate.id)}>
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

export default CandidateList;
