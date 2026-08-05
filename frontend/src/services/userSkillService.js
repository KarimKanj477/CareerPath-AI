const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

async function sendAuthenticatedRequest(
    endpoint,
    token,
    options = {},
) {
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
            ...options.headers,
        },
    })

    const responseData = await response.json()

    if (!response.ok) {
        throw new Error(
            responseData.message ||
            "An error occurred while processing the request",
        )
    }

    return responseData
}

export function getMySkills(token) {
    return sendAuthenticatedRequest(
        "/user-skills/me",
        token,
        {
            method: "GET",
        },
    )
}

export function addUserSkill(token, skillData) {
    return sendAuthenticatedRequest(
        "/user-skills",
        token,
        {
            method: "POST",
            body: JSON.stringify(skillData),
        },
    )
}

export function updateUserSkill(
    token,
    userSkillId,
    skillData,
) {
    return sendAuthenticatedRequest(
        `/user-skills/${userSkillId}`,
        token,
        {
            method: "PUT",
            body: JSON.stringify(skillData),
        },
    )
}

export function deleteUserSkill(token, userSkillId) {
    return sendAuthenticatedRequest(
        `/user-skills/${userSkillId}`,
        token,
        {
            method: "DELETE",
        },
    )
}