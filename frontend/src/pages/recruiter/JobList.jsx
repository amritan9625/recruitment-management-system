import { useEffect, useState } from "react";
import { getAllJobs, getJobById, deleteJob } from "../../services/jobService";
import JobForm from "./JobForm";
import JobDetails from "./JobDetails";

function JobList() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [editingJob, setEditingJob] = useState(null);
  const [viewingJobId, setViewingJobId] = useState(null);
  const [updatingStatusId, setUpdatingStatusId] = useState(null);

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

  const handleEdit = async (id) => {
    try {
      setError("");

      const response = await getJobById(id);

      setEditingJob(response.data);
      setShowForm(true);
    } catch (err) {
      setError(err.message || "Failed to load job details.");
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this job?",
    );
    if (!confirmed) return;

    try {
      setError("");
      await deleteJob(id);
      await fetchJobs();
    } catch (err) {
      setError(err.message || "Failed to delete job.");
    }
  };

  useEffect(() => {
    fetchJobs();
  }, []);

  if (viewingJobId) {
    return (
      <JobDetails jobId={viewingJobId} onBack={() => setViewingJobId(null)} />
    );
  }

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

  const handleStatusChange = async (job, status) => {
    try {
      setError("");
      setUpdatingStatusId(job.id);

      const jobData = {
        title: job.title,
        description: job.description,
        location: job.location,
        salary: job.salary,
        jobType: job.jobType,
        status,
      };

      await updateJob(job.id, jobData);

      await fetchJobs();
    } catch (err) {
      setError(err.message || "Failed to update job status.");
    } finally {
      setUpdatingStatusId(null);
    }
  };

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
          job={editingJob}
          onSuccess={() => {
            setShowForm(false);
            setEditingJob(null);
            fetchJobs();
          }}
          onCancel={() => {
            setShowForm(false);
            setEditingJob(null);
          }}
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

                  <td>
                    <select
                      value={job.status || "OPEN"}
                      onChange={(event) =>
                        handleStatusChange(job, event.target.value)
                      }
                      disabled={updatingStatusId === job.id}
                    >
                      <option value="OPEN">OPEN</option>
                      <option value="CLOSED">CLOSED</option>
                    </select>
                  </td>

                  <td>
                    <button onClick={() => setViewingJobId(job.id)}>
                      View
                    </button>

                    <button onClick={() => handleEdit(job.id)}>Edit</button>

                    <button onClick={() => handleDelete(job.id)}>Delete</button>
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
