import { useEffect, useState } from "react";
import { getAllJobs } from "../../services/jobService";
import JobForm from "./JobForm";

function JobList() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);

  const fetchJobs = async () => {
    try {
      setLoading(true);
      setError("");

      const response = await getAllJobs();

      setJobs(response.data?.content || []);
    } catch (err) {
      setError(err.message || "Failed to load jobs.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchJobs();
  }, []);

  if (loading) {
    return (
      <div className="job-page">
        <h1>Jobs</h1>
        <p>Loading jobs...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="job-page">
        <h1>Jobs</h1>
        <p className="job-error">{error}</p>
      </div>
    );
  }

  return (
    <div className="job-page">
      <div className="job-page-header">
        <div>
          <h1>Jobs</h1>
          <p>Manage jobs in the recruitment system.</p>
        </div>

        <button className="job-add-button" onClick={() => setShowForm(true)}>
          Add Job
        </button>
      </div>

      {showForm && (
        <JobForm
          onSuccess={() => {
            setShowForm(false);
            fetchJobs();
          }}
          onCancel={() => setShowForm(false)}
        />
      )}

      {jobs.length === 0 ? (
        <div className="job-empty">No jobs found.</div>
      ) : (
        <div className="job-table-container">
          <table className="job-table">
            <thead>
              <tr>
                <th>Title</th>
                <th>Description</th>
                <th>Location</th>
                <th>Salary</th>
                <th>Job Type</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {jobs.map((job) => (
                <tr key={job.id}>
                  <td>{job.title}</td>

                  <td>{job.description}</td>

                  <td>{job.location}</td>

                  <td>{job.salary}</td>

                  <td>{job.jobType}</td>

                  <td>{job.status}</td>

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

export default JobList;
