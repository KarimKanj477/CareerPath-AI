import { useEffect, useState } from "react"
import { useAuth } from "../context/useAuth"
import {
    getAllUsers,
    updateUserRole,
} from "../services/userService"
import { getAllRoles } from "../services/roleService"
import "./AdminPage.css"

function AdminPage() {
    const { token, user } = useAuth()

    const [users, setUsers] = useState([])
    const [roles, setRoles] = useState([])
    const [isLoading, setIsLoading] = useState(true)
    const [updatingUserId, setUpdatingUserId] = useState(null)
    const [errorMessage, setErrorMessage] = useState("")
    const [successMessage, setSuccessMessage] = useState("")

    useEffect(() => {
        async function loadAdminData() {
            try {
                setIsLoading(true)
                setErrorMessage("")

                const [usersResponse, rolesResponse] =
                    await Promise.all([
                        getAllUsers(token),
                        getAllRoles(token),
                    ])

                setUsers(
                    Array.isArray(usersResponse?.data)
                        ? usersResponse.data
                        : [],
                )

                setRoles(
                    Array.isArray(rolesResponse?.content)
                        ? rolesResponse.content
                        : [],
                )
            } catch (error) {
                setErrorMessage(
                    error.message ||
                    "Unable to load administration data",
                )
            } finally {
                setIsLoading(false)
            }
        }

        loadAdminData()
    }, [token])

    async function handleRoleChange(userId, roleId) {
        try {
            setUpdatingUserId(userId)
            setErrorMessage("")
            setSuccessMessage("")

            const response = await updateUserRole(
                token,
                userId,
                Number(roleId),
            )

            setUsers((currentUsers) =>
                currentUsers.map((currentUser) =>
                    currentUser.id === userId
                        ? response.data
                        : currentUser,
                ),
            )

            setSuccessMessage(
                "User role updated successfully.",
            )
        } catch (error) {
            setErrorMessage(
                error.message ||
                "Unable to update user role",
            )
        } finally {
            setUpdatingUserId(null)
        }
    }

    const totalUsers = users.length

    const regularUsers = users.filter(
        (currentUser) =>
            currentUser.roleName === "User",
    ).length

    const admins = users.filter(
        (currentUser) =>
            currentUser.roleName === "Admin",
    ).length

    const mentors = users.filter(
        (currentUser) =>
            currentUser.roleName === "Mentor",
    ).length

    function formatDate(date) {
        if (!date) return "—"

        return new Date(date).toLocaleDateString()
    }

    return (
        <main className="admin-page">
            <section className="admin-header">
                <p>Administration</p>

                <h1>Admin Dashboard</h1>

                <p>
                    Monitor registered users and manage
                    application roles.
                </p>
            </section>

            <section
                className="admin-summary"
                aria-label="User statistics"
            >
                <div className="admin-summary-card">
                    <span>Total Accounts</span>
                    <strong>
                        {isLoading ? "..." : totalUsers}
                    </strong>
                </div>

                <div className="admin-summary-card">
                    <span>Users</span>
                    <strong>
                        {isLoading ? "..." : regularUsers}
                    </strong>
                </div>

                <div className="admin-summary-card">
                    <span>Admins</span>
                    <strong>
                        {isLoading ? "..." : admins}
                    </strong>
                </div>

                <div className="admin-summary-card">
                    <span>Mentors</span>
                    <strong>
                        {isLoading ? "..." : mentors}
                    </strong>
                </div>
            </section>

            {successMessage && (
                <p className="admin-success">
                    {successMessage}
                </p>
            )}

            {errorMessage && (
                <p className="admin-error">
                    {errorMessage}
                </p>
            )}

            <section className="admin-users-section">
                <div className="admin-users-header">
                    <div>
                        <h2>Registered Users</h2>

                        <p>
                            View accounts and manage their
                            platform roles.
                        </p>
                    </div>
                </div>

                {isLoading ? (
                    <p>Loading users...</p>
                ) : users.length === 0 ? (
                    <p>No registered users found.</p>
                ) : (
                    <div className="admin-table-wrapper">
                        <table className="admin-users-table">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Experience</th>
                                <th>Role</th>
                                <th>Registered</th>
                            </tr>
                            </thead>

                            <tbody>
                            {users.map(
                                (currentUser) => {
                                    const isCurrentAdmin =
                                        currentUser.id === user?.id

                                    return (
                                        <tr
                                            key={
                                                currentUser.id
                                            }
                                        >
                                            <td>
                                                {
                                                    currentUser.firstname
                                                }{" "}
                                                {
                                                    currentUser.lastname
                                                }

                                                {isCurrentAdmin &&
                                                    " (You)"}
                                            </td>

                                            <td>
                                                {
                                                    currentUser.email
                                                }
                                            </td>

                                            <td>
                                                {currentUser.experienceLevel ||
                                                    "—"}
                                            </td>

                                            <td>
                                                {isCurrentAdmin ? (
                                                    <span
                                                        className={`admin-role-badge admin-role-${currentUser.roleName?.toLowerCase()}`}
                                                    >
                                                            {
                                                                currentUser.roleName
                                                            }
                                                        </span>
                                                ) : (
                                                    <select
                                                        className="admin-role-select"
                                                        value={
                                                            currentUser.roleId
                                                        }
                                                        disabled={
                                                            updatingUserId ===
                                                            currentUser.id
                                                        }
                                                        onChange={(
                                                            event,
                                                        ) =>
                                                            handleRoleChange(
                                                                currentUser.id,
                                                                event
                                                                    .target
                                                                    .value,
                                                            )
                                                        }
                                                    >
                                                        {roles.map(
                                                            (
                                                                role,
                                                            ) => (
                                                                <option
                                                                    key={
                                                                        role.id
                                                                    }
                                                                    value={
                                                                        role.id
                                                                    }
                                                                >
                                                                    {
                                                                        role.name
                                                                    }
                                                                </option>
                                                            ),
                                                        )}
                                                    </select>
                                                )}
                                            </td>

                                            <td>
                                                {formatDate(
                                                    currentUser.createdAt,
                                                )}
                                            </td>
                                        </tr>
                                    )
                                },
                            )}
                            </tbody>
                        </table>
                    </div>
                )}
            </section>
        </main>
    )
}

export default AdminPage