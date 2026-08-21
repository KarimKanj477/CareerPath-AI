import { Navigate } from "react-router-dom"
import { useAuth } from "../context/useAuth"

function AdminRoute({ children }) {
    const { user } = useAuth()

    if (user?.roleName !== "Admin") {
        return (
            <Navigate
                to="/dashboard"
                replace
            />
        )
    }

    return children
}

export default AdminRoute