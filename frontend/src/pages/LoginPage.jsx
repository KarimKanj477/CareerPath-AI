import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { loginUser } from "../services/authService"

function LoginPage() {
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [message, setMessage] = useState("")
    const navigate = useNavigate()

    async function handleSubmit(event) {
        event.preventDefault()
        setMessage("")

        try {
            const response = await loginUser({
                email,
                password,
            })

            const token = response.data?.token

            if (!token) {
                throw new Error("Authentication token was not returned")
            }

            localStorage.setItem("token", token)

            if (response.data?.user) {
                localStorage.setItem(
                    "user",
                    JSON.stringify(response.data.user),
                )
            }

            navigate("/dashboard")
        } catch (error) {
            setMessage(error.message)
        }
    }

    return (
        <div>
            <h1>Login</h1>

            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="email">Email</label>
                    <input
                        id="email"
                        type="email"
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                        required
                    />
                </div>

                <div>
                    <label htmlFor="password">Password</label>
                    <input
                        id="password"
                        type="password"
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                        required
                    />
                </div>

                <button type="submit">Login</button>
            </form>

            {message && <p>{message}</p>}
        </div>
    )
}

export default LoginPage