import { useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import { registerUser } from "../services/authService"

function RegisterPage() {
    const navigate = useNavigate()
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

            navigate("/login", {
                replace: true,
                state: {
                    message: "Registration successful. You can now log in.",
                },
            })
        } catch (error) {
            setMessage(error.message)
        } finally {
            setIsSubmitting(false)
        }
    }

    return (
        <main className="auth-page">
            <section className="auth-card register-card">
                <div className="auth-header">
                    <p>Start your journey</p>

                    <h1>Create your account</h1>

                    <p>
                        Build your profile and receive personalized career
                        guidance.
                    </p>
                </div>

                <form className="auth-form" onSubmit={handleSubmit}>
                    <div className="form-row">
                        <div className="form-group">
                            <label htmlFor="firstname">First name</label>

                            <input
                                id="firstname"
                                name="firstname"
                                type="text"
                                value={formData.firstname}
                                onChange={handleChange}
                                maxLength={100}
                                placeholder="Enter your first name"
                                required
                            />
                        </div>

                        <div className="form-group">
                            <label htmlFor="lastname">Last name</label>

                            <input
                                id="lastname"
                                name="lastname"
                                type="text"
                                value={formData.lastname}
                                onChange={handleChange}
                                maxLength={100}
                                placeholder="Enter your last name"
                                required
                            />
                        </div>
                    </div>

                    <div className="form-group">
                        <label htmlFor="email">Email address</label>

                        <input
                            id="email"
                            name="email"
                            type="email"
                            value={formData.email}
                            onChange={handleChange}
                            maxLength={150}
                            placeholder="Enter your email"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Password</label>

                        <input
                            id="password"
                            name="password"
                            type="password"
                            value={formData.password}
                            onChange={handleChange}
                            minLength={8}
                            maxLength={255}
                            placeholder="At least 8 characters"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="experienceLevel">
                            Experience level
                        </label>

                        <select
                            id="experienceLevel"
                            name="experienceLevel"
                            value={formData.experienceLevel}
                            onChange={handleChange}
                        >
                            <option value="">Select your level</option>
                            <option value="Beginner">Beginner</option>
                            <option value="Intermediate">
                                Intermediate
                            </option>
                            <option value="Advanced">Advanced</option>
                        </select>
                    </div>

                    <button
                        className="primary-button"
                        type="submit"
                        disabled={isSubmitting}
                    >
                        {isSubmitting
                            ? "Creating account..."
                            : "Create account"}
                    </button>
                </form>

                {message && (
                    <p className="form-message error-message">
                        {message}
                    </p>
                )}

                <p className="auth-switch">
                    Already have an account?{" "}
                    <Link to="/login">Login</Link>
                </p>
            </section>
        </main>
    )
}

export default RegisterPage