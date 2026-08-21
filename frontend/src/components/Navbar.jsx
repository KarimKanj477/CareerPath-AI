import { NavLink, useNavigate } from "react-router-dom"
import { useAuth } from "../context/useAuth"

function Navbar() {
    const { isAuthenticated, user, logout } = useAuth()
    const navigate = useNavigate()

    function handleLogout() {
        logout()
        navigate("/login", { replace: true })
    }

    return (
        <nav className="navbar">
            <NavLink to="/dashboard" className="navbar-brand">
                CareerPath AI
            </NavLink>

            <div className="navbar-links">
                {isAuthenticated ? (
                    <>
                        <NavLink to="/dashboard">
                            Dashboard
                        </NavLink>

                        <NavLink to="/skills">
                            My Skills
                        </NavLink>

                        <NavLink to="/recommendations">
                            Recommendations
                        </NavLink>

                        <NavLink to="/roadmaps">
                            My Roadmaps
                        </NavLink>
                        {user?.roleName === "Admin" && (
                            <NavLink to="/admin">
                                Admin
                            </NavLink>
                        )}

                        <span className="navbar-user">
                            {user?.firstname
                                ?   user.firstname.charAt(0).toUpperCase() +
                                    user.firstname.slice(1).toLowerCase()
                                  : user?.email || "User"}

                            {user?.roleName && ` · ${user.roleName}`}
</span>


                        <button
                            type="button"
                            className="logout-button"
                            onClick={handleLogout}
                        >
                            Logout
                        </button>
                    </>
                ) : (
                    <>
                        <NavLink to="/login">
                            Login
                        </NavLink>

                        <NavLink to="/register">
                            Register
                        </NavLink>
                    </>
                )}
            </div>
        </nav>
    )
}

export default Navbar