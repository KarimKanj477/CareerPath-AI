const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

async function sendRequest(endpoint, options) {
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
            "An error occurred while processing the request",
        )
    }

    return responseData
}

export function loginUser(loginData) {
    return sendRequest("/auth/login", {
        method: "POST",
        body: JSON.stringify(loginData),
    })
}

export function registerUser(registerData) {
    return sendRequest("/auth/register", {
        method: "POST",
        body: JSON.stringify(registerData),
    })
}