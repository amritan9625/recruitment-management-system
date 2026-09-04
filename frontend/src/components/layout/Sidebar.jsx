import { Link } from "react-router-dom";

function Sidebar() {
  return (
    <aside className="sidebar">
      <nav>
        <Link to="/dashboard">Dashboard</Link>
        <Link to="/jobs">Jobs</Link>
        <Link to="/candidates">Candidates</Link>
        <Link to="/applications">Applications</Link>
        <Link to="/interviews">Interviews</Link>
        <Link to="/offers">Offers</Link>
      </nav>
    </aside>
  );
}

export default Sidebar;
