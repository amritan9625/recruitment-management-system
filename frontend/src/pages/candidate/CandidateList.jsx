import { useEffect, useState } from "react";
import {
  getAllCandidates,
  deleteCandidate,
  updateCandidate,
  searchCandidates,
  getCandidatesByStatus,
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

  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [sortBy, setSortBy] = useState("id");
  const [sortDir, setSortDir] = useState("asc");

  const [searchText, setSearchText] = useState("");
  const [statusFilter, setStatusFilter] = useState("");

  const fetchCandidates = async () => {
    try {
      setLoading(true);
      setError("");

      let response;

      if (searchText.trim()) {
        response = await searchCandidates(searchText.trim(), pageNo, pageSize);
      } else if (statusFilter) {
        response = await getCandidatesByStatus(statusFilter, pageNo, pageSize);
      } else {
        response = await getAllCandidates(pageNo, pageSize, sortBy, sortDir);
      }

      setCandidates(response.data?.content || []);
      setTotalPages(response.data?.totalPages || 0);
      setTotalElements(response.data?.totalElements || 0);
    } catch (err) {
      setError(err.message || "Failed to load candidates.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCandidates();
  }, [pageNo, pageSize, sortBy, sortDir, searchText, statusFilter]);

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
          {/* sorting */}
          <div className="candidate-sorting">
            <label>Sort by: </label>

            <select
              value={sortBy}
              onChange={(e) => {
                setSortBy(e.target.value);
                setPageNo(0);
              }}
            >
              <option value="id">ID</option>
              <option value="firstName">First Name</option>
              <option value="lastName">Last Name</option>
              <option value="experience">Experience</option>
              <option value="status">Status</option>
            </select>

            <select
              value={sortDir}
              onChange={(e) => {
                setSortDir(e.target.value);
                setPageNo(0);
              }}
            >
              <option value="asc">Ascending</option>
              <option value="desc">Descending</option>
            </select>
          </div>

            {/* filters */}
          <div className="candidate-filters">
            <input
              type="text"
              placeholder="Search candidate name..."
              value={searchText}
              onChange={(e) => {
                setSearchText(e.target.value);
                setPageNo(0);
              }}
            />

            <select
              value={statusFilter}
              onChange={(e) => {
                setStatusFilter(e.target.value);
                setPageNo(0);
              }}
            >
              <option value="">All Statuses</option>
              <option value="APPLIED">APPLIED</option>
              <option value="SCREENING">SCREENING</option>
              <option value="SHORTLISTED">SHORTLISTED</option>
              <option value="INTERVIEW_SCHEDULED">INTERVIEW_SCHEDULED</option>
              <option value="INTERVIEWED">INTERVIEWED</option>
              <option value="OFFERED">OFFERED</option>
              <option value="HIRED">HIRED</option>
              <option value="REJECTED">REJECTED</option>
            </select>

            <button
              type="button"
              onClick={() => {
                setSearchText("");
                setStatusFilter("");
                setPageNo(0);
              }}
            >
              Reset
            </button>
          </div>

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

          <div className="candidate-pagination">
            <div className="candidate-page-size">
              <label>Rows per page: </label>

              <select
                value={pageSize}
                onChange={(e) => {
                  setPageSize(Number(e.target.value));
                  setPageNo(0);
                }}
              >
                <option value={5}>5</option>
                <option value={10}>10</option>
                <option value={20}>20</option>
              </select>
            </div>

            <button
              onClick={() => setPageNo((prev) => prev - 1)}
              disabled={pageNo === 0}
            >
              Previous
            </button>

            <span>
              Page {pageNo + 1} of {totalPages}
            </span>

            <button
              onClick={() => setPageNo((prev) => prev + 1)}
              disabled={pageNo >= totalPages - 1}
            >
              Next
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default CandidateList;
