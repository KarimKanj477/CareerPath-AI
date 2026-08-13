import { useEffect, useState } from "react"
import { useAuth } from "../context/useAuth"
import { getMyRoadmaps } from "../services/roadmapService"
import {
    getMyProgress,
    updateProgress,
} from "../services/progressService"
import "./RoadmapsPage.css"

function RoadmapsPage() {
    const { token } = useAuth()

    const [roadmaps, setRoadmaps] = useState([])
    const [progressByStep, setProgressByStep] = useState({})
    const [isLoading, setIsLoading] = useState(true)
    const [errorMessage, setErrorMessage] = useState("")
    const [progressError, setProgressError] = useState("")
    const [updatingStepId, setUpdatingStepId] = useState(null)

    useEffect(() => {
        async function loadRoadmaps() {
            try {
                setIsLoading(true)
                setErrorMessage("")

                const [roadmapData, progressData] =
                    await Promise.all([
                        getMyRoadmaps(token),
                        getMyProgress(token),
                    ])

                setRoadmaps(roadmapData)

                const progressMap = {}

                progressData.forEach((progress) => {
                    progressMap[progress.roadmapStepId] =
                        progress
                })

                setProgressByStep(progressMap)
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

    async function handleProgressUpdate(
        roadmapStepId,
        percentage
    ) {
        try {
            setUpdatingStepId(roadmapStepId)
            setProgressError("")

            const updatedProgress =
                await updateProgress(
                    token,
                    roadmapStepId,
                    percentage
                )

            setProgressByStep((currentProgress) => ({
                ...currentProgress,
                [roadmapStepId]: updatedProgress,
            }))
        } catch (error) {
            setProgressError(
                error.message ||
                "Unable to update progress"
            )
        } finally {
            setUpdatingStepId(null)
        }
    }

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
                    for your selected careers and track
                    your learning progress.
                </p>
            </section>

            {progressError && (
                <p className="roadmaps-error">
                    {progressError}
                </p>
            )}

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

                                    {roadmap.steps.map((step) => {

                                        const progress =
                                            progressByStep[
                                                step.id
                                                ]

                                        const percentage =
                                            progress
                                                ?.progressPercentage ??
                                            0

                                        const status =
                                            progress?.status ||
                                            step.status ||
                                            "Not Started"

                                        return (
                                            <div
                                                className="roadmap-step"
                                                key={step.id}
                                            >

                                                <div className="roadmap-step-number">
                                                    {
                                                        step.stepOrder
                                                    }
                                                </div>

                                                <div className="roadmap-step-content">

                                                    <h3>
                                                        {
                                                            step.title
                                                        }
                                                    </h3>

                                                    {step.skillName && (
                                                        <p className="roadmap-step-skill">
                                                            Skill:{" "}
                                                            {
                                                                step.skillName
                                                            }
                                                        </p>
                                                    )}

                                                    <p>
                                                        {
                                                            step.description
                                                        }
                                                    </p>

                                                    <span className="roadmap-step-status">
                                                        {status}
                                                    </span>

                                                    <div className="roadmap-progress">

                                                        <div className="roadmap-progress-header">
                                                            <strong>
                                                                Progress
                                                            </strong>

                                                            <span>
                                                                {
                                                                    percentage
                                                                }
                                                                %
                                                            </span>
                                                        </div>

                                                        <div className="roadmap-progress-bar">
                                                            <div
                                                                className="roadmap-progress-fill"
                                                                style={{
                                                                    width: `${percentage}%`,
                                                                }}
                                                            />
                                                        </div>

                                                        <div className="roadmap-progress-actions">

                                                            {[
                                                                0,
                                                                25,
                                                                50,
                                                                75,
                                                                100,
                                                            ].map(
                                                                (
                                                                    value
                                                                ) => (
                                                                    <button
                                                                        type="button"
                                                                        key={
                                                                            value
                                                                        }
                                                                        className={
                                                                            percentage ===
                                                                            value
                                                                                ? "progress-button active"
                                                                                : "progress-button"
                                                                        }
                                                                        disabled={
                                                                            updatingStepId ===
                                                                            step.id
                                                                        }
                                                                        onClick={() =>
                                                                            handleProgressUpdate(
                                                                                step.id,
                                                                                value
                                                                            )
                                                                        }
                                                                    >
                                                                        {
                                                                            value
                                                                        }
                                                                        %
                                                                    </button>
                                                                )
                                                            )}

                                                        </div>

                                                    </div>

                                                    {step.resources &&
                                                        step.resources
                                                            .length >
                                                        0 && (

                                                            <div className="roadmap-resources">

                                                                <h4>
                                                                    Learning
                                                                    Resources
                                                                </h4>

                                                                {step.resources.map(
                                                                    (
                                                                        resource
                                                                    ) => (

                                                                        <div
                                                                            className="roadmap-resource"
                                                                            key={
                                                                                resource.id
                                                                            }
                                                                        >

                                                                            <div>
                                                                                <strong>
                                                                                    {
                                                                                        resource.title
                                                                                    }
                                                                                </strong>

                                                                                <p>
                                                                                    {
                                                                                        resource.provider
                                                                                    }

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
                                                                                    href={
                                                                                        resource.url
                                                                                    }
                                                                                    target="_blank"
                                                                                    rel="noopener noreferrer"
                                                                                >
                                                                                    Open
                                                                                    Resource
                                                                                    →
                                                                                </a>
                                                                            )}

                                                                        </div>

                                                                    )
                                                                )}

                                                            </div>
                                                        )}

                                                </div>

                                            </div>
                                        )
                                    })}

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