import { NavLink } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import { PERMISSIONS } from "../../utils/permissions";

function Sidebar() {
  const { role } = useAuth();

  const navigationItems = [
    {
      label: "Dashboard",
      path: "/dashboard",
      permission: PERMISSIONS.DASHBOARD,
    },
    {
      label: "Users",
      path: "/users",
      permission: PERMISSIONS.USERS,
    },
    {
      label: "Roles",
      path: "/roles",
      permission: PERMISSIONS.ROLES,
    },
    {
      label: "Jobs",
      path: "/jobs",
      permission: PERMISSIONS.JOBS_VIEW,
    },
    {
      label: "Candidates",
      path: "/candidates",
      permission: PERMISSIONS.CANDIDATES_VIEW,
    },
    {
      label: "Applications",
      path: "/applications",
      permission: PERMISSIONS.APPLICATIONS,
    },
    {
      label: "Interviews",
      path: "/interviews",
      permission: PERMISSIONS.INTERVIEWS_VIEW,
    },
    {
      label: "Offers",
      path: "/offers",
      permission: PERMISSIONS.OFFERS,
    },
  ];

  const visibleItems = navigationItems.filter((item) =>
    item.permission.includes(role),
  );

  return (
    <aside className="sidebar">
      <nav>
        {visibleItems.map((item) => (
          <NavLink key={item.path} to={item.path}>
            {item.label}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}

export default Sidebar;
