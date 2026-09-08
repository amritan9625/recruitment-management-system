import { useEffect, useState } from "react";
import { getOfferById } from "../../services/offerService";

function OfferDetails({ offerId, onBack }) {
  const [offer, setOffer] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchOffer = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await getOfferById(offerId);

        setOffer(response.data);
      } catch (err) {
        setError(err.message || "Failed to load offer details.");
      } finally {
        setLoading(false);
      }
    };

    fetchOffer();
  }, [offerId]);

  if (loading) {
    return (
      <div className="offer-page">
        <h1>Offer Details</h1>
        <p>Loading offer details...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="offer-page">
        <h1>Offer Details</h1>
        <p className="offer-error">{error}</p>

        <button onClick={onBack}>
          Back to Offers
        </button>
      </div>
    );
  }

  if (!offer) {
    return (
      <div className="offer-page">
        <h1>Offer Details</h1>
        <p>Offer not found.</p>

        <button onClick={onBack}>
          Back to Offers
        </button>
      </div>
    );
  }

  return (
    <div className="offer-page">
      <div className="offer-page-header">
        <div>
          <h1>Offer Details</h1>
          <p>View complete offer information.</p>
        </div>

        <button onClick={onBack}>
          Back to Offers
        </button>
      </div>

      <div className="offer-details">
        <p>
          <strong>Offer ID:</strong> {offer.id}
        </p>

        <p>
          <strong>Salary:</strong> {offer.salary}
        </p>

        <p>
          <strong>Joining Date:</strong> {offer.joiningDate}
        </p>

        <p>
          <strong>Candidate ID:</strong> {offer.candidateId}
        </p>

        <p>
          <strong>Job ID:</strong> {offer.jobId}
        </p>

        <p>
          <strong>Status:</strong> {offer.status}
        </p>
      </div>
    </div>
  );
}

export default OfferDetails;