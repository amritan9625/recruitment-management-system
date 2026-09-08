import { useEffect, useState } from "react";
import {
  getAllOffers,
  deleteOffer,
  getOfferById,
  updateOffer,
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

  const fetchOffers = async () => {
    try {
      setLoading(true);
      setError("");

      const response = await getAllOffers();

      setOffers(response.data?.content || []);
    } catch (err) {
      setError(err.message || "Failed to load offers.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchOffers();
  }, []);

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
      )}
    </div>
  );
}

export default OfferList;
