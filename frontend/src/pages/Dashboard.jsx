import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import { ROLES } from "../utils/permissions";
import { getDashboard } from "../services/dashboardService";

function Dashboard() {
  const { role } = useAuth();

  const [dashboardData, setDashboardData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchDashboard = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await getDashboard();

        setDashboardData(response);
      } catch (err) {
        setError(err.message || "Failed to load dashboard data.");
      } finally {
        setLoading(false);
      }
    };

    fetchDashboard();
  }, []);

  const roleName = role ? role.charAt(0) + role.slice(1).toLowerCase() : "User";

  const dashboardCards = {
    [ROLES.ADMIN]: [
      {
        title: "Total Users",
        value: dashboardData?.totalUsers ?? 0,
      },
      {
        title: "Total Candidates",
        value: dashboardData?.totalCandidates ?? 0,
      },
      {
        title: "Total Jobs",
        value: dashboardData?.totalJobs ?? 0,
      },
      {
        title: "Total Applications",
        value: dashboardData?.totalApplications ?? 0,
      },
      {
        title: "Total Interviews",
        value: dashboardData?.totalInterviews ?? 0,
      },
      {
        title: "Total Offers",
        value: dashboardData?.totalOffers ?? 0,
      },
    ],

    [ROLES.RECRUITER]: [
      {
        title: "Total Candidates",
        value: dashboardData?.totalCandidates ?? 0,
      },
      {
        title: "Total Jobs",
        value: dashboardData?.totalJobs ?? 0,
      },
      {
        title: "Total Applications",
        value: dashboardData?.totalApplications ?? 0,
      },
      {
        title: "Total Interviews",
        value: dashboardData?.totalInterviews ?? 0,
      },
      {
        title: "Total Offers",
        value: dashboardData?.totalOffers ?? 0,
      },
    ],

    [ROLES.INTERVIEWER]: [
      {
        title: "Total Interviews",
        value: dashboardData?.totalInterviews ?? 0,
      },
      {
        title: "Total Candidates",
        value: dashboardData?.totalCandidates ?? 0,
      },
    ],

    [ROLES.CANDIDATE]: [
      {
        title: "Available Jobs",
        value: dashboardData?.totalJobs ?? 0,
      },
      {
        title: "Applications",
        value: dashboardData?.totalApplications ?? 0,
      },
      {
        title: "Interviews",
        value: dashboardData?.totalInterviews ?? 0,
      },
      {
        title: "Offers",
        value: dashboardData?.totalOffers ?? 0,
      },
    ],
  };

  const cards = dashboardCards[role] || [];

  if (loading) {
    return (
      <div className="dashboard">
        <div className="dashboard-header">
          <h1>Dashboard</h1>
        </div>

        <div className="dashboard-loading">Loading dashboard...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="dashboard">
        <div className="dashboard-header">
          <h1>Dashboard</h1>
        </div>

        <div className="dashboard-error">{error}</div>
      </div>
    );
  }

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

            <p className="dashboard-card-value">{card.value}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Dashboard;
