import { useEffect, useState } from "react";
import {
  getAllUsers,
  deleteUser,
  getUserById,
  getUsersByName,
  getUsersByEmail,
} from "../../services/userService";
import Pagination from "../../components/Pagination";
import SearchFilterBar from "../../components/SearchFilterBar";
import UserForm from "./UserForm";

function UserList() {
  const [users, setUsers] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [viewingUser, setViewingUser] = useState(null);

  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [sortBy, setSortBy] = useState("id");
  const [sortDir, setSortDir] = useState("asc");

  const [name, setName] = useState("");
  const [submittedName, setSubmittedName] = useState("");

  const [email, setEmail] = useState("");
  const [submittedEmail, setSubmittedEmail] = useState("");

  const [showForm, setShowForm] = useState(false);
  const [editingUser, setEditingUser] = useState(null);

  const fetchUsers = async () => {
    try {
      setLoading(true);
      setError("");

      let response;

      if (submittedName.trim()) {
        response = await getUsersByName(submittedName.trim(), pageNo, pageSize);
      } else if (submittedEmail.trim()) {
        response = await getUsersByEmail(
          submittedEmail.trim(),
          pageNo,
          pageSize,
        );
      } else {
        response = await getAllUsers(pageNo, pageSize, sortBy, sortDir);
      }

      const pageData = response.data;

      setUsers(pageData?.content || []);
      setTotalPages(pageData?.totalPages || 0);
      setTotalElements(pageData?.totalElements || 0);
    } catch (err) {
      setError(err.message || "Failed to load users.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, [pageNo, pageSize, sortBy, sortDir, submittedName, submittedEmail]);

  const handleNameSearch = () => {
    setPageNo(0);
    setSubmittedName(name.trim());

    setEmail("");
    setSubmittedEmail("");
  };

  const handleEmailSearch = () => {
    setPageNo(0);
    setSubmittedEmail(email.trim());

    setName("");
    setSubmittedName("");
  };

  const handleReset = () => {
    setName("");
    setSubmittedName("");

    setEmail("");
    setSubmittedEmail("");

    setSortBy("id");
    setSortDir("asc");

    setPageSize(10);
    setPageNo(0);
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

  const handleView = async (id) => {
    try {
      setError("");

      const response = await getUserById(id);

      setViewingUser(response.data);
    } catch (err) {
      setError(err.message || "Failed to load user details.");
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this user?",
    );

    if (!confirmed) {
      return;
    }

    try {
      setError("");

      await deleteUser(id);
      await fetchUsers();
    } catch (err) {
      setError(err.message || "Failed to delete user.");
    }
  };

  if (viewingUser) {
    return (
      <div className="user-page">
        <div className="user-page-header">
          <div>
            <h1>User Details</h1>
            <p>View user information.</p>
          </div>

          <button type="button" onClick={() => setViewingUser(null)}>
            Back
          </button>
        </div>

        <div className="user-details">
          <p>
            <strong>ID:</strong> {viewingUser.id}
          </p>

          <p>
            <strong>Name:</strong> {viewingUser.name}
          </p>

          <p>
            <strong>Email:</strong> {viewingUser.email}
          </p>

          <p>
            <strong>Phone:</strong> {viewingUser.phone}
          </p>

          <p>
            <strong>Role ID:</strong> {viewingUser.roleId}
          </p>
        </div>
      </div>
    );
  }

  if (loading) {
    return (
      <div className="user-page">
        <h1>Users</h1>
        <p>Loading users...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="user-page">
        <h1>Users</h1>

        <p className="user-error">{error}</p>

        <button type="button" onClick={fetchUsers}>
          Try Again
        </button>
      </div>
    );
  }

  return (
    <div className="user-page">
      <div className="user-page-header">
        <div>
          <h1>Users</h1>
          <p>Manage users in the recruitment system.</p>
        </div>

        <button
          type="button"
          className="user-add-button"
          onClick={() => {
            setEditingUser(null);
            setShowForm(true);
          }}
        >
          Add User
        </button>
      </div>

      <SearchFilterBar onReset={handleReset}>
        <div className="search-filter-group">
          <label>Name</label>

          <input
            type="text"
            placeholder="Search user..."
            value={name}
            onChange={(event) => setName(event.target.value)}
            onKeyDown={(event) => {
              if (event.key === "Enter") {
                handleNameSearch();
              }
            }}
          />

          <button type="button" onClick={handleNameSearch}>
            Search
          </button>
        </div>

        <div className="search-filter-group">
          <label>Email</label>

          <input
            type="text"
            placeholder="Search email..."
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            onKeyDown={(event) => {
              if (event.key === "Enter") {
                handleEmailSearch();
              }
            }}
          />

          <button type="button" onClick={handleEmailSearch}>
            Search
          </button>
        </div>

        <div className="search-filter-group">
          <label>Sort By</label>

          <select value={sortBy} onChange={handleSortChange}>
            <option value="id">ID</option>
            <option value="name">Name</option>
            <option value="email">Email</option>
            <option value="phone">Phone</option>
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
        <UserForm
          user={editingUser}
          onSuccess={() => {
            setShowForm(false);
            setEditingUser(null);
            fetchUsers();
          }}
          onCancel={() => {
            setShowForm(false);
            setEditingUser(null);
          }}
        />
      )}

      {users.length === 0 ? (
        <div className="user-empty">No users found.</div>
      ) : (
        <>
          <div className="user-table-container">
            <table className="user-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Role ID</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {users.map((user) => (
                  <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.name}</td>
                    <td>{user.email}</td>
                    <td>{user.phone}</td>
                    <td>{user.roleId}</td>

                    <td>
                      <button type="button" onClick={() => handleView(user.id)}>
                        View
                      </button>

                      <button
                        type="button"
                        onClick={async () => {
                          try {
                            setError("");

                            const response = await getUserById(user.id);

                            setEditingUser(response.data);
                            setShowForm(true);
                          } catch (err) {
                            setError(
                              err.message || "Failed to load user for editing.",
                            );
                          }
                        }}
                      >
                        Edit
                      </button>

                      <button
                        type="button"
                        onClick={() => handleDelete(user.id)}
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

export default UserList;
