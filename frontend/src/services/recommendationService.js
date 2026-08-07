const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

async function sendAuthenticatedRequest(
    endpoint,
    token,
    options = {},
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
        },
    )

    const responseData = await response.json()

    if (!response.ok) {
        throw new Error(
            responseData.message ||
            "Unable to load career recommendations",
        )
    }

    return responseData
}

export function getMyRecommendations(token) {
    return sendAuthenticatedRequest(
        "/recommendations/me",
        token,
        {
            method: "GET",
        },
    )
}