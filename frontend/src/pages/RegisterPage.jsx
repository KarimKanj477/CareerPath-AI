import { useState } from "react"
import { Link } from "react-router-dom"
import { registerUser } from "../services/authService"

function RegisterPage() {
    const [formData, setFormData] = useState({
        firstname: "",
        lastname: "",
        email: "",
        password: "",
        experienceLevel: "",
    })

    const [message, setMessage] = useState("")
    const [isSubmitting, setIsSubmitting] = useState(false)

    function handleChange(event) {
        const { name, value } = event.target

        setFormData((currentData) => ({
            ...currentData,
            [name]: value,
        }))
    }

    async function handleSubmit(event) {
        event.preventDefault()
        setMessage("")
        setIsSubmitting(true)

        try {
            await registerUser(formData)

            setMessage(
                "Registration successful. You can now log in.",
            )

            setFormData({
                firstname: "",
                lastname: "",
                email: "",
                password: "",
                experienceLevel: "",
            })
        } catch (error) {
            setMessage(error.message)
        } finally {
            setIsSubmitting(false)
        }
    }

    return (
        <div>
            <h1>Create Account</h1>

            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="firstname">First name</label>

                    <input
                        id="firstname"
                        name="firstname"
                        type="text"
                        value={formData.firstname}
                        onChange={handleChange}
                        maxLength={100}
                        required
                    />
                </div>

                <div>
                    <label htmlFor="lastname">Last name</label>

                    <input
                        id="lastname"
                        name="lastname"
                        type="text"
                        value={formData.lastname}
                        onChange={handleChange}
                        maxLength={100}
                        required
                    />
                </div>

                <div>
                    <label htmlFor="email">Email</label>

                    <input
                        id="email"
                        name="email"
                        type="email"
                        value={formData.email}
                        onChange={handleChange}
                        maxLength={150}
                        required
                    />
                </div>

                <div>
                    <label htmlFor="password">Password</label>

                    <input
                        id="password"
                        name="password"
                        type="password"
                        value={formData.password}
                        onChange={handleChange}
                        minLength={8}
                        maxLength={255}
                        required
                    />
                </div>

                <div>
                    <label htmlFor="experienceLevel">
                        Experience level
                    </label>

                    <input
                        id="experienceLevel"
                        name="experienceLevel"
                        type="text"
                        value={formData.experienceLevel}
                        onChange={handleChange}
                        maxLength={50}
                        placeholder="Example: Beginner"
                    />
                </div>

                <button type="submit" disabled={isSubmitting}>
                    {isSubmitting ? "Creating account..." : "Register"}
                </button>
            </form>

            {message && <p>{message}</p>}

            <p>
                Already have an account?{" "}
                <Link to="/login">Login</Link>
            </p>
        </div>
    )
}

export default RegisterPage