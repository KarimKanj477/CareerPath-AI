// ---------------------------------------------------------
// Shared state
// ---------------------------------------------------------
let allCareers = [];
let allSkills = [];

const careerFilters = { query: "", demand: "ALL", category: "ALL" };
const skillFilters = { query: "", category: "ALL" };

// ---------------------------------------------------------
// Toasts
// ---------------------------------------------------------
function showToast(message, type = "default") {
  const rail = document.getElementById("toast-rail");
  const el = document.createElement("div");
  el.className = `toast${type !== "default" ? ` toast--${type}` : ""}`;
  el.textContent = message;
  rail.appendChild(el);
  setTimeout(() => el.remove(), 3500);
}

// ---------------------------------------------------------
// Generic modal helpers
// ---------------------------------------------------------
function openModal(id) {
  document.getElementById(id).hidden = false;
  document.body.style.overflow = "hidden";
}
function closeModal(id) {
  document.getElementById(id).hidden = true;
  document.body.style.overflow = "";
}

document.querySelectorAll(".cp-modal").forEach((modal) => {
  modal.querySelectorAll("[data-close]").forEach((btn) => {
    btn.addEventListener("click", () => closeModal(modal.id));
  });
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape" && !modal.hidden) closeModal(modal.id);
  });
});

function confirmAction(text, onConfirm) {
  document.getElementById("confirm-modal-text").textContent = text;
  const btn = document.getElementById("confirm-modal-confirm");
  const fresh = btn.cloneNode(true); // strip old listeners
  btn.replaceWith(fresh);
  fresh.addEventListener("click", async () => {
    closeModal("confirm-modal");
    await onConfirm();
  });
  openModal("confirm-modal");
}

function money(value) {
  if (value == null) return "—";
  return `$${Number(value).toLocaleString("en-US", { maximumFractionDigits: 0 })}/yr`;
}

function demandBadgeClass(level) {
  const l = (level || "").toUpperCase();
  if (l === "HIGH") return "badge--high";
  if (l === "LOW") return "badge--low";
  return "badge--medium";
}

function escapeHtml(str) {
  return String(str ?? "").replace(/[&<>"']/g, (c) => (
    { "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" }[c]
  ));
}

// ===========================================================
// CAREERS
// ===========================================================
async function loadCareers() {
  const grid = document.getElementById("career-grid");
  try {
    allCareers = await CareerAPI.getAll();
    populateCategoryOptions("career-category-filter", allCareers);
    renderCareers();
    updateHeroStats();
  } catch (err) {
    grid.innerHTML = `<div class="state-card state-card--error">Lost signal — ${escapeHtml(err.message)}</div>`;
  }
}

function populateCategoryOptions(selectId, items) {
  const select = document.getElementById(selectId);
  const current = select.value;
  const categories = [...new Set(items.map((i) => i.category).filter(Boolean))].sort();
  select.innerHTML =
    `<option value="ALL">All categories</option>` +
    categories.map((c) => `<option value="${escapeHtml(c)}">${escapeHtml(c)}</option>`).join("");
  if (categories.includes(current)) select.value = current;
}

function filteredCareers() {
  return allCareers.filter((c) => {
    const matchesQuery = c.title.toLowerCase().includes(careerFilters.query.toLowerCase());
    const matchesDemand = careerFilters.demand === "ALL" || (c.demandLevel || "").toUpperCase() === careerFilters.demand;
    const matchesCategory = careerFilters.category === "ALL" || c.category === careerFilters.category;
    return matchesQuery && matchesDemand && matchesCategory;
  });
}

function renderCareers() {
  const grid = document.getElementById("career-grid");
  const managing = document.getElementById("career-manage-toggle").checked;
  const items = filteredCareers();

  if (items.length === 0) {
    grid.innerHTML = `<div class="state-card">No routes match "${escapeHtml(careerFilters.query)}" yet — try another heading, or clear a filter.</div>`;
    return;
  }

  grid.innerHTML = items.map((c) => `
    <article class="card">
      <span class="card-stamp">${escapeHtml(c.category || "General")}</span>
      <h3 class="card-title">${escapeHtml(c.title)}</h3>
      <p class="card-desc">${escapeHtml(c.description || "No description logged yet.")}</p>
      <div class="card-meta">
        <span class="card-salary">${money(c.averageSalary)}</span>
        <span class="badge ${demandBadgeClass(c.demandLevel)}">${escapeHtml(c.demandLevel || "Medium")} demand</span>
      </div>
      <div class="card-actions">
        <button class="btn-view" data-view="${c.id}">View route</button>
        ${managing ? `
          <button class="btn-edit" data-edit="${c.id}">Edit</button>
          <button class="btn-delete" data-delete="${c.id}">Remove</button>
        ` : ""}
      </div>
    </article>
  `).join("");

  grid.querySelectorAll("[data-view]").forEach((btn) =>
    btn.addEventListener("click", () => viewCareer(Number(btn.dataset.view)))
  );
  grid.querySelectorAll("[data-edit]").forEach((btn) =>
    btn.addEventListener("click", () => editCareer(Number(btn.dataset.edit)))
  );
  grid.querySelectorAll("[data-delete]").forEach((btn) =>
    btn.addEventListener("click", () => deleteCareer(Number(btn.dataset.delete)))
  );
}

function viewCareer(id) {
  const c = allCareers.find((x) => x.id === id);
  if (!c) return;
  document.getElementById("career-modal-eyebrow").textContent = c.category || "Career";
  document.getElementById("career-modal-title").textContent = c.title;
  document.getElementById("career-modal-view").innerHTML = `
    <div class="modal-detail-row"><span>Average salary</span><span>${money(c.averageSalary)}</span></div>
    <div class="modal-detail-row"><span>Demand</span><span>${escapeHtml(c.demandLevel || "Medium")}</span></div>
    <p style="margin-top:1rem;color:var(--ink-soft)">${escapeHtml(c.description || "No description logged yet.")}</p>
  `;
  document.getElementById("career-modal-view").hidden = false;
  document.getElementById("career-modal-form").hidden = true;
  openModal("career-modal");
}

function editCareer(id) {
  const c = id ? allCareers.find((x) => x.id === id) : null;
  document.getElementById("career-modal-eyebrow").textContent = c ? "Edit career" : "New career";
  document.getElementById("career-modal-title").textContent = c ? c.title : "Chart a new career";
  document.getElementById("career-modal-view").hidden = true;

  const form = document.getElementById("career-modal-form");
  form.hidden = false;
  form.reset();
  document.getElementById("career-form-error").hidden = true;
  form.dataset.editId = c ? c.id : "";

  if (c) {
    form.title.value = c.title;
    form.category.value = c.category || "";
    form.averageSalary.value = c.averageSalary ?? "";
    form.demandLevel.value = (c.demandLevel || "MEDIUM").toUpperCase();
    form.description.value = c.description || "";
  }
  openModal("career-modal");
}

document.getElementById("career-modal-form").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;
  const errorEl = document.getElementById("career-form-error");
  errorEl.hidden = true;

  const payload = {
    title: form.title.value.trim(),
    category: form.category.value.trim() || null,
    averageSalary: form.averageSalary.value === "" ? null : Number(form.averageSalary.value),
    demandLevel: form.demandLevel.value,
    description: form.description.value.trim() || null,
  };

  try {
    if (form.dataset.editId) {
      await CareerAPI.update(Number(form.dataset.editId), payload);
      showToast("Career updated", "success");
    } else {
      await CareerAPI.create(payload);
      showToast("Career added to the catalogue", "success");
    }
    closeModal("career-modal");
    await loadCareers();
  } catch (err) {
    errorEl.textContent = err.message;
    errorEl.hidden = false;
  }
});

function deleteCareer(id) {
  const c = allCareers.find((x) => x.id === id);
  confirmAction(`Remove "${c ? c.title : "this career"}" from the catalogue?`, async () => {
    try {
      await CareerAPI.remove(id);
      showToast("Career removed", "success");
      await loadCareers();
    } catch (err) {
      showToast(err.message, "error");
    }
  });
}

document.getElementById("career-add-btn").addEventListener("click", () => editCareer(null));

document.getElementById("career-search").addEventListener("input", (e) => {
  careerFilters.query = e.target.value;
  renderCareers();
});

document.getElementById("career-demand-filter").addEventListener("click", (e) => {
  const btn = e.target.closest("[data-demand]");
  if (!btn) return;
  document.querySelectorAll("#career-demand-filter .chip").forEach((c) => c.classList.remove("is-active"));
  btn.classList.add("is-active");
  careerFilters.demand = btn.dataset.demand;
  renderCareers();
});

document.getElementById("career-category-filter").addEventListener("change", (e) => {
  careerFilters.category = e.target.value;
  renderCareers();
});

document.getElementById("career-manage-toggle").addEventListener("change", (e) => {
  document.getElementById("career-manage-note").hidden = !e.target.checked;
  renderCareers();
});

// ===========================================================
// SKILLS
// ===========================================================
async function loadSkills() {
  const grid = document.getElementById("skill-grid");
  try {
    allSkills = await SkillAPI.getAll();
    populateCategoryOptions("skill-category-filter", allSkills);
    renderSkills();
    updateHeroStats();
  } catch (err) {
    grid.innerHTML = `<div class="state-card state-card--error">Lost signal — ${escapeHtml(err.message)}</div>`;
  }
}

function filteredSkills() {
  return allSkills.filter((s) => {
    const matchesQuery = s.name.toLowerCase().includes(skillFilters.query.toLowerCase());
    const matchesCategory = skillFilters.category === "ALL" || s.category === skillFilters.category;
    return matchesQuery && matchesCategory;
  });
}

function renderSkills() {
  const grid = document.getElementById("skill-grid");
  const managing = document.getElementById("skill-manage-toggle").checked;
  const items = filteredSkills();

  if (items.length === 0) {
    grid.innerHTML = `<div class="state-card">Nothing in the field notes matches "${escapeHtml(skillFilters.query)}" yet.</div>`;
    return;
  }

  grid.innerHTML = items.map((s) => `
    <article class="tag-card">
      <div class="tag-card-top">
        <span class="tag-card-name">${escapeHtml(s.name)}</span>
        <span class="tag-card-category">${escapeHtml(s.category || "General")}</span>
      </div>
      <p class="tag-card-desc">${escapeHtml(s.description || "No description logged yet.")}</p>
      ${managing ? `
        <div class="tag-card-actions">
          <button class="btn-edit" data-edit="${s.id}">Edit</button>
          <button class="btn-delete" data-delete="${s.id}">Remove</button>
        </div>
      ` : ""}
    </article>
  `).join("");

  grid.querySelectorAll("[data-edit]").forEach((btn) =>
    btn.addEventListener("click", () => editSkill(Number(btn.dataset.edit)))
  );
  grid.querySelectorAll("[data-delete]").forEach((btn) =>
    btn.addEventListener("click", () => deleteSkill(Number(btn.dataset.delete)))
  );
}

function editSkill(id) {
  const s = id ? allSkills.find((x) => x.id === id) : null;
  document.getElementById("skill-modal-eyebrow").textContent = s ? "Edit skill" : "New skill";
  document.getElementById("skill-modal-title").textContent = s ? s.name : "Log a new skill";
  document.getElementById("skill-modal-view").hidden = true;

  const form = document.getElementById("skill-modal-form");
  form.hidden = false;
  form.reset();
  document.getElementById("skill-form-error").hidden = true;
  form.dataset.editId = s ? s.id : "";

  if (s) {
    form.name.value = s.name;
    form.category.value = s.category || "";
    form.description.value = s.description || "";
  }
  openModal("skill-modal");
}

document.getElementById("skill-modal-form").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;
  const errorEl = document.getElementById("skill-form-error");
  errorEl.hidden = true;

  const payload = {
    name: form.name.value.trim(),
    category: form.category.value.trim() || null,
    description: form.description.value.trim() || null,
  };

  try {
    if (form.dataset.editId) {
      await SkillAPI.update(Number(form.dataset.editId), payload);
      showToast("Skill updated", "success");
    } else {
      await SkillAPI.create(payload);
      showToast("Skill logged", "success");
    }
    closeModal("skill-modal");
    await loadSkills();
  } catch (err) {
    errorEl.textContent = err.message;
    errorEl.hidden = false;
  }
});

function deleteSkill(id) {
  const s = allSkills.find((x) => x.id === id);
  confirmAction(`Remove "${s ? s.name : "this skill"}" from the catalogue?`, async () => {
    try {
      await SkillAPI.remove(id);
      showToast("Skill removed", "success");
      await loadSkills();
    } catch (err) {
      showToast(err.message, "error");
    }
  });
}

document.getElementById("skill-add-btn").addEventListener("click", () => editSkill(null));

document.getElementById("skill-search").addEventListener("input", (e) => {
  skillFilters.query = e.target.value;
  renderSkills();
});

document.getElementById("skill-category-filter").addEventListener("change", (e) => {
  skillFilters.category = e.target.value;
  renderSkills();
});

document.getElementById("skill-manage-toggle").addEventListener("change", (e) => {
  document.getElementById("skill-manage-note").hidden = !e.target.checked;
  renderSkills();
});

// ===========================================================
// HERO
// ===========================================================
function updateHeroStats() {
  const el = document.getElementById("hero-stats");
  if (allCareers.length && allSkills.length) {
    el.textContent = `${allCareers.length} careers charted · ${allSkills.length} skills catalogued`;
  }
}

document.getElementById("hero-search-form").addEventListener("submit", (e) => {
  e.preventDefault();
  const value = document.getElementById("hero-search-input").value.trim();
  document.getElementById("career-search").value = value;
  careerFilters.query = value;
  renderCareers();
  document.getElementById("careers").scrollIntoView({ behavior: "smooth" });
});

// ===========================================================
// Boot
// ===========================================================
loadCareers();
loadSkills();
