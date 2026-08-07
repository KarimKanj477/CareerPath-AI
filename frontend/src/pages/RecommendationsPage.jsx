import { useEffect, useState } from "react"
import { useAuth } from "../context/useAuth"
import { getMyRecommendations } from "../services/recommendationService"
import "./RecommendationsPage.css"
import { Link } from "react-router-dom"

function RecommendationsPage() {
    const { token } = useAuth()

    const [recommendations, setRecommendations] = useState([])
    const [isLoading, setIsLoading] = useState(true)
    const [errorMessage, setErrorMessage] = useState("")

    useEffect(() => {
        async function loadRecommendations() {
            try {
                setIsLoading(true)
                setErrorMessage("")

                const data = await getMyRecommendations(token)

                setRecommendations(data)
            } catch (error) {
                setErrorMessage(
                    error.message ||
                    "Unable to load career recommendations",
                )
            } finally {
                setIsLoading(false)
            }
        }

        loadRecommendations()
    }, [token])

    if (isLoading) {
        return (
            <main className="recommendations-page">
                <p>Loading recommendations...</p>
            </main>
        )
    }

    if (errorMessage) {
        return (
            <main className="recommendations-page">
                <p className="recommendations-error">
                    {errorMessage}
                </p>
            </main>
        )
    }

    return (
        <main className="recommendations-page">
            <section className="recommendations-header">
                <p className="recommendations-label">
                    CareerPath AI
                </p>

                <h1>Your Career Recommendations</h1>

                <p>
                    These recommendations are based on the
                    skills currently saved in your profile.
                </p>

                <Link
                    to="/skills"
                    className="recommendations-skills-link"
                >
                    Manage My Skills →
                </Link>
            </section>

            {recommendations.length === 0 ? (
                <section className="recommendations-empty">
                    <h2>No recommendations available</h2>
                    <p>
                        Add some skills to your profile and try
                        again.
                    </p>
                </section>
            ) : (
                <section className="recommendations-list">
                    {recommendations.map(
                        (recommendation, index) => (
                            <article
                                className="recommendation-card"
                                key={recommendation.careerId}
                            >
                                <div className="recommendation-top">
                                    <div>
                                        <span className="recommendation-rank">
                                            #{index + 1}
                                        </span>

                                        <h2>
                                            {
                                                recommendation.careerTitle
                                            }
                                        </h2>

                                        <p className="recommendation-category">
                                            {
                                                recommendation.careerCategory
                                            }
                                        </p>
                                    </div>

                                    <div className="match-score">
                                        <strong>
                                            {
                                                recommendation.matchPercentage
                                            }
                                            %
                                        </strong>
                                        <span>Match</span>
                                    </div>
                                </div>

                                <p className="recommendation-description">
                                    {
                                        recommendation.careerDescription
                                    }
                                </p>

                                <div className="career-details">
                                    <span>
                                        Demand:{" "}
                                        {
                                            recommendation.demandLevel
                                        }
                                    </span>

                                    <span>
                                        Required skills:{" "}
                                        {
                                            recommendation.totalRequiredSkills
                                        }
                                    </span>
                                </div>

                                <div className="skill-gap-grid">
                                    <div className="skill-section">
                                        <h3>Matched Skills</h3>

                                        {recommendation
                                            .matchedSkills.length ===
                                        0 ? (
                                            <p className="skill-empty">
                                                No matched skills yet.
                                            </p>
                                        ) : (
                                            <div className="skill-tags">
                                                {recommendation.matchedSkills.map(
                                                    (skill) => (
                                                        <span
                                                            className="skill-tag matched"
                                                            key={
                                                                skill.skillId
                                                            }
                                                        >
                                                            ✓{" "}
                                                            {
                                                                skill.skillName
                                                            }
                                                        </span>
                                                    ),
                                                )}
                                            </div>
                                        )}
                                    </div>

                                    <div className="skill-section">
                                        <h3>Missing Skills</h3>

                                        {recommendation
                                            .missingSkills.length ===
                                        0 ? (
                                            <p className="skill-empty">
                                                You already have all
                                                required skills.
                                            </p>
                                        ) : (
                                            <div className="skill-tags">
                                                {recommendation.missingSkills.map(
                                                    (skill) => (
                                                        <span
                                                            className="skill-tag missing"
                                                            key={
                                                                skill.skillId
                                                            }
                                                        >
                                                            {
                                                                skill.skillName
                                                            }
                                                        </span>
                                                    ),
                                                )}
                                            </div>
                                        )}
                                    </div>
                                </div>
                            </article>
                        ),
                    )}
                </section>
            )}
        </main>
    )
}

export default RecommendationsPage