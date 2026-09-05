import { useAuth } from "../context/AuthContext";
import { ROLES } from "../utils/permissions";

function Dashboard() {
  const { role } = useAuth();

  const roleName = role ? role.charAt(0) + role.slice(1).toLowerCase() : "User";

  const dashboardCards = {
    [ROLES.ADMIN]: [
      {
        title: "Jobs",
        description: "Manage and view available jobs.",
      },
      {
        title: "Candidates",
        description: "Manage candidate information.",
      },
      {
        title: "Applications",
        description: "Track recruitment applications.",
      },
      {
        title: "Interviews",
        description: "Manage interview schedules and status.",
      },
      {
        title: "Offers",
        description: "Manage candidate offers.",
      },
      {
        title: "Users",
        description: "Manage system users.",
      },
      {
        title: "Roles",
        description: "Manage user roles and permissions.",
      },
    ],

    [ROLES.RECRUITER]: [
      {
        title: "Jobs",
        description: "Manage and view available jobs.",
      },
      {
        title: "Candidates",
        description: "Manage candidate information.",
      },
      {
        title: "Applications",
        description: "Track recruitment applications.",
      },
      {
        title: "Interviews",
        description: "Manage interview schedules and status.",
      },
      {
        title: "Offers",
        description: "Manage candidate offers.",
      },
    ],

    [ROLES.INTERVIEWER]: [
      {
        title: "Interviews",
        description: "View and manage assigned interviews.",
      },
      {
        title: "Candidates",
        description: "View candidates related to interviews.",
      },
    ],

    [ROLES.CANDIDATE]: [
      {
        title: "Jobs",
        description: "Browse available job opportunities.",
      },
      {
        title: "My Applications",
        description: "Track your job applications.",
      },
      {
        title: "My Interviews",
        description: "View your interview schedule and status.",
      },
      {
        title: "My Offers",
        description: "View your recruitment offers.",
      },
    ],
  };

  const cards = dashboardCards[role] || [];

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <h1>Dashboard</h1>

        <p>Welcome to the Recruitment Management System.</p>
      </div>

      <div className="dashboard-welcome">
        <h2>Welcome back!</h2>

        <p>
          You are logged in as <strong>{roleName}</strong>.
        </p>
      </div>

      <div className="dashboard-cards">
        {cards.map((card) => (
          <div className="dashboard-card" key={card.title}>
            <h3>{card.title}</h3>

            <p>{card.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Dashboard;
