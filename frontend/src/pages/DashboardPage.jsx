import { useAuth } from "../context/useAuth"
import { Link } from "react-router-dom"

function DashboardPage() {
    const { user } = useAuth()

    const fullName =
        [user?.firstname, user?.lastname]
            .filter(Boolean)
            .join(" ") ||
        user?.email ||
        "User"

    return (
        <main className="dashboard-page">
            <section className="dashboard-header">
                <p>Your career development space</p>

                <h1>Welcome, {fullName}</h1>

                <p>
                    Complete your profile, manage your skills and receive
                    personalized career recommendations.
                </p>
            </section>

            <section
                className="dashboard-grid"
                aria-label="CareerPath tools"
            >
                <article className="dashboard-card">
                    <h2>Career Profile</h2>

                    <p>
                        Add your experience level, interests and career goals.
                    </p>

                    <span>Profile module coming soon</span>
                </article>

                <Link
                    to="/skills"
                    className="dashboard-card dashboard-card-link"
                >
                    <h2>My Skills</h2>

                    <p>
                        Add your current skills and track the skills you need
                        to improve.
                    </p>

                    <span>Open My Skills →</span>
                </Link>

                <article className="dashboard-card">
                    <h2>Career Recommendations</h2>

                    <p>
                        Discover careers that match your profile and technical
                        abilities.
                    </p>

                    <Link to="/recommendations">
                        View Career Recommendations →
                    </Link>
                </article>

                <article className="dashboard-card">
                    <h2>Learning Roadmap</h2>

                    <p>
                        Follow a personalized roadmap to prepare for your
                        selected career.
                    </p>

                    <span>Roadmap module coming soon</span>
                </article>
            </section>
        </main>
    )
}

export default DashboardPage