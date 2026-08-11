import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import { useAuth } from "../context/useAuth"
import { getMyRecommendations } from "../services/recommendationService"
import { generateRoadmap } from "../services/roadmapService"
import "./RecommendationsPage.css"

function RecommendationsPage() {
    const { token } = useAuth()

    const [recommendations, setRecommendations] = useState([])
    const [isLoading, setIsLoading] = useState(true)
    const [errorMessage, setErrorMessage] = useState("")

    const [generatingCareerId, setGeneratingCareerId] =
        useState(null)

    const [roadmapMessage, setRoadmapMessage] =
        useState("")

    const [roadmapError, setRoadmapError] =
        useState("")

    useEffect(() => {
        async function loadRecommendations() {
            try {
                setIsLoading(true)
                setErrorMessage("")

                const data =
                    await getMyRecommendations(token)

                setRecommendations(data)
            } catch (error) {
                setErrorMessage(
                    error.message ||
                    "Unable to load career recommendations"
                )
            } finally {
                setIsLoading(false)
            }
        }

        loadRecommendations()
    }, [token])

    async function handleGenerateRoadmap(
        careerId,
        careerTitle
    ) {
        try {
            setGeneratingCareerId(careerId)
            setRoadmapMessage("")
            setRoadmapError("")

            await generateRoadmap(
                token,
                careerId
            )

            setRoadmapMessage(
                `Roadmap for ${careerTitle} is ready.`
            )
        } catch (error) {
            setRoadmapError(
                error.message ||
                "Unable to generate roadmap"
            )
        } finally {
            setGeneratingCareerId(null)
        }
    }

    if (isLoading) {
        return (
            <main className="recommendations-page">
                <p>
                    Loading recommendations...
                </p>
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

                <h1>
                    Your Career Recommendations
                </h1>

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

            {roadmapMessage && (
                <p className="roadmap-success-message">
                    {roadmapMessage}
                </p>
            )}

            {roadmapError && (
                <p className="roadmap-error-message">
                    {roadmapError}
                </p>
            )}

            {recommendations.length === 0 ? (
                <section className="recommendations-empty">
                    <h2>
                        No recommendations available
                    </h2>

                    <p>
                        Add some skills to your profile
                        and try again.
                    </p>
                </section>
            ) : (
                <section className="recommendations-list">

                    {recommendations.map(
                        (recommendation, index) => (

                            <article
                                className="recommendation-card"
                                key={
                                    recommendation.careerId
                                }
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

                                        <span>
                                            Match
                                        </span>

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

                                        <h3>
                                            Matched Skills
                                        </h3>

                                        {recommendation
                                            .matchedSkills.length ===
                                        0 ? (

                                            <p className="skill-empty">
                                                No matched
                                                skills yet.
                                            </p>

                                        ) : (

                                            <div className="skill-tags">

                                                {recommendation
                                                    .matchedSkills
                                                    .map(
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

                                                        )
                                                    )}

                                            </div>

                                        )}

                                    </div>

                                    <div className="skill-section">

                                        <h3>
                                            Missing Skills
                                        </h3>

                                        {recommendation
                                            .missingSkills.length ===
                                        0 ? (

                                            <p className="skill-empty">
                                                You already
                                                have all
                                                required
                                                skills.
                                            </p>

                                        ) : (

                                            <div className="skill-tags">

                                                {recommendation
                                                    .missingSkills
                                                    .map(
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

                                                        )
                                                    )}

                                            </div>

                                        )}

                                    </div>

                                </div>

                                <div className="recommendation-actions">

                                    {recommendation
                                        .missingSkills.length >
                                    0 ? (

                                        <button
                                            type="button"
                                            className="generate-roadmap-button"
                                            disabled={
                                                generatingCareerId ===
                                                recommendation.careerId
                                            }
                                            onClick={() =>
                                                handleGenerateRoadmap(
                                                    recommendation.careerId,
                                                    recommendation.careerTitle
                                                )
                                            }
                                        >

                                            {generatingCareerId ===
                                            recommendation.careerId
                                                ? "Generating..."
                                                : "Generate Roadmap"}

                                        </button>

                                    ) : (

                                        <span className="roadmap-not-needed">
                                            All required skills
                                            completed
                                        </span>

                                    )}

                                </div>

                            </article>

                        )
                    )}

                </section>
            )}

        </main>
    )
}

export default RecommendationsPage