import { useEffect, useState } from "react"
import { useAuth } from "../context/useAuth"
import { getMyRecommendations } from "../services/recommendationService"
import "./SkillGapAnalysisPage.css"

function SkillGapAnalysisPage() {
    const { token } = useAuth()

    const [recommendations, setRecommendations] = useState([])
    const [isLoading, setIsLoading] = useState(true)
    const [errorMessage, setErrorMessage] = useState("")

    useEffect(() => {
        async function loadRecommendations() {
            try {
                setIsLoading(true)
                setErrorMessage("")

                const data =
                    await getMyRecommendations(token)

                setRecommendations(
                    Array.isArray(data) ? data : [],
                )
            } catch (error) {
                setErrorMessage(
                    error.message ||
                    "Unable to load skill gap analysis",
                )
            } finally {
                setIsLoading(false)
            }
        }

        loadRecommendations()
    }, [token])

    const skillMap = {}

    recommendations.forEach((recommendation) => {
        recommendation.missingSkills.forEach((skill) => {
            if (!skillMap[skill.skillId]) {
                skillMap[skill.skillId] = {
                    skillId: skill.skillId,
                    skillName: skill.skillName,
                    count: 0,
                    careers: [],
                }
            }

            skillMap[skill.skillId].count += 1

            skillMap[skill.skillId].careers.push(
                recommendation.careerTitle,
            )
        })
    })

    const prioritySkills = Object.values(skillMap)
        .sort((skillA, skillB) => {
            if (skillB.count !== skillA.count) {
                return skillB.count - skillA.count
            }

            return skillA.skillName.localeCompare(
                skillB.skillName,
            )
        })

    const topPrioritySkill =
        prioritySkills.length > 0
            ? prioritySkills[0]
            : null

    function getPriorityPercentage(skill) {
        if (recommendations.length === 0) {
            return 0
        }

        return Math.round(
            (skill.count / recommendations.length) * 100,
        )
    }

    function getPriorityLevel(percentage) {
        if (percentage >= 60) {
            return "High Priority"
        }

        if (percentage >= 30) {
            return "Medium Priority"
        }

        return "Lower Priority"
    }

    function getPriorityClass(percentage) {
        if (percentage >= 60) {
            return "high"
        }

        if (percentage >= 30) {
            return "medium"
        }

        return "low"
    }

    if (isLoading) {
        return (
            <main className="skill-gap-page">
                <p>Loading skill gap analysis...</p>
            </main>
        )
    }

    if (errorMessage) {
        return (
            <main className="skill-gap-page">
                <p className="skill-gap-error">
                    {errorMessage}
                </p>
            </main>
        )
    }

    return (
        <main className="skill-gap-page">

            <section className="skill-gap-header">
                <p className="skill-gap-label">
                    CareerPath AI
                </p>

                <h1>Skill Gap Analysis</h1>

                <p>
                    Discover which missing skills appear most
                    often across your recommended careers and
                    identify what you should learn next.
                </p>
            </section>

            <section className="skill-gap-summary">

                <article className="skill-gap-summary-card">
                    <span>
                        Recommended Careers
                    </span>

                    <strong>
                        {recommendations.length}
                    </strong>

                    <p>
                        Careers included in the analysis
                    </p>
                </article>

                <article className="skill-gap-summary-card">
                    <span>
                        Unique Missing Skills
                    </span>

                    <strong>
                        {prioritySkills.length}
                    </strong>

                    <p>
                        Skills that could improve your matches
                    </p>
                </article>

                <article className="skill-gap-summary-card">
                    <span>
                        Top Priority Skill
                    </span>

                    <strong className="top-priority-skill">
                        {topPrioritySkill
                            ? topPrioritySkill.skillName
                            : "None"}
                    </strong>

                    <p>
                        Most common skill gap across careers
                    </p>
                </article>

            </section>

            <section className="priority-skills-section">

                <div className="priority-skills-header">
                    <div>
                        <p className="skill-gap-label">
                            Learning Priorities
                        </p>

                        <h2>Priority Skills</h2>
                    </div>

                    <p>
                        Skills are ranked by how often they
                        are missing from your recommended
                        careers.
                    </p>
                </div>

                {prioritySkills.length === 0 ? (
                    <div className="skill-gap-empty">
                        <h3>No skill gaps found</h3>

                        <p>
                            Your current skills cover all
                            recommended career requirements.
                        </p>
                    </div>
                ) : (
                    <div className="priority-skills-list">

                        {prioritySkills.map(
                            (skill, index) => {
                                const percentage =
                                    getPriorityPercentage(
                                        skill,
                                    )

                                const priorityLevel =
                                    getPriorityLevel(
                                        percentage,
                                    )

                                const priorityClass =
                                    getPriorityClass(
                                        percentage,
                                    )

                                return (
                                    <article
                                        className="priority-skill-card"
                                        key={skill.skillId}
                                    >

                                        <div className="priority-skill-top">

                                            <div>
                                                <span className="priority-rank">
                                                    #{index + 1}
                                                </span>

                                                <h3>
                                                    {
                                                        skill.skillName
                                                    }
                                                </h3>
                                            </div>

                                            <span
                                                className={`priority-badge ${priorityClass}`}
                                            >
                                                {priorityLevel}
                                            </span>

                                        </div>

                                        <p className="priority-description">
                                            Missing in{" "}
                                            <strong>
                                                {skill.count}
                                            </strong>{" "}
                                            of{" "}
                                            <strong>
                                                {
                                                    recommendations.length
                                                }
                                            </strong>{" "}
                                            recommended careers
                                        </p>

                                        <div className="priority-progress">

                                            <div className="priority-progress-info">
                                                <span>
                                                    Career impact
                                                </span>

                                                <strong>
                                                    {percentage}%
                                                </strong>
                                            </div>

                                            <div className="priority-progress-track">
                                                <div
                                                    className={`priority-progress-fill ${priorityClass}`}
                                                    style={{
                                                        width: `${percentage}%`,
                                                    }}
                                                />
                                            </div>

                                        </div>

                                        <div className="affected-careers">
                                            <span>
                                                Relevant Careers
                                            </span>

                                            <div className="affected-career-tags">
                                                {skill.careers.map(
                                                    (career) => (
                                                        <span
                                                            key={
                                                                career
                                                            }
                                                        >
                                                            {
                                                                career
                                                            }
                                                        </span>
                                                    ),
                                                )}
                                            </div>
                                        </div>

                                    </article>
                                )
                            },
                        )}

                    </div>
                )}

            </section>

        </main>
    )
}

export default SkillGapAnalysisPage