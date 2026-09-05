import { useEffect, useState } from "react";
import { getAllCandidates } from "../../services/candidateService";
import CandidateForm from "./CandidateForm";

function CandidateList() {
  const [candidates, setCandidates] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);

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

  return (
    <div className="candidate-page">
      <div className="candidate-page-header">
        <div>
          <h1>Candidates</h1>
          <p>Manage candidates in the recruitment system.</p>
        </div>
        <button
          className="candidate-add-button"
          onClick={() => setShowForm(true)}
        >
          Add Candidate
        </button>
      </div>

      {showForm && (
        <CandidateForm
          onSuccess={() => {
            setShowForm(false);
            fetchCandidates();
          }}
          onCancel={() => setShowForm(false)}
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

                    <button>Edit</button>

                    <button>Delete</button>
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
