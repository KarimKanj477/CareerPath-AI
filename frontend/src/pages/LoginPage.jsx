import { useState } from "react"
import {Link,useLocation, useNavigate,} from "react-router-dom"
import { useAuth } from "../context/useAuth"
import { loginUser } from "../services/authService"


function LoginPage() {
    const location = useLocation()
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [message, setMessage] = useState("")
    const navigate = useNavigate()
    const { saveAuthentication } = useAuth()
    const [isSubmitting, setIsSubmitting] = useState(false)

    async function handleSubmit(event) {
        event.preventDefault()
        setMessage("")
        setIsSubmitting(true)

        try {
            const response = await loginUser({
                email,
                password,
            })

            const authenticationData = response.data

            if (!authenticationData?.token) {
                throw new Error(
                    "Authentication token was not returned",
                )
            }

            saveAuthentication(authenticationData)
            navigate("/dashboard")
        } catch (error) {
            setMessage(error.message)
        }finally {
            setIsSubmitting(false)
        }
    }

    return (
        <main className="auth-page">
            <section className="auth-card">
                <div className="auth-header">
                    <p>Welcome back</p>
                    <h1>Login to CareerPath AI</h1>
                    <p>
                        Continue building your personalized career roadmap.
                    </p>
                </div>

                {location.state?.message && (
                    <p className="form-message success-message">
                        {location.state.message}
                    </p>
                )}

                <form className="auth-form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="email">Email address</label>

                        <input
                            id="email"
                            type="email"
                            value={email}
                            onChange={(event) =>
                                setEmail(event.target.value)
                            }
                            placeholder="Enter your email"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Password</label>

                        <input
                            id="password"
                            type="password"
                            value={password}
                            onChange={(event) =>
                                setPassword(event.target.value)
                            }
                            placeholder="Enter your password"
                            required
                        />
                    </div>

                    <button className="primary-button" type="submit" disabled={isSubmitting}>
                        {isSubmitting ? "Logging in..." : "Login"}
                    </button>
                </form>

                {message && (
                    <p className="form-message error-message">
                        {message}
                    </p>
                )}

                <p className="auth-switch">
                    Do not have an account?{" "}
                    <Link to="/register">Create an account</Link>
                </p>
            </section>
        </main>
    )
}

export default LoginPage