import { useEffect, useState } from "react"
import { Link } from "react-router-dom"
import { useAuth } from "../context/useAuth"
import { getMySkills } from "../services/userSkillService"
import { getMyRecommendations } from "../services/recommendationService"
import { getMyRoadmaps } from "../services/roadmapService"
import { getMyProgress } from "../services/progressService"

function DashboardPage() {
    const { user, token } = useAuth()

    const [summary, setSummary] = useState({
        skillsCount: 0,
        roadmapsCount: 0,
        topMatch: null,
        overallProgress: null,
        completedSteps: 0,
        totalSteps: 0,
    })

    const [isSummaryLoading, setIsSummaryLoading] = useState(true)
    const [summaryError, setSummaryError] = useState("")

    useEffect(() => {
        async function loadDashboardSummary() {
            if (!token) {
                return
            }

            try {
                setIsSummaryLoading(true)
                setSummaryError("")

                const [
                    skillsResponse,
                    recommendationsResponse,
                    roadmapsResponse,
                    progressResponse,
                ] = await Promise.all([
                    getMySkills(token),
                    getMyRecommendations(token),
                    getMyRoadmaps(token),
                    getMyProgress(token),
                ])

                const skills = Array.isArray(skillsResponse?.data)
                    ? skillsResponse.data
                    : []

                const recommendations =
                    Array.isArray(recommendationsResponse)
                        ? recommendationsResponse
                        : []

                const roadmaps = Array.isArray(roadmapsResponse)
                    ? roadmapsResponse
                    : []
                const progressRecords = Array.isArray(progressResponse)
                    ? progressResponse
                    : []

                const progressByStep = {}

                progressRecords.forEach((progress) => {
                    progressByStep[progress.roadmapStepId] =
                        progress.progressPercentage ?? 0
                })

                const allSteps = roadmaps.flatMap(
                    (roadmap) =>
                        Array.isArray(roadmap.steps)
                            ? roadmap.steps
                            : [],
                )

                const totalSteps = allSteps.length

                const completedSteps = allSteps.filter(
                    (step) =>
                        (progressByStep[step.id] ?? 0) === 100,
                ).length

                const totalProgress = allSteps.reduce(
                    (sum, step) =>
                        sum + (progressByStep[step.id] ?? 0),
                    0,
                )

                const overallProgress =
                    totalSteps > 0
                        ? totalProgress / totalSteps
                        : null

                const topMatch =
                    recommendations.length > 0
                        ? Math.max(
                            ...recommendations.map(
                                (recommendation) =>
                                    Number(
                                        recommendation.matchPercentage,
                                    ) || 0,
                            ),
                        )
                        : null

                setSummary({
                    skillsCount: skills.length,
                    roadmapsCount: roadmaps.length,
                    topMatch,
                    overallProgress,
                    completedSteps,
                    totalSteps,
                })
            } catch (error) {
                setSummaryError(
                    error.message ||
                    "Unable to load dashboard summary",
                )
            } finally {
                setIsSummaryLoading(false)
            }
        }

        loadDashboardSummary()
    }, [token])

    const capitalizeName = (value) => {
        if (!value) return ""

        return value
            .split(" ")
            .map(
                (word) =>
                    word.charAt(0).toUpperCase() +
                    word.slice(1).toLowerCase(),
            )
            .join(" ")
    }

    const fullName =
        [
            capitalizeName(user?.firstname),
            capitalizeName(user?.lastname),
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
                className="dashboard-summary"
                aria-label="Career progress summary"
            >
                <div className="dashboard-summary-card">
                    <span>Skills Added</span>

                    <strong>
                        {isSummaryLoading
                            ? "..."
                            : summary.skillsCount}
                    </strong>

                    <p>Skills currently saved in your profile</p>
                </div>

                <div className="dashboard-summary-card">
                    <span>My Roadmaps</span>

                    <strong>
                        {isSummaryLoading
                            ? "..."
                            : summary.roadmapsCount}
                    </strong>

                    <p>Personalized learning roadmaps generated</p>
                </div>

                <div className="dashboard-summary-card">
                    <span>Top Career Match</span>

                    <strong>
                        {isSummaryLoading
                            ? "..."
                            : summary.topMatch !== null
                                ? `${summary.topMatch.toFixed(2)}%`
                                : "—"}
                    </strong>

                    <p>Your highest current career match</p>
                </div>

                <div className="dashboard-summary-card">
                    <span>Overall Progress</span>

                    <strong>
                        {isSummaryLoading
                            ? "..."
                            : summary.overallProgress !== null
                                ? `${summary.overallProgress.toFixed(0)}%`
                                : "—"}
                    </strong>

                    <p>
                        {summary.totalSteps > 0
                            ? `${summary.completedSteps} of ${summary.totalSteps} steps completed`
                            : "Generate a roadmap to start tracking"}
                    </p>
                </div>
            </section>

            {summaryError && (
                <p className="dashboard-summary-error">
                    Dashboard summary could not be loaded.
                </p>
            )}

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