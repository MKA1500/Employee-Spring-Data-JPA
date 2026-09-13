import { useState } from "react";
import "./App.css";
import { AuthProvider } from "./auth/AuthContext";
import { useAuth } from "./auth/useAuth";
import { LoginForm } from "./components/LoginForm";
import { EmployeesPanel } from "./components/EmployeesPanel";
import { EmployeeDetailsPanel } from "./components/EmployeeDetailsPanel";
import { EmployeeBenefitsPanel } from "./components/EmployeeBenefitsPanel";

type Tab = "employees" | "details" | "benefits";

function Dashboard() {
  const { username, logout } = useAuth();
  const [tab, setTab] = useState<Tab>("employees");

  return (
    <div className="app-shell">
      <header className="app-header">
        <h1>Employee API Tester</h1>
        <div className="session-info">
          <span>
            Signed in as <strong>{username}</strong>
          </span>
          <button type="button" onClick={logout}>
            Sign out
          </button>
        </div>
      </header>
      <nav className="tabs">
        <button
          className={tab === "employees" ? "active" : ""}
          onClick={() => setTab("employees")}
        >
          Employees
        </button>
        <button
          className={tab === "details" ? "active" : ""}
          onClick={() => setTab("details")}
        >
          Employee Details
        </button>
        <button
          className={tab === "benefits" ? "active" : ""}
          onClick={() => setTab("benefits")}
        >
          Employee Benefits
        </button>
      </nav>
      <main className="app-main">
        {tab === "employees" && <EmployeesPanel />}
        {tab === "details" && <EmployeeDetailsPanel />}
        {tab === "benefits" && <EmployeeBenefitsPanel />}
      </main>
    </div>
  );
}

function AuthGate() {
  const { username } = useAuth();
  return username ? <Dashboard /> : <LoginForm />;
}

function App() {
  return (
    <AuthProvider>
      <AuthGate />
    </AuthProvider>
  );
}

export default App;
