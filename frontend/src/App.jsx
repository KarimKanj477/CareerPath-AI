import {
    Navigate,
    NavLink,
    Route,
    Routes,
} from "react-router-dom"

import LoginPage from "./pages/LoginPage"
import RegisterPage from "./pages/RegisterPage"
import DashboardPage from "./pages/DashboardPage"

function App() {
    return (
        <>
            <nav>
                <NavLink to="/login">Login</NavLink>
                {" | "}
                <NavLink to="/register">Register</NavLink>
                {" | "}
                <NavLink to="/dashboard">Dashboard</NavLink>
            </nav>

            <Routes>
                <Route
                    path="/"
                    element={<Navigate to="/login" replace />}
                />

                <Route path="/login" element={<LoginPage />} />

                <Route
                    path="/register"
                    element={<RegisterPage />}
                />

                <Route
                    path="/dashboard"
                    element={<DashboardPage />}
                />
            </Routes>
        </>
    )
}

export default App
