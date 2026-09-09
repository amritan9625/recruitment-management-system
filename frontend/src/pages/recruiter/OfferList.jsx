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
import Pagination from "../../components/Pagination";
import SearchFilterBar from "../../components/SearchFilterBar";

function OfferList() {
  const [offers, setOffers] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showForm, setShowForm] = useState(false);
  const [viewingOfferId, setViewingOfferId] = useState(null);
  const [editingOffer, setEditingOffer] = useState(null);
  const [updatingStatusId, setUpdatingStatusId] = useState(null);

  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [sortBy, setSortBy] = useState("id");
  const [sortDir, setSortDir] = useState("asc");

  const [salary, setSalary] = useState("");
  const [submittedSalary, setSubmittedSalary] = useState("");

  const [candidateName, setCandidateName] = useState("");
  const [submittedCandidateName, setSubmittedCandidateName] = useState("");

  const [jobTitle, setJobTitle] = useState("");
  const [submittedJobTitle, setSubmittedJobTitle] = useState("");

  const [statusFilter, setStatusFilter] = useState("");

  const fetchOffers = async () => {
    try {
      setLoading(true);
      setError("");

      let response;

      if (submittedCandidateName.trim()) {
        response = await getOffersByCandidateName(
          submittedCandidateName.trim(),
          pageNo,
          pageSize,
        );
      } else if (submittedJobTitle.trim()) {
        response = await getOffersByJobTitle(
          submittedJobTitle.trim(),
          pageNo,
          pageSize,
        );
      } else if (submittedSalary !== "") {
        response = await getOffersBySalary(submittedSalary, pageNo, pageSize);
      } else if (statusFilter) {
        response = await getOffersByStatus(statusFilter, pageNo, pageSize);
      } else {
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

  const handleCandidateNameSearch = () => {
    setPageNo(0);

    setSubmittedCandidateName(candidateName.trim());

    setSubmittedJobTitle("");
    setJobTitle("");

    setSalary("");
    setStatusFilter("");
  };

  const handleJobTitleSearch = () => {
    setPageNo(0);

    setSubmittedJobTitle(jobTitle.trim());

    setSubmittedCandidateName("");
    setCandidateName("");

    setSalary("");
    setStatusFilter("");
  };

  const handleStatusFilterChange = (event) => {
    setPageNo(0);

    setStatusFilter(event.target.value);

    setCandidateName("");
    setSubmittedCandidateName("");

    setJobTitle("");
    setSubmittedJobTitle("");

    setSalary("");
    setSubmittedSalary("");
  };

  const handleSalarySearch = () => {
    setPageNo(0);
    setSubmittedSalary(salary.trim());

    setSubmittedCandidateName("");
    setCandidateName("");

    setSubmittedJobTitle("");
    setJobTitle("");

    setStatusFilter("");
  };

  const handleSortChange = (event) => {
    setPageNo(0);
    setSortBy(event.target.value);
  };

  const handleSortDirectionChange = (event) => {
    setPageNo(0);
    setSortDir(event.target.value);
  };

  const handlePageChange = (newPageNo) => {
    setPageNo(newPageNo);
  };

  const handlePageSizeChange = (newPageSize) => {
    setPageSize(newPageSize);
    setPageNo(0);
  };

  const handleReset = () => {
    setCandidateName("");
    setSubmittedCandidateName("");

    setJobTitle("");
    setSubmittedJobTitle("");

    setSalary("");
    setSubmittedSalary("");
    setStatusFilter("");

    setSortBy("id");
    setSortDir("asc");

    setPageSize(10);
    setPageNo(0);
  };

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

  const handleStatusUpdate = async (offer, status) => {
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

  if (viewingOfferId) {
    return (
      <OfferDetails
        offerId={viewingOfferId}
        onBack={() => setViewingOfferId(null)}
      />
    );
  }

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

        <button type="button" onClick={fetchOffers}>
          Try Again
        </button>
      </div>
    );
  }

  return (
    <div className="offer-page">
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

      <SearchFilterBar onReset={handleReset}>
        <div className="search-filter-group">
          <label>Candidate Name</label>
          <input
            type="text"
            placeholder="Search candidate..."
            value={candidateName}
            onChange={(event) => setCandidateName(event.target.value)}
            onKeyDown={(event) => {
              if (event.key === "Enter") {
                handleCandidateNameSearch();
              }
            }}
          />

          <button type="button" onClick={handleCandidateNameSearch}>
            Search
          </button>
        </div>

        <div className="search-filter-group">
          <label>Job Title</label>
          <input
            type="text"
            placeholder="Search job..."
            value={jobTitle}
            onChange={(event) => setJobTitle(event.target.value)}
            onKeyDown={(event) => {
              if (event.key === "Enter") {
                handleJobTitleSearch();
              }
            }}
          />

          <button type="button" onClick={handleJobTitleSearch}>
            Search
          </button>
        </div>

        <div className="search-filter-group">
          <label>Salary</label>
          <input
            type="number"
            min="0"
            placeholder="Salary"
            value={salary}
            onChange={(event) => setSalary(event.target.value)}
            onKeyDown={(event) => {
              if (event.key === "Enter") {
                handleSalarySearch();
              }
            }}
          />
          <button type="button" onClick={handleSalarySearch}>
            Search
          </button>
        </div>

        <div className="search-filter-group">
          <label>Status</label>
          <select value={statusFilter} onChange={handleStatusFilterChange}>
            <option value="">All Statuses</option>
            <option value="PENDING">PENDING</option>
            <option value="ACCEPTED">ACCEPTED</option>
            <option value="REJECTED">REJECTED</option>
          </select>
        </div>

        <div className="search-filter-group">
          <label>Sort By</label>
          <select value={sortBy} onChange={handleSortChange}>
            <option value="id">ID</option>
            <option value="salary">Salary</option>
            <option value="joiningDate">Joining Date</option>
            <option value="status">Status</option>
          </select>
        </div>

        <div className="search-filter-group">
          <label>Direction</label>
          <select value={sortDir} onChange={handleSortDirectionChange}>
            <option value="asc">Ascending</option>
            <option value="desc">Descending</option>
          </select>
        </div>
      </SearchFilterBar>

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
                          handleStatusUpdate(offer, event.target.value)
                        }
                        disabled={updatingStatusId === offer.id}
                      >
                        <option value="PENDING">PENDING</option>
                        <option value="ACCEPTED">ACCEPTED</option>
                        <option value="REJECTED">REJECTED</option>
                      </select>
                    </td>

                    <td>
                      <button
                        type="button"
                        onClick={() => setViewingOfferId(offer.id)}
                      >
                        View
                      </button>

                      <button
                        type="button"
                        onClick={() => handleEdit(offer.id)}
                      >
                        Edit
                      </button>

                      <button
                        type="button"
                        onClick={() => handleDelete(offer.id)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <Pagination
            pageNo={pageNo}
            pageSize={pageSize}
            totalPages={totalPages}
            totalElements={totalElements}
            onPageChange={handlePageChange}
            onPageSizeChange={handlePageSizeChange}
          />
        </>
      )}
    </div>
  );
}

export default OfferList;
