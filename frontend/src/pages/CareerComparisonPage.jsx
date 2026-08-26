import { useEffect, useState } from "react"
import { useAuth } from "../context/useAuth"
import { getMyRecommendations } from "../services/recommendationService"
import "./CareerComparisonPage.css"

function CareerComparisonPage() {
    const { token } = useAuth()

    const [recommendations, setRecommendations] = useState([])
    const [selectedCareerA, setSelectedCareerA] = useState("")
    const [selectedCareerB, setSelectedCareerB] = useState("")
    const [isLoading, setIsLoading] = useState(true)
    const [errorMessage, setErrorMessage] = useState("")

    useEffect(() => {
        async function loadRecommendations() {
            try {
                setIsLoading(true)
                setErrorMessage("")

                const data = await getMyRecommendations(token)

                setRecommendations(
                    Array.isArray(data) ? data : [],
                )
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

    const careerA = recommendations.find(
        (career) =>
            String(career.careerId) === selectedCareerA,
    )

    const careerB = recommendations.find(
        (career) =>
            String(career.careerId) === selectedCareerB,
    )

    const matchA = Number(
        careerA?.matchPercentage || 0,
    )

    const matchB = Number(
        careerB?.matchPercentage || 0,
    )

    const matchDifference = Math.abs(
        matchA - matchB,
    )

    let betterCareer = null

    if (careerA && careerB) {
        if (matchA > matchB) {
            betterCareer = careerA
        } else if (matchB > matchA) {
            betterCareer = careerB
        }
    }

    if (isLoading) {
        return (
            <main className="career-comparison-page">
                <p>Loading careers...</p>
            </main>
        )
    }

    if (errorMessage) {
        return (
            <main className="career-comparison-page">
                <p>{errorMessage}</p>
            </main>
        )
    }

    return (
        <main className="career-comparison-page">

            <section className="career-comparison-header">
                <p className="career-comparison-label">
                    CareerPath AI
                </p>

                <h1>Compare Careers</h1>

                <p>
                    Select two recommended careers to compare
                    them based on your current skills.
                </p>
            </section>

            <section className="career-comparison-selectors">

                <div className="career-selector">
                    <label htmlFor="career-a">
                        First Career
                    </label>

                    <select
                        id="career-a"
                        value={selectedCareerA}
                        onChange={(event) =>
                            setSelectedCareerA(
                                event.target.value,
                            )
                        }
                    >
                        <option value="">
                            Select a career
                        </option>

                        {recommendations.map((career) => (
                            <option
                                key={career.careerId}
                                value={career.careerId}
                                disabled={
                                    String(
                                        career.careerId,
                                    ) === selectedCareerB
                                }
                            >
                                {career.careerTitle}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="career-selector">
                    <label htmlFor="career-b">
                        Second Career
                    </label>

                    <select
                        id="career-b"
                        value={selectedCareerB}
                        onChange={(event) =>
                            setSelectedCareerB(
                                event.target.value,
                            )
                        }
                    >
                        <option value="">
                            Select a career
                        </option>

                        {recommendations.map((career) => (
                            <option
                                key={career.careerId}
                                value={career.careerId}
                                disabled={
                                    String(
                                        career.careerId,
                                    ) === selectedCareerA
                                }
                            >
                                {career.careerTitle}
                            </option>
                        ))}
                    </select>
                </div>

            </section>

            {careerA && careerB && (
                <>
                    <section className="career-comparison-results">

                        {/* FIRST CAREER */}
                        <article className="career-comparison-card">

                            {betterCareer?.careerId ===
                                careerA.careerId && (
                                    <span className="best-match-badge">
                                    Best Current Match
                                </span>
                                )}

                            <div className="comparison-card-header">

                                <div>
                                    <span className="comparison-career-category">
                                        {careerA.careerCategory}
                                    </span>

                                    <h2>
                                        {careerA.careerTitle}
                                    </h2>
                                </div>

                                <div className="comparison-match-score">
                                    <strong>
                                        {careerA.matchPercentage}%
                                    </strong>

                                    <span>Match</span>
                                </div>

                            </div>

                            <p className="comparison-description">
                                {careerA.careerDescription}
                            </p>

                            <div className="comparison-details">

                                <span>
                                    Demand:{" "}
                                    <strong>
                                        {careerA.demandLevel}
                                    </strong>
                                </span>

                                <span>
                                    Required Skills:{" "}
                                    <strong>
                                        {
                                            careerA.totalRequiredSkills
                                        }
                                    </strong>
                                </span>

                            </div>

                            <div className="comparison-skill-section">
                                <h3>Matched Skills</h3>

                                {careerA.matchedSkills.length ===
                                0 ? (
                                    <p>
                                        No matched skills yet.
                                    </p>
                                ) : (
                                    <div className="comparison-skill-tags">

                                        {careerA.matchedSkills.map(
                                            (skill) => (
                                                <span
                                                    key={
                                                        skill.skillId
                                                    }
                                                    className="comparison-skill-tag matched"
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

                            <div className="comparison-skill-section">
                                <h3>Missing Skills</h3>

                                {careerA.missingSkills.length ===
                                0 ? (
                                    <p>
                                        No missing skills.
                                    </p>
                                ) : (
                                    <div className="comparison-skill-tags">

                                        {careerA.missingSkills.map(
                                            (skill) => (
                                                <span
                                                    key={
                                                        skill.skillId
                                                    }
                                                    className="comparison-skill-tag missing"
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

                        </article>


                        {/* SECOND CAREER */}
                        <article className="career-comparison-card">

                            {betterCareer?.careerId ===
                                careerB.careerId && (
                                    <span className="best-match-badge">
                                    Best Current Match
                                </span>
                                )}

                            <div className="comparison-card-header">

                                <div>
                                    <span className="comparison-career-category">
                                        {careerB.careerCategory}
                                    </span>

                                    <h2>
                                        {careerB.careerTitle}
                                    </h2>
                                </div>

                                <div className="comparison-match-score">
                                    <strong>
                                        {careerB.matchPercentage}%
                                    </strong>

                                    <span>Match</span>
                                </div>

                            </div>

                            <p className="comparison-description">
                                {careerB.careerDescription}
                            </p>

                            <div className="comparison-details">

                                <span>
                                    Demand:{" "}
                                    <strong>
                                        {careerB.demandLevel}
                                    </strong>
                                </span>

                                <span>
                                    Required Skills:{" "}
                                    <strong>
                                        {
                                            careerB.totalRequiredSkills
                                        }
                                    </strong>
                                </span>

                            </div>

                            <div className="comparison-skill-section">
                                <h3>Matched Skills</h3>

                                {careerB.matchedSkills.length ===
                                0 ? (
                                    <p>
                                        No matched skills yet.
                                    </p>
                                ) : (
                                    <div className="comparison-skill-tags">

                                        {careerB.matchedSkills.map(
                                            (skill) => (
                                                <span
                                                    key={
                                                        skill.skillId
                                                    }
                                                    className="comparison-skill-tag matched"
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

                            <div className="comparison-skill-section">
                                <h3>Missing Skills</h3>

                                {careerB.missingSkills.length ===
                                0 ? (
                                    <p>
                                        No missing skills.
                                    </p>
                                ) : (
                                    <div className="comparison-skill-tags">

                                        {careerB.missingSkills.map(
                                            (skill) => (
                                                <span
                                                    key={
                                                        skill.skillId
                                                    }
                                                    className="comparison-skill-tag missing"
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

                        </article>

                    </section>

                    <section className="comparison-insight">

                        <span className="comparison-insight-label">
                            Comparison Insight
                        </span>

                        {betterCareer ? (
                            <>
                                <h2>
                                    Better Current Match:{" "}
                                    {
                                        betterCareer.careerTitle
                                    }
                                </h2>

                                <p>
                                    Based on your current
                                    skills,{" "}
                                    <strong>
                                        {
                                            betterCareer.careerTitle
                                        }
                                    </strong>{" "}
                                    has a higher match by{" "}
                                    <strong>
                                        {matchDifference.toFixed(
                                            2,
                                        )}{" "}
                                        percentage points
                                    </strong>
                                    . Review the matched and
                                    missing skills above to
                                    understand the difference.
                                </p>
                            </>
                        ) : (
                            <>
                                <h2>Equal Match</h2>

                                <p>
                                    Both careers currently
                                    have the same match
                                    percentage. Review their
                                    missing skills, required
                                    skills, and descriptions
                                    to decide which career
                                    better fits your interests.
                                </p>
                            </>
                        )}

                    </section>
                </>
            )}

        </main>
    )
}

export default CareerComparisonPage