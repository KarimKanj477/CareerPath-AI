const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

async function sendAuthenticatedRequest(
    endpoint,
    token,
    options = {}
) {
    const response = await fetch(
        `${API_BASE_URL}${endpoint}`,
        {
            ...options,
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
                ...options.headers,
            },
        }
    )

    const responseData = await response.json()

    if (!response.ok) {
        throw new Error(
            responseData.message ||
            "Unable to process progress request"
        )
    }

    return responseData
}

export function getProgressForStep(
    token,
    roadmapStepId
) {
    return sendAuthenticatedRequest(
        `/progress/steps/${roadmapStepId}`,
        token,
        {
            method: "GET",
        }
    )
}

export function updateProgress(
    token,
    roadmapStepId,
    progressPercentage
) {
    return sendAuthenticatedRequest(
        `/progress/steps/${roadmapStepId}`,
        token,
        {
            method: "PUT",
            body: JSON.stringify({
                progressPercentage,
            }),
        }
    )
}

export function getMyProgress(token) {
    return sendAuthenticatedRequest(
        "/progress/me",
        token,
        {
            method: "GET",
        }
    )
}