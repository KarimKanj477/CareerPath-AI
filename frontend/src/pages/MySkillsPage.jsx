import { useEffect, useState } from "react"
import { useAuth } from "../context/useAuth"
import {
    addUserSkill,
    getMySkills,
    updateUserSkill,
    deleteUserSkill,
} from "../services/userSkillService"
import { getAllSkills } from "../services/skillService"

function MySkillsPage() {
    const { token } = useAuth()

    const [skills, setSkills] = useState([])
    const [availableSkills, setAvailableSkills] = useState([])

    const [selectedSkillId, setSelectedSkillId] = useState("")
    const [selectedLevel, setSelectedLevel] = useState("")

    const [isLoading, setIsLoading] = useState(true)
    const [isSubmitting, setIsSubmitting] = useState(false)

    const [editingSkillId, setEditingSkillId] = useState(null)
    const [editingLevel, setEditingLevel] = useState("")
    const [isUpdating, setIsUpdating] = useState(false)

    const [deletingSkillId, setDeletingSkillId] = useState(null)

    const [errorMessage, setErrorMessage] = useState("")
    const [successMessage, setSuccessMessage] = useState("")

    useEffect(() => {
        async function loadPageData() {
            try {
                setIsLoading(true)
                setErrorMessage("")

                const userSkillsResponse = await getMySkills(token)
                const allSkillsResponse = await getAllSkills()

                setSkills(
                    Array.isArray(userSkillsResponse.data)
                        ? userSkillsResponse.data
                        : [],
                )

                setAvailableSkills(
                    Array.isArray(allSkillsResponse.data)
                        ? allSkillsResponse.data
                        : [],
                )
            } catch (error) {
                setErrorMessage(
                    error.message ||
                    "Unable to load the skills.",
                )
            } finally {
                setIsLoading(false)
            }
        }

        if (token) {
            loadPageData()
        }
    }, [token])

    async function handleAddSkill(event) {
        event.preventDefault()

        if (!selectedSkillId || !selectedLevel) {
            setErrorMessage(
                "Please select a skill and a level.",
            )
            return
        }

        try {
            setIsSubmitting(true)
            setErrorMessage("")
            setSuccessMessage("")

            const response = await addUserSkill(token, {
                skillId: Number(selectedSkillId),
                level: selectedLevel,
            })

            setSkills((currentSkills) => [
                ...currentSkills,
                response.data,
            ])

            setSelectedSkillId("")
            setSelectedLevel("")

            setSuccessMessage(
                "Skill added successfully.",
            )
        } catch (error) {
            setErrorMessage(
                error.message ||
                "Unable to add the skill.",
            )
        } finally {
            setIsSubmitting(false)
        }
    }
    function handleStartEdit(skill) {
        setEditingSkillId(skill.id)
        setEditingLevel(skill.level)
        setErrorMessage("")
        setSuccessMessage("")
    }

    function handleCancelEdit() {
        setEditingSkillId(null)
        setEditingLevel("")
    }

    async function handleUpdateSkill(skill) {
        if (!editingLevel) {
            setErrorMessage("Please select a level.")
            return
        }

        try {
            setIsUpdating(true)
            setErrorMessage("")
            setSuccessMessage("")

            const response = await updateUserSkill(
                token,
                skill.id,
                {
                    skillId: skill.skillId,
                    level: editingLevel,
                },
            )

            setSkills((currentSkills) =>
                currentSkills.map((currentSkill) =>
                    currentSkill.id === skill.id
                        ? response.data
                        : currentSkill,
                ),
            )

            setEditingSkillId(null)
            setEditingLevel("")

            setSuccessMessage(
                "Skill level updated successfully.",
            )
        } catch (error) {
            setErrorMessage(
                error.message ||
                "Unable to update the skill.",
            )
        } finally {
            setIsUpdating(false)
        }
    }

    async function handleDeleteSkill(skill) {
        const confirmed = window.confirm(
            `Are you sure you want to delete ${skill.skillName}?`,
        )

        if (!confirmed) {
            return
        }

        try {
            setDeletingSkillId(skill.id)
            setErrorMessage("")
            setSuccessMessage("")

            await deleteUserSkill(token, skill.id)

            setSkills((currentSkills) =>
                currentSkills.filter(
                    (currentSkill) =>
                        currentSkill.id !== skill.id,
                ),
            )

            setSuccessMessage(
                "Skill deleted successfully.",
            )
        } catch (error) {
            setErrorMessage(
                error.message ||
                "Unable to delete the skill.",
            )
        } finally {
            setDeletingSkillId(null)
        }
    }


    const skillsNotAddedYet = availableSkills.filter(
        (availableSkill) =>
            !skills.some(
                (userSkill) =>
                    userSkill.skillId === availableSkill.id,
            ),
    )

    if (isLoading) {
        return (
            <main className="skills-page">
                <p>Loading your skills...</p>
            </main>
        )
    }

    return (
        <main className="skills-page">
            <div className="skills-header">
                <div>
                    <h1>My Skills</h1>

                    <p>
                        Add and manage the skills in your
                        professional profile.
                    </p>
                </div>
            </div>

            <section className="skill-form-section">
                <h2>Add a Skill</h2>

                <form
                    className="skill-form"
                    onSubmit={handleAddSkill}
                >
                    <div className="skill-form-field">
                        <label htmlFor="skill">
                            Skill
                        </label>

                        <select
                            id="skill"
                            value={selectedSkillId}
                            onChange={(event) =>
                                setSelectedSkillId(
                                    event.target.value,
                                )
                            }
                        >
                            <option value="">
                                Select a skill
                            </option>

                            {skillsNotAddedYet.map((skill) => (
                                <option
                                    key={skill.id}
                                    value={skill.id}
                                >
                                    {skill.name}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="skill-form-field">
                        <label htmlFor="level">
                            Level
                        </label>

                        <select
                            id="level"
                            value={selectedLevel}
                            onChange={(event) =>
                                setSelectedLevel(
                                    event.target.value,
                                )
                            }
                        >
                            <option value="">
                                Select a level
                            </option>
                            <option value="Beginner">
                                Beginner
                            </option>
                            <option value="Intermediate">
                                Intermediate
                            </option>
                            <option value="Advanced">
                                Advanced
                            </option>
                        </select>
                    </div>

                    <button
                        type="submit"
                        className="primary-button"
                        disabled={
                            isSubmitting ||
                            skillsNotAddedYet.length === 0
                        }
                    >
                        {isSubmitting
                            ? "Adding..."
                            : "Add Skill"}
                    </button>
                </form>

                {skillsNotAddedYet.length === 0 && (
                    <p className="form-information">
                        All available skills have already been
                        added.
                    </p>
                )}
            </section>

            {errorMessage && (
                <p className="message error-message">
                    {errorMessage}
                </p>
            )}

            {successMessage && (
                <p className="message success-message">
                    {successMessage}
                </p>
            )}

            {skills.length === 0 && (
                <div className="empty-state">
                    <h2>No skills added yet</h2>

                    <p>
                        Select a skill and level using the form
                        above.
                    </p>
                </div>
            )}

            {skills.length > 0 && (
                <div className="skills-grid">
                    {skills.map((skill) => (
                        <article
                            className="skill-card"
                            key={skill.id}
                        >
                            <div className="skill-card-header">
                                <h2>{skill.skillName}</h2>

                                {editingSkillId !== skill.id && (
                                    <span className="skill-level">
                    {skill.level}
                </span>
                                )}
                            </div>

                            <p className="skill-category">
                                {skill.skillCategory || "Uncategorized"}
                            </p>

                            <p>
                                {skill.skillDescription ||
                                    "No description available."}
                            </p>

                            {editingSkillId === skill.id ? (
                                <div className="skill-edit-section">
                                    <label htmlFor={`level-${skill.id}`}>
                                        New level
                                    </label>

                                    <select
                                        id={`level-${skill.id}`}
                                        value={editingLevel}
                                        onChange={(event) =>
                                            setEditingLevel(event.target.value)
                                        }
                                    >
                                        <option value="Beginner">
                                            Beginner
                                        </option>

                                        <option value="Intermediate">
                                            Intermediate
                                        </option>

                                        <option value="Advanced">
                                            Advanced
                                        </option>
                                    </select>

                                    <div className="skill-actions">
                                        <button
                                            type="button"
                                            className="primary-button"
                                            disabled={isUpdating}
                                            onClick={() =>
                                                handleUpdateSkill(skill)
                                            }
                                        >
                                            {isUpdating
                                                ? "Saving..."
                                                : "Save"}
                                        </button>

                                        <button
                                            type="button"
                                            className="secondary-button"
                                            disabled={isUpdating}
                                            onClick={handleCancelEdit}
                                        >
                                            Cancel
                                        </button>
                                    </div>
                                </div>
                            ) : (
                                <div className="skill-actions">
                                    <button
                                        type="button"
                                        className="secondary-button"
                                        onClick={() =>
                                            handleStartEdit(skill)
                                        }
                                        disabled={deletingSkillId === skill.id}
                                    >
                                        Edit Level
                                    </button>

                                    <button
                                        type="button"
                                        className="danger-button"
                                        onClick={() =>
                                            handleDeleteSkill(skill)
                                        }
                                        disabled={deletingSkillId === skill.id}
                                    >
                                        {deletingSkillId === skill.id
                                            ? "Deleting..."
                                            : "Delete"}
                                    </button>
                                </div>
                            )}
                        </article>
                    ))}
                </div>
            )}
        </main>
    )
}

export default MySkillsPage
