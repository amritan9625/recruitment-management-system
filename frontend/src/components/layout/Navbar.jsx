import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";

function Navbar() {
    const { logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    return (
        <header className="navbar">
            <div className="navbar-brand">
                Recruitment Management System
            </div>

            <div className="navbar-actions">
                <span>Welcome</span>

                <button onClick={handleLogout}>
                    Logout
                </button>
            </div>
        </header>
    );
}

export default Navbar;