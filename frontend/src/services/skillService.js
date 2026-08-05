const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

async function sendRequest(endpoint, options = {}) {
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...options.headers,
        },
    })

    const responseData = await response.json()

    if (!response.ok) {
        throw new Error(
            responseData.message ||
            "Unable to retrieve the available skills",
        )
    }

    return responseData
}

export function getAllSkills() {
    return sendRequest("/skills", {
        method: "GET",
    })
}