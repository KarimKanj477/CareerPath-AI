import { useAuth } from "../context/useAuth"
import { Link } from "react-router-dom"

function DashboardPage() {
    const { user } = useAuth()

    const capitalizeName = (value) => {
        if (!value) return ""

        return value
            .split(" ")
            .map(
                (word) =>
                    word.charAt(0).toUpperCase() +
                    word.slice(1).toLowerCase()
            )
            .join(" ")
    }

    const fullName =
        [
            capitalizeName(user?.firstname),
            capitalizeName(user?.lastname)
        ]
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
                    Manage your skills, explore career recommendations and
                    follow your personalized learning roadmap.
                </p>
            </section>

            <section
                className="dashboard-grid"
                aria-label="CareerPath tools"
            >
                <Link
                    to="/skills"
                    className="dashboard-card dashboard-card-link"
                >
                    <h2>My Skills</h2>

                    <p>
                        Add and manage your current skills and experience
                        levels.
                    </p>

                    <span>Open My Skills →</span>
                </Link>

                <Link
                    to="/recommendations"
                    className="dashboard-card dashboard-card-link"
                >
                    <h2>Career Recommendations</h2>

                    <p>
                        Discover careers that match your current skills and
                        identify the skills you still need.
                    </p>

                    <span>View Recommendations →</span>
                </Link>

                <Link
                    to="/roadmaps"
                    className="dashboard-card dashboard-card-link"
                >
                    <h2>Learning Roadmap</h2>

                    <p>
                        Follow personalized learning steps for your selected
                        career.
                    </p>

                    <span>Open My Roadmaps →</span>
                </Link>

                <Link
                    to="/roadmaps"
                    className="dashboard-card dashboard-card-link"
                >
                    <h2>Progress Tracking</h2>

                    <p>
                        Track your learning progress and complete each roadmap
                        step toward your career goal.
                    </p>

                    <span>View My Progress →</span>
                </Link>
            </section>
        </main>
    )
}

export default DashboardPage