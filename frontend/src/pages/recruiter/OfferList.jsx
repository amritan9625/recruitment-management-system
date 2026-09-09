import { useEffect, useState } from "react";
import {
  getAllOffers,
  deleteOffer,
  getOfferById,
  updateOffer,
  getOffersBySalary,
  getOffersByCandidateName,
  getOffersByJobTitle,
  getOffersByStatus,
} from "../../services/offerService";
import OfferForm from "./OfferForm";
import OfferDetails from "./OfferDetails";

function OfferList() {
  const [offers, setOffers] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showForm, setShowForm] = useState(false);
  const [viewingOfferId, setViewingOfferId] = useState(null);
  const [editingOffer, setEditingOffer] = useState(null);
  const [updatingStatusId, setUpdatingStatusId] = useState(null);

  // Pagination
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  // Sorting
  const [sortBy, setSortBy] = useState("id");
  const [sortDir, setSortDir] = useState("asc");

  // Search / Filter inputs
  const [salaryFilter, setSalaryFilter] = useState("");
  const [submittedSalary, setSubmittedSalary] = useState("");

  const [candidateNameSearch, setCandidateNameSearch] = useState("");
  const [submittedCandidateName, setSubmittedCandidateName] = useState("");

  const [jobTitleSearch, setJobTitleSearch] = useState("");
  const [submittedJobTitle, setSubmittedJobTitle] = useState("");

  const [statusFilter, setStatusFilter] = useState("");

  const fetchOffers = async () => {
    try {
      setLoading(true);
      setError("");

      let response;

      // 1. Candidate name search
      if (submittedCandidateName) {
        response = await getOffersByCandidateName(
          submittedCandidateName,
          pageNo,
          pageSize,
        );
      }

      // 2. Job title search
      else if (submittedJobTitle) {
        response = await getOffersByJobTitle(
          submittedJobTitle,
          pageNo,
          pageSize,
        );
      }

      // 3. Salary filter
      else if (submittedSalary) {
        response = await getOffersBySalary(submittedSalary, pageNo, pageSize);
      }

      // 4. Status filter
      else if (statusFilter) {
        response = await getOffersByStatus(statusFilter, pageNo, pageSize);
      }

      // 5. Normal pagination + sorting
      else {
        response = await getAllOffers(pageNo, pageSize, sortBy, sortDir);
      }

      const pageData = response.data;

      setOffers(pageData?.content || []);
      setTotalPages(pageData?.totalPages || 0);
      setTotalElements(pageData?.totalElements || 0);
    } catch (err) {
      setError(err.message || "Failed to load offers.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchOffers();
  }, [
    pageNo,
    pageSize,
    sortBy,
    sortDir,
    submittedSalary,
    submittedCandidateName,
    submittedJobTitle,
    statusFilter,
  ]);

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this offer?",
    );

    if (!confirmed) return;

    try {
      setError("");

      await deleteOffer(id);
      await fetchOffers();
    } catch (err) {
      setError(err.message || "Failed to delete offer.");
    }
  };

  const handleEdit = async (id) => {
    try {
      setError("");

      const response = await getOfferById(id);

      setEditingOffer(response.data);
      setShowForm(true);
    } catch (err) {
      setError(err.message || "Failed to load offer for editing.");
    }
  };

  const handleStatusChange = async (offer, status) => {
    try {
      setError("");
      setUpdatingStatusId(offer.id);

      const offerData = {
        salary: offer.salary,
        joiningDate: offer.joiningDate,
        status,
        candidateId: offer.candidateId,
        jobId: offer.jobId,
      };

      await updateOffer(offer.id, offerData);
      await fetchOffers();
    } catch (err) {
      setError(err.message || "Failed to update offer status.");
    } finally {
      setUpdatingStatusId(null);
    }
  };

  const handleSalarySearch = () => {
    setPageNo(0);
    setSubmittedSalary(salaryFilter.trim());
  };

  const handleCandidateSearch = () => {
    setPageNo(0);
    setSubmittedCandidateName(candidateNameSearch.trim());
  };

  const handleJobSearch = () => {
    setPageNo(0);
    setSubmittedJobTitle(jobTitleSearch.trim());
  };

  const handleReset = () => {
    setSalaryFilter("");
    setSubmittedSalary("");

    setCandidateNameSearch("");
    setSubmittedCandidateName("");

    setJobTitleSearch("");
    setSubmittedJobTitle("");

    setStatusFilter("");

    setSortBy("id");
    setSortDir("asc");

    setPageNo(0);
  };

  const handleStatusFilterChange = (event) => {
    setPageNo(0);
    setStatusFilter(event.target.value);
  };

  const handleSortChange = (event) => {
    setPageNo(0);
    setSortBy(event.target.value);
  };

  const handleSortDirectionChange = (event) => {
    setPageNo(0);
    setSortDir(event.target.value);
  };

  const handlePageSizeChange = (event) => {
    setPageNo(0);
    setPageSize(Number(event.target.value));
  };

  if (loading) {
    return (
      <div className="offer-page">
        <h1>Offers</h1>
        <p>Loading offers...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="offer-page">
        <h1>Offers</h1>
        <p className="offer-error">{error}</p>
      </div>
    );
  }

  if (viewingOfferId) {
    return (
      <OfferDetails
        offerId={viewingOfferId}
        onBack={() => setViewingOfferId(null)}
      />
    );
  }

  return (
    <div className="offer-page">
      {/* Header */}
      <div className="offer-page-header">
        <div>
          <h1>Offers</h1>
          <p>Manage job offers in the recruitment system.</p>
        </div>

        <button
          className="offer-add-button"
          onClick={() => {
            setEditingOffer(null);
            setShowForm(true);
          }}
        >
          Add Offer
        </button>
      </div>

      {/* Search / Filter Controls */}
      <div className="offer-controls">
        {/* Candidate Name Search */}
        <div>
          <input
            type="text"
            placeholder="Search candidate name..."
            value={candidateNameSearch}
            onChange={(event) => {
              setCandidateNameSearch(event.target.value);
            }}
            onKeyDown={(event) => {
              if (event.key === "Enter") {
                handleCandidateSearch();
              }
            }}
          />

          <button type="button" onClick={handleCandidateSearch}>
            Search Candidate
          </button>
        </div>

        {/* Job Title Search */}
        <div>
          <input
            type="text"
            placeholder="Search job title..."
            value={jobTitleSearch}
            onChange={(event) => {
              setJobTitleSearch(event.target.value);
            }}
            onKeyDown={(event) => {
              if (event.key === "Enter") {
                handleJobSearch();
              }
            }}
          />

          <button type="button" onClick={handleJobSearch}>
            Search Job
          </button>
        </div>

        {/* Salary Filter */}
        <div>
          <input
            type="number"
            placeholder="Filter by salary..."
            value={salaryFilter}
            onChange={(event) => {
              setSalaryFilter(event.target.value);
            }}
            onKeyDown={(event) => {
              if (event.key === "Enter") {
                handleSalarySearch();
              }
            }}
          />

          <button type="button" onClick={handleSalarySearch}>
            Search Salary
          </button>
        </div>

        {/* Status Filter */}
        <div>
          <select value={statusFilter} onChange={handleStatusFilterChange}>
            <option value="">All Statuses</option>
            <option value="PENDING">PENDING</option>
            <option value="ACCEPTED">ACCEPTED</option>
            <option value="REJECTED">REJECTED</option>
          </select>
        </div>

        {/* Sorting */}
        <div>
          <select value={sortBy} onChange={handleSortChange}>
            <option value="id">ID</option>
            <option value="salary">Salary</option>
            <option value="joiningDate">Joining Date</option>
            <option value="candidateId">Candidate ID</option>
            <option value="jobId">Job ID</option>
            <option value="status">Status</option>
          </select>

          <select value={sortDir} onChange={handleSortDirectionChange}>
            <option value="asc">Ascending</option>
            <option value="desc">Descending</option>
          </select>
        </div>

        {/* Reset */}
        <button type="button" onClick={handleReset}>
          Reset
        </button>
      </div>

      {/* Form */}
      {showForm && (
        <OfferForm
          offer={editingOffer}
          onSuccess={() => {
            setShowForm(false);
            setEditingOffer(null);
            fetchOffers();
          }}
          onCancel={() => {
            setShowForm(false);
            setEditingOffer(null);
          }}
        />
      )}

      {/* Offer Table */}
      {offers.length === 0 ? (
        <div className="offer-empty">No offers found.</div>
      ) : (
        <>
          <div className="offer-table-container">
            <table className="offer-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Salary</th>
                  <th>Joining Date</th>
                  <th>Candidate ID</th>
                  <th>Job ID</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {offers.map((offer) => (
                  <tr key={offer.id}>
                    <td>{offer.id}</td>

                    <td>{offer.salary}</td>

                    <td>{offer.joiningDate}</td>

                    <td>{offer.candidateId}</td>

                    <td>{offer.jobId}</td>

                    <td>
                      <select
                        value={offer.status || "PENDING"}
                        onChange={(event) =>
                          handleStatusChange(offer, event.target.value)
                        }
                        disabled={updatingStatusId === offer.id}
                      >
                        <option value="PENDING">PENDING</option>

                        <option value="ACCEPTED">ACCEPTED</option>

                        <option value="REJECTED">REJECTED</option>
                      </select>
                    </td>

                    <td>
                      <button onClick={() => setViewingOfferId(offer.id)}>
                        View
                      </button>

                      <button onClick={() => handleEdit(offer.id)}>Edit</button>

                      <button onClick={() => handleDelete(offer.id)}>
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Pagination */}
          <div className="offer-pagination">
            <div>
              <label>Rows per page: </label>

              <select value={pageSize} onChange={handlePageSizeChange}>
                <option value={5}>5</option>
                <option value={10}>10</option>
                <option value={20}>20</option>
              </select>
            </div>

            <div>
              <span>Total Offers: {totalElements}</span>
            </div>

            <div>
              <button
                type="button"
                disabled={pageNo === 0}
                onClick={() => setPageNo(pageNo - 1)}
              >
                Previous
              </button>

              <span>
                Page {totalPages === 0 ? 0 : pageNo + 1} of {totalPages}
              </span>

              <button
                type="button"
                disabled={totalPages === 0 || pageNo >= totalPages - 1}
                onClick={() => setPageNo(pageNo + 1)}
              >
                Next
              </button>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default OfferList;
