// Every backend response is wrapped as { success, message, data }.
// This helper does the fetch, unwraps that envelope, and throws a plain
// Error with the backend's own message on failure so callers can just
// try/catch and show err.message directly.
async function request(path, options = {}) {
  let res;
  try {
    res = await fetch(`${window.API_BASE_URL}${path}`, {
      headers: { "Content-Type": "application/json" },
      ...options,
    });
  } catch (networkError) {
    throw new Error(
      `Can't reach the API at ${window.API_BASE_URL}. Is the backend running?`
    );
  }

  let body = null;
  try {
    body = await res.json();
  } catch {
    // No JSON body (e.g. a 204 or a proxy error page) — fall through.
  }

  if (!res.ok || (body && body.success === false)) {
    const message =
      (body && body.message) || `Request failed with status ${res.status}`;
    throw new Error(message);
  }

  if (body && Object.prototype.hasOwnProperty.call(body, "data")) {
    return body.data;
  }

  return body;
}

const CareerAPI = {
  getAll: () => request("/careers"),
  getById: (id) => request(`/careers/${id}`),
  search: (title) => request(`/careers/search?title=${encodeURIComponent(title)}`),
  create: (payload) =>
    request("/careers", { method: "POST", body: JSON.stringify(payload) }),
  update: (id, payload) =>
    request(`/careers/${id}`, { method: "PUT", body: JSON.stringify(payload) }),
  remove: (id) => request(`/careers/${id}`, { method: "DELETE" }),
};

const SkillAPI = {
  getAll: () => request("/skills"),
  getById: (id) => request(`/skills/${id}`),
  search: (name) => request(`/skills/search?name=${encodeURIComponent(name)}`),
  create: (payload) =>
    request("/skills", { method: "POST", body: JSON.stringify(payload) }),
  update: (id, payload) =>
    request(`/skills/${id}`, { method: "PUT", body: JSON.stringify(payload) }),
  remove: (id) => request(`/skills/${id}`, { method: "DELETE" }),
};
