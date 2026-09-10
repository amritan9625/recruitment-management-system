import { useEffect, useState } from "react";
import { createRole, updateRole } from "../../services/roleService";

function RoleForm({ role, onSuccess, onCancel }) {
  const isEditing = Boolean(role);

  const [formData, setFormData] = useState({
    roleName: "",
    description: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    if (role) {
      setFormData({
        roleName: role.roleName || "",
        description: role.description || "",
      });
    } else {
      setFormData({
        roleName: "",
        description: "",
      });
    }
  }, [role]);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setLoading(true);
      setError("");

      if (!formData.roleName.trim()) {
        setError("Role name is required.");
        return;
      }

      if (!formData.description.trim()) {
        setError("Description is required.");
        return;
      }

      if (formData.description.trim().length < 10) {
        setError("Description must be at least 10 characters.");
        return;
      }

      const roleData = {
        roleName: formData.roleName.trim(),
        description: formData.description.trim(),
      };

      if (isEditing) {
        await updateRole(role.id, roleData);
      } else {
        await createRole(roleData);
      }

      onSuccess();
    } catch (err) {
      setError(err.message || "Failed to save role.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="role-form">
      <div className="role-form-header">
        <h2>{isEditing ? "Edit Role" : "Add Role"}</h2>
      </div>

      {error && <p className="role-error">{error}</p>}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="role-name">Role Name</label>

          <input
            id="role-name"
            name="roleName"
            type="text"
            value={formData.roleName}
            onChange={handleChange}
            placeholder="Enter role name"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="role-description">Description</label>

          <textarea
            id="role-description"
            name="description"
            value={formData.description}
            onChange={handleChange}
            placeholder="Enter role description"
            minLength={10}
            maxLength={300}
            rows={4}
            required
          />
        </div>

        <div className="role-form-actions">
          <button type="submit" disabled={loading}>
            {loading ? "Saving..." : isEditing ? "Update Role" : "Create Role"}
          </button>

          <button type="button" onClick={onCancel} disabled={loading}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}

export default RoleForm;
