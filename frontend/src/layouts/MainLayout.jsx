import Navbar from "../components/layout/Navbar";
import Sidebar from "../components/layout/Sidebar";

function MainLayout({ children }) {
  return (
    <div className="app-layout">
      <Navbar />

      <div className="app-body">
        <Sidebar />

        {/* pages */}
        <main className="main-content">{children}</main>
      </div>
    </div>
  );
}

export default MainLayout;
