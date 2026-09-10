import { useEffect, useState } from "react";
import {
  getAllRoles,
  getRoleById,
  getRolesByName,
  deleteRole,
} from "../../services/roleService";
import Pagination from "../../components/Pagination";
import SearchFilterBar from "../../components/SearchFilterBar";
import RoleForm from "./RoleForm";

function RoleList() {
  const [roles, setRoles] = useState([]);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showForm, setShowForm] = useState(false);
  const [editingRole, setEditingRole] = useState(null);
  const [viewingRole, setViewingRole] = useState(null);

  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [sortBy, setSortBy] = useState("id");
  const [sortDir, setSortDir] = useState("asc");

  const [roleName, setRoleName] = useState("");
  const [submittedRoleName, setSubmittedRoleName] = useState("");

  const fetchRoles = async () => {
    try {
      setLoading(true);
      setError("");

      let response;

      if (submittedRoleName.trim()) {
        response = await getRolesByName(
          submittedRoleName.trim(),
          pageNo,
          pageSize,
        );
      } else {
        response = await getAllRoles(pageNo, pageSize, sortBy, sortDir);
      }

      const pageData = response.data;

      setRoles(pageData?.content || []);
      setTotalPages(pageData?.totalPages || 0);
      setTotalElements(pageData?.totalElements || 0);
    } catch (err) {
      setError(err.message || "Failed to load roles.");
      setRoles([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRoles();
  }, [pageNo, pageSize, sortBy, sortDir, submittedRoleName]);

  const handleRoleNameSearch = () => {
    setPageNo(0);
    setSubmittedRoleName(roleName.trim());
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
    setRoleName("");
    setSubmittedRoleName("");

    setSortBy("id");
    setSortDir("asc");

    setPageSize(10);
    setPageNo(0);

    setViewingRole(null);
  };

  const handleView = async (id) => {
    try {
      setError("");

      const response = await getRoleById(id);

      setViewingRole(response.data);
    } catch (err) {
      setError(err.message || "Failed to load role details.");
    }
  };

  const handleEdit = async (id) => {
    try {
      setError("");

      const response = await getRoleById(id);

      setEditingRole(response.data);
      setShowForm(true);
      setViewingRole(null);
    } catch (err) {
      setError(err.message || "Failed to load role for editing.");
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm(
      "Are you sure you want to delete this role?",
    );

    if (!confirmed) {
      return;
    }

    try {
      setError("");

      await deleteRole(id);

      await fetchRoles();
    } catch (err) {
      setError(err.message || "Failed to delete role.");
    }
  };

  const handleFormSuccess = async () => {
    setShowForm(false);
    setEditingRole(null);
    setPageNo(0);

    await fetchRoles();
  };

  const handleFormCancel = () => {
    setShowForm(false);
    setEditingRole(null);
  };

  if (loading) {
    return (
      <div className="role-page">
        <h1>Roles</h1>
        <p>Loading roles...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="role-page">
        <h1>Roles</h1>

        <p className="role-error">{error}</p>

        <button type="button" onClick={fetchRoles}>
          Try Again
        </button>
      </div>
    );
  }

  return (
    <div className="role-page">
      <div className="role-page-header">
        <div>
          <h1>Roles</h1>

          <p>Manage system roles and permissions.</p>
        </div>

        <button
          type="button"
          onClick={() => {
            setEditingRole(null);
            setShowForm(true);
            setViewingRole(null);
          }}
        >
          Add Role
        </button>
      </div>

      <SearchFilterBar onReset={handleReset}>
        <div className="search-filter-group">
          <label>Role Name</label>

          <input
            type="text"
            placeholder="Search role..."
            value={roleName}
            onChange={(event) => setRoleName(event.target.value)}
            onKeyDown={(event) => {
              if (event.key === "Enter") {
                handleRoleNameSearch();
              }
            }}
          />

          <button type="button" onClick={handleRoleNameSearch}>
            Search
          </button>
        </div>

        <div className="search-filter-group">
          <label>Sort By</label>

          <select value={sortBy} onChange={handleSortChange}>
            <option value="id">ID</option>
            <option value="roleName">Role Name</option>
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
        <RoleForm
          role={editingRole}
          onSuccess={handleFormSuccess}
          onCancel={handleFormCancel}
        />
      )}

      {viewingRole && (
        <div className="role-details">
          <h2>Role Details</h2>

          <p>
            <strong>ID:</strong> {viewingRole.id}
          </p>

          <p>
            <strong>Role Name:</strong> {viewingRole.roleName}
          </p>

          <p>
            <strong>Description:</strong> {viewingRole.description}
          </p>

          <button type="button" onClick={() => setViewingRole(null)}>
            Close
          </button>
        </div>
      )}

      {roles.length === 0 ? (
        <div className="role-empty">No roles found.</div>
      ) : (
        <>
          <div className="role-table-container">
            <table className="role-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Role Name</th>
                  <th>Description</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {roles.map((role) => (
                  <tr key={role.id}>
                    <td>{role.id}</td>

                    <td>{role.roleName}</td>

                    <td>{role.description}</td>

                    <td>
                      <button type="button" onClick={() => handleView(role.id)}>
                        View
                      </button>

                      <button type="button" onClick={() => handleEdit(role.id)}>
                        Edit
                      </button>

                      <button
                        type="button"
                        onClick={() => handleDelete(role.id)}
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

export default RoleList;
