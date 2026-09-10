import { useEffect, useState } from "react";
import { createUser, updateUser } from "../../services/userService";
import { getAllRoles } from "../../services/roleService";

function UserForm({ user, onSuccess, onCancel }) {
  const isEditing = Boolean(user);

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    roleId: "",
    password: "",
  });

  const [roles, setRoles] = useState([]);

  const [loading, setLoading] = useState(false);
  const [rolesLoading, setRolesLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadRoles = async () => {
      try {
        setRolesLoading(true);
        setError("");

        const response = await getAllRoles(0, 100, "id", "asc");

        setRoles(response.data?.content || []);
      } catch (err) {
        setError(err.message || "Failed to load roles.");
      } finally {
        setRolesLoading(false);
      }
    };

    loadRoles();
  }, []);

  useEffect(() => {
    if (user) {
      setFormData({
        name: user.name || "",
        email: user.email || "",
        phone: user.phone || "",
        roleId: user.roleId || "",
        password: "",
      });
    } else {
      setFormData({
        name: "",
        email: "",
        phone: "",
        roleId: "",
        password: "",
      });
    }
  }, [user]);

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

      if (!formData.name.trim()) {
        setError("Name is required.");
        return;
      }

      if (!formData.email.trim()) {
        setError("Email is required.");
        return;
      }

      if (!formData.phone.trim()) {
        setError("Phone number is required.");
        return;
      }

      if (!formData.roleId) {
        setError("Please select a role.");
        return;
      }

      if (!isEditing && !formData.password) {
        setError("Password is required.");
        return;
      }

      if (!isEditing && formData.password.length < 6) {
        setError("Password must be at least 6 characters.");
        return;
      }

      if (isEditing) {
        await updateUser(user.id, {
          name: formData.name.trim(),
          email: formData.email.trim(),
          phone: formData.phone.trim(),
          roleId: Number(formData.roleId),
        });
      } else {
        await createUser({
          name: formData.name.trim(),
          email: formData.email.trim(),
          phone: formData.phone.trim(),
          roleId: Number(formData.roleId),
          password: formData.password,
        });
      }

      onSuccess();
    } catch (err) {
      setError(err.message || "Failed to save user.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="user-form">
      <div className="user-form-header">
        <h2>{isEditing ? "Edit User" : "Add User"}</h2>
      </div>

      {error && <p className="user-error">{error}</p>}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="user-name">Name</label>

          <input
            id="user-name"
            name="name"
            type="text"
            value={formData.name}
            onChange={handleChange}
            placeholder="Enter name"
            minLength={3}
            maxLength={50}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="user-email">Email</label>

          <input
            id="user-email"
            name="email"
            type="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Enter email"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="user-phone">Phone</label>

          <input
            id="user-phone"
            name="phone"
            type="text"
            value={formData.phone}
            onChange={handleChange}
            placeholder="Enter phone number"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="user-role">Role</label>

          {rolesLoading ? (
            <p>Loading roles...</p>
          ) : (
            <select
              id="user-role"
              name="roleId"
              value={formData.roleId}
              onChange={handleChange}
              required
            >
              <option value="">Select Role</option>

              {roles.map((role) => (
                <option key={role.id} value={role.id}>
                  {role.roleName}
                </option>
              ))}
            </select>
          )}
        </div>

        {!isEditing && (
          <div className="form-group">
            <label htmlFor="user-password">Password</label>

            <input
              id="user-password"
              name="password"
              type="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Enter password"
              minLength={6}
              required
            />
          </div>
        )}

        <div className="user-form-actions">
          <button type="submit" disabled={loading || rolesLoading}>
            {loading ? "Saving..." : isEditing ? "Update User" : "Create User"}
          </button>

          <button type="button" onClick={onCancel} disabled={loading}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}

export default UserForm;
