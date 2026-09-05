import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

function Navbar() {
  const { role, logout } = useAuth();

  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  const roleName = role ? role.charAt(0) + role.slice(1).toLowerCase() : "User";

  return (
    <header className="navbar">
      <div className="navbar-brand">Recruitment Management System</div>

      <div className="navbar-actions">
        <div className="navbar-user">
          <span className="navbar-welcome">Welcome, {roleName}</span>

          <span className="navbar-role">{role}</span>
        </div>

        <button className="logout-button" onClick={handleLogout}>
          Logout
        </button>
      </div>
    </header>
  );
}

export default Navbar;
