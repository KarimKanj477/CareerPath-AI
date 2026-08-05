import { useState } from "react"
import AuthContext from "./authContext"

function AuthProvider({ children }) {
    const [token, setToken] = useState(() =>
        localStorage.getItem("token"),
    )

    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem("user")

        if (!storedUser) {
            return null
        }

        try {
            return JSON.parse(storedUser)
        } catch {
            localStorage.removeItem("user")
            return null
        }
    })

    function saveAuthentication(authenticationData) {
        const newToken = authenticationData.token
        const newUser = authenticationData.user

        localStorage.setItem("token", newToken)
        localStorage.setItem(
            "user",
            JSON.stringify(newUser),
        )

        setToken(newToken)
        setUser(newUser)
    }

    function logout() {
        localStorage.removeItem("token")
        localStorage.removeItem("user")

        setToken(null)
        setUser(null)
    }

    const contextValue = {
        token,
        user,
        isAuthenticated: Boolean(token),
        saveAuthentication,
        logout,
    }

    return (
        <AuthContext.Provider value={contextValue}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider