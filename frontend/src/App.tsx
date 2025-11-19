// App.tsx
import React from "react";
import { Routes, Route, useNavigate } from "react-router-dom";

import LoginPage from "./login";
import RegisterPage from "./register";
import MainPage from "./MainPage";
import DoctorsPage from "./DoctorsPage";
import DoctorProfile from "./DoctorProfile/DoctorProfilePage";
import Dock from "./components/Dock";
import { useAuth } from "./AuthContext";
import {
  VscHome,
  VscArchive,
  VscAccount,
  VscSettingsGear,
} from "react-icons/vsc";

// If you want stricter typing for roles, you can import AuthUser and use it here.
import type { AuthUser } from "./AuthContext";
import DoctorProfile_outside from "./DoctorProfile/DoctorProfilePage_outside";
import DoctorProfileRoute from "./DoctorProfile/DoctorProfileRoute";

/** Guard a route by role */
function RoleRoute({
  user,
  allow,
  children,
}: {
  user: AuthUser; // ✅ align with context type
  allow: Array<AuthUser["role"]>; // ✅ align with context type
  children: React.ReactElement;
}) {
  if (!allow.includes(user.role))
    return <div style={{ padding: 24 }}>Forbidden</div>;
  return children;
}

const API_URL = import.meta.env.VITE_API_URL ?? "http://localhost:8080";

export default function App() {
  const { user, setUser, loading } = useAuth(); // ✅ get setUser (+ loading) from context
  const navigate = useNavigate();

  const items = [
    {
      icon: <VscHome size={27} />,
      label: "Home",
      onClick: () => navigate("/"),
    },
    {
      icon: <VscArchive size={27} />,
      label: "Doctors",
      onClick: () => navigate("/doctors"),
    },
    {
      icon: <VscAccount size={27} />,
      label: "Profile",
      onClick: () => navigate("/me"),
    },
    {
      icon: <VscSettingsGear size={27} />,
      label: "Settings",
      onClick: () => alert("Settings"),
    },
  ];

  async function handleLogout() {
    await fetch(`${API_URL}/api/auth/logout`, {
      method: "POST",
      credentials: "include",
    });
    setUser(null); // ✅ update via context
    navigate("/");
  }

  // Optional: show a boot screen while context loads the session
  if (loading) return <div style={{ padding: 24 }}>Loading…</div>;

  return (
    <>
      {/* Show Dock only when logged in */}
      {user && (
        <Dock
          items={items}
          panelHeight={75}
          baseItemSize={70}
          magnification={90}
        />
      )}

      <Routes>
        {/* Public routes (when not logged in) */}
        {!user ? (
          <>
            <Route
              path="/register"
              element={
                <RegisterPage
                  onRegister={async (vals) => {
                    const res = await fetch(`${API_URL}/api/auth/register`, {
                      method: "POST",
                      headers: { "Content-Type": "application/json" },
                      credentials: "include",
                      body: JSON.stringify(vals),
                    });
                    if (!res.ok) throw new Error(await res.text());
                    // Optionally fetch /me or set returned user if API returns it
                    // const me = await (await fetch(`${API_URL}/api/auth/me`, { credentials: "include" })).json();
                    // setUser(me);
                    navigate("/");
                  }}
                  onNavigateToLogin={() => navigate("/")}
                />
              }
            />
            <Route
              path="/"
              element={
                <LoginPage
                  onNavigateToRegister={() => navigate("/register")}
                  onLoginSuccess={(u) => {
                    setUser(u); // ✅ update via context
                    if (u.role === "DOCTOR") navigate("/doctors");
                    else navigate("/");
                  }}
                />
              }
            />
          </>
        ) : (
          <>
            {/* Private/main area */}
            <Route
              path="/"
              element={<MainPage user={user} onLogout={handleLogout} />}
            />
            <Route path="/doctors" element={<DoctorsPage />} />
            <Route path="/doctor/:id" element={<DoctorProfileRoute />} />
            <Route
              path="/me"
              element={
                <RoleRoute user={user} allow={["DOCTOR"]}>
                  <DoctorProfile />
                </RoleRoute>
              }
            />
          </>
        )}

        {/* Fallback */}
        <Route
          path="*"
          element={<div style={{ padding: 24 }}>Not found</div>}
        />
      </Routes>
    </>
  );
}
