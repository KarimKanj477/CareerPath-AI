import { useEffect, useState } from "react"
import { useAuth } from "../context/useAuth"
import { getMyRoadmaps } from "../services/roadmapService"
import "./RoadmapsPage.css"

function RoadmapsPage() {
    const { token } = useAuth()

    const [roadmaps, setRoadmaps] = useState([])
    const [isLoading, setIsLoading] = useState(true)
    const [errorMessage, setErrorMessage] = useState("")

    useEffect(() => {
        async function loadRoadmaps() {
            try {
                setIsLoading(true)
                setErrorMessage("")

                const data = await getMyRoadmaps(token)

                setRoadmaps(data)
            } catch (error) {
                setErrorMessage(
                    error.message ||
                    "Unable to load your roadmaps"
                )
            } finally {
                setIsLoading(false)
            }
        }

        loadRoadmaps()
    }, [token])

    if (isLoading) {
        return (
            <main className="roadmaps-page">
                <p>Loading roadmaps...</p>
            </main>
        )
    }

    if (errorMessage) {
        return (
            <main className="roadmaps-page">
                <p className="roadmaps-error">
                    {errorMessage}
                </p>
            </main>
        )
    }

    return (
        <main className="roadmaps-page">

            <section className="roadmaps-header">
                <p className="roadmaps-label">
                    CareerPath AI
                </p>

                <h1>My Learning Roadmaps</h1>

                <p>
                    Follow the personalized steps generated
                    for your selected careers.
                </p>
            </section>

            {roadmaps.length === 0 ? (
                <section className="roadmaps-empty">
                    <h2>No roadmaps yet</h2>

                    <p>
                        Generate a roadmap from your career
                        recommendations first.
                    </p>
                </section>
            ) : (
                <section className="roadmaps-list">

                    {roadmaps.map((roadmap) => (

                        <article
                            className="roadmap-card"
                            key={roadmap.id}
                        >

                            <div className="roadmap-card-header">

                                <div>
                                    <h2>
                                        {roadmap.title}
                                    </h2>

                                    <p className="roadmap-career">
                                        {roadmap.careerTitle}
                                    </p>
                                </div>

                                <span className="roadmap-status">
                                    {roadmap.status}
                                </span>

                            </div>

                            {roadmap.steps.length === 0 ? (

                                <p className="roadmap-empty-steps">
                                    No learning steps are required.
                                </p>

                            ) : (

                                <div className="roadmap-steps">

                                    {roadmap.steps.map((step) => (

                                        <div
                                            className="roadmap-step"
                                            key={step.id}
                                        >

                                            <div className="roadmap-step-number">
                                                {step.stepOrder}
                                            </div>

                                            <div className="roadmap-step-content">

                                                <h3>
                                                    {step.title}
                                                </h3>

                                                {step.skillName && (
                                                    <p className="roadmap-step-skill">
                                                        Skill:{" "}
                                                        {step.skillName}
                                                    </p>
                                                )}

                                                <p>
                                                    {step.description}
                                                </p>

                                                <span className="roadmap-step-status">
                                                    {step.status}
                                                </span>

                                                {step.resources && step.resources.length > 0 && (
                                                    <div className="roadmap-resources">

                                                        <h4>Learning Resources</h4>

                                                        {step.resources.map((resource) => (
                                                            <div
                                                                className="roadmap-resource"
                                                                key={resource.id}
                                                            >
                                                                <div>
                                                                    <strong>
                                                                        {resource.title}
                                                                    </strong>

                                                                    <p>
                                                                        {resource.provider}
                                                                        {resource.type
                                                                            ? ` • ${resource.type}`
                                                                            : ""}
                                                                        {resource.isFree
                                                                            ? " • Free"
                                                                            : ""}
                                                                    </p>
                                                                </div>

                                                                {resource.url && (
                                                                    <a
                                                                        href={resource.url}
                                                                        target="_blank"
                                                                        rel="noopener noreferrer"
                                                                    >
                                                                        Open Resource →
                                                                    </a>
                                                                )}
                                                            </div>
                                                        ))}

                                                    </div>
                                                )}

                                            </div>

                                        </div>

                                    ))}

                                </div>

                            )}

                        </article>

                    ))}

                </section>
            )}

        </main>
    )
}

export default RoadmapsPage