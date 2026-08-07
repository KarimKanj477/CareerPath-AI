import { Navigate, useLocation } from "react-router-dom"
import { useAuth } from "../context/useAuth"
import Navbar from "../components/Navbar"

function ProtectedRoute({ children }) {
    const { isAuthenticated } = useAuth()
    const location = useLocation()

    if (!isAuthenticated) {
        return (
            <Navigate
                to="/login"
                replace
                state={{ from: location }}
            />
        )
    }

    return (
        <>
            <Navbar />
            {children}
        </>
    )
}

export default ProtectedRoute