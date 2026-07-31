import { useNavigate } from "react-router-dom"
import { useAuth } from "../context/AuthContext"

function DashboardPage() {
    const { user, logout } = useAuth()
    const navigate = useNavigate()

    function handleLogout() {
        logout()
        navigate("/login", { replace: true })
    }

    return (
        <div>
            <h1>Dashboard</h1>

            <p>
                Welcome, {user?.email || "User"}
            </p>

            <button type="button" onClick={handleLogout}>
                Logout
            </button>
        </div>
    )
}

export default DashboardPage