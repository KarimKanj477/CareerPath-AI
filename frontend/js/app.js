// ---------------------------------------------------------
// Shared state
// ---------------------------------------------------------
let allCareers = [];
let allSkills = [];
let currentUser = null;
let currentUserSkills = [];

const careerFilters = { query: "", demand: "ALL", category: "ALL" };
const skillFilters = { query: "", category: "ALL" };
const mySkillFilters = { query: "", category: "ALL" };
let authMode = "login";
let editingUserSkillId = null;

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

function setAuthToken(token) {
  if (token) {
    localStorage.setItem("cp_token", token);
  } else {
    localStorage.removeItem("cp_token");
  }
}

function clearAuthState() {
  currentUser = null;
  currentUserSkills = [];
  setAuthToken(null);
  renderAuthPanel();
  renderMySkills();
}

function renderAuthPanel() {
  const button = document.getElementById("nav-auth-btn");
  const bannerButton = document.getElementById("banner-auth-btn");
  const gate = document.getElementById("auth-gate");
  const panel = document.getElementById("my-skills-panel");

  if (currentUser) {
    const name = [currentUser.firstname, currentUser.lastname].filter(Boolean).join(" ") || currentUser.email;
    button.textContent = `Hi, ${name}`;
    bannerButton.textContent = "Go to your workspace";
    if (gate) gate.hidden = true;
    if (panel) panel.hidden = false;
  } else {
    button.textContent = "Sign in";
    bannerButton.textContent = "Open login portal";
    if (gate) gate.hidden = false;
    if (panel) panel.hidden = true;
  }
}

async function hydrateAuth() {
  const token = localStorage.getItem("cp_token");
  if (!token) {
    renderAuthPanel();
    return;
  }

  try {
    currentUser = await AuthAPI.me();
    renderAuthPanel();
    await loadMySkills();
  } catch (err) {
    clearAuthState();
    showToast("Your session expired. Please sign in again.", "error");
  }
}

function openAuthModal(mode = "login") {
  authMode = mode;
  const form = document.getElementById("auth-form");
  const switchButtons = document.querySelectorAll(".auth-mode-btn");
  const extraFields = document.getElementById("auth-extra-fields");
  const title = document.getElementById("auth-modal-title");

  switchButtons.forEach((btn) => {
    btn.classList.toggle("is-active", btn.dataset.mode === authMode);
  });

  extraFields.hidden = authMode !== "register";
  title.textContent = authMode === "register" ? "Create your account" : "Sign in";
  form.reset();
  document.getElementById("auth-form-error").hidden = true;
  openModal("auth-modal");
}

function populateMySkillOptions() {
  const select = document.querySelector("#my-skill-form select[name='skillId']") || document.querySelector("#my-skill-modal select[name='skillId']") ;
  if (!select) return;

  const currentValue = select.value;
  const skillOptions = allSkills
    .filter((skill) => skill && skill.id != null)
    .map((skill) => `<option value="${skill.id}" ${currentValue === String(skill.id) ? "selected" : ""}>${escapeHtml(skill.name)}</option>`)
    .join("");

  select.innerHTML = `<option value="">Choose a skill…</option>${skillOptions}`;
  if (currentValue) {
    select.value = currentValue;
  }
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
function getRoadmapForCareer(career) {
  const title = (career.title || "").toLowerCase();
  const category = (career.category || "").toLowerCase();

  if (title.includes("software") || title.includes("developer") || title.includes("programmer")) {
    return {
      summary: "Build a strong technical foundation, ship practical projects, and grow into responsibilities that combine delivery and collaboration.",
      steps: [
        { title: "Master the fundamentals", description: "Strengthen programming, problem solving, and core tools such as Git, APIs, and databases." },
        { title: "Develop in a real stack", description: "Work with a modern frontend, backend, and deployment workflow to build end-to-end products." },
        { title: "Create portfolio work", description: "Publish projects that demonstrate reliability, design thinking, and clear documentation." },
        { title: "Advance into professional roles", description: "Prepare for interviews, team collaboration, and ownership of larger technical initiatives." }
      ],
      skills: ["Programming fundamentals", "Version control", "Databases", "APIs", "System design", "Communication"],
      nextAction: "Start by choosing one project that solves a real problem and complete it end to end."
    };
  }

  if (title.includes("data analyst") || title.includes("data scientist") || (title.includes("analyst") && category.includes("data"))) {
    return {
      summary: "Turn information into clear business decisions through analysis, visualization, and careful storytelling.",
      steps: [
        { title: "Learn analytical foundations", description: "Build fluency in statistics, data cleaning, and spreadsheet-based analysis." },
        { title: "Strengthen querying skills", description: "Practice SQL and become comfortable working with structured datasets." },
        { title: "Create dashboards", description: "Translate findings into visual stories using charts, KPIs, and business context." },
        { title: "Show business impact", description: "Tie each project back to measurable outcomes and decision-making." }
      ],
      skills: ["SQL", "Excel", "Statistics", "Data visualization", "Business communication", "Dashboard design"],
      nextAction: "Build one dashboard that answers a business question with clear insight and a polished narrative."
    };
  }

  if (title.includes("ux") || title.includes("designer") || category.includes("design")) {
    return {
      summary: "Design products that are intuitive, accessible, and valuable by combining research, structure, and visual clarity.",
      steps: [
        { title: "Understand user needs", description: "Learn research methods, user flows, and patterns that improve usability." },
        { title: "Practice interface design", description: "Develop wireframes, prototypes, and visual systems that support clear interaction." },
        { title: "Build a portfolio", description: "Showcase design thinking through case studies and polished examples of your work." },
        { title: "Prepare for product teams", description: "Learn how to collaborate with developers and stakeholders across the full design process." }
      ],
      skills: ["User research", "Wireframing", "Prototyping", "Accessibility", "Design systems", "Presenting ideas"],
      nextAction: "Create a small end-to-end design case study that shows your process from research to final solution."
    };
  }

  if (title.includes("security") || title.includes("cyber")) {
    return {
      summary: "Protect systems through strong technical knowledge, risk awareness, and disciplined response practices.",
      steps: [
        { title: "Build networking fundamentals", description: "Understand how systems, protocols, and endpoints interact in a real environment." },
        { title: "Learn security principles", description: "Study authentication, vulnerabilities, logging, and defensive controls." },
        { title: "Practice hands-on analysis", description: "Use labs and scenarios to strengthen detection and incident response skills." },
        { title: "Develop professional credibility", description: "Showcase your work through practical projects, certifications, and clear documentation." }
      ],
      skills: ["Networking", "Threat analysis", "Security tools", "Risk assessment", "Incident response", "Documentation"],
      nextAction: "Set up a small lab environment and practice identifying and documenting a realistic security issue."
    };
  }

  if (category.includes("engineering") || title.includes("mechanical") || title.includes("civil")) {
    return {
      summary: "Combine technical knowledge with practical problem solving and project awareness to deliver reliable results.",
      steps: [
        { title: "Strengthen core engineering concepts", description: "Deepen your understanding of design principles, calculations, and technical standards." },
        { title: "Learn industry tools", description: "Gain confidence with design, modeling, and documentation software used in the field." },
        { title: "Get project experience", description: "Work on practical examples that connect theory to real-world implementation." },
        { title: "Grow into leadership", description: "Build communication and coordination skills to support teams and clients effectively." }
      ],
      skills: ["Technical analysis", "Design software", "Project awareness", "Problem solving", "Team collaboration", "Documentation"],
      nextAction: "Build a project portfolio that highlights your design thinking and technical decisions."
    };
  }

  if (category.includes("business") || title.includes("business analyst") || title.includes("marketing")) {
    return {
      summary: "Connect strategy, operations, and communication so that business goals are translated into measurable progress.",
      steps: [
        { title: "Develop business fluency", description: "Understand how organizations create value, measure performance, and make decisions." },
        { title: "Improve analytical execution", description: "Learn tools and methods that support planning, reporting, and process improvement." },
        { title: "Build stakeholder confidence", description: "Practice communicating recommendations clearly across teams and leadership." },
        { title: "Lead with impact", description: "Show how your work supports growth, efficiency, and better outcomes." }
      ],
      skills: ["Business analysis", "Communication", "Strategy", "Reporting", "Stakeholder management", "Problem solving"],
      nextAction: "Prepare a short recommendation memo or case study that shows how you would improve a real process."
    };
  }

  if (category.includes("health") || title.includes("nurse") || title.includes("physician") || title.includes("assistant")) {
    return {
      summary: "Build clinical competence, empathy, and reliability through consistent practice and professional development.",
      steps: [
        { title: "Complete the required training", description: "Focus on the core education and certifications needed for the role." },
        { title: "Gain practical experience", description: "Develop confidence by working in real clinical or care environments." },
        { title: "Strengthen patient communication", description: "Practice clear, compassionate communication with patients and interdisciplinary teams." },
        { title: "Advance professionally", description: "Keep improving through specialization, mentorship, and continued learning." }
      ],
      skills: ["Clinical knowledge", "Patient care", "Communication", "Teamwork", "Professional ethics", "Adaptability"],
      nextAction: "Focus on the next certification or clinical milestone that moves you closer to the role."
    };
  }

  if (category.includes("education") || title.includes("teacher")) {
    return {
      summary: "Create meaningful learning experiences by combining subject knowledge, classroom management, and student support.",
      steps: [
        { title: "Build teaching foundations", description: "Develop lesson planning, pedagogy, and assessment methods." },
        { title: "Gain classroom experience", description: "Practice leading learning environments and adapting to student needs." },
        { title: "Strengthen communication", description: "Learn how to guide, motivate, and support learners effectively." },
        { title: "Grow into leadership", description: "Advance through mentoring, curriculum work, and professional development." }
      ],
      skills: ["Instruction", "Assessment", "Classroom management", "Communication", "Adaptability", "Mentorship"],
      nextAction: "Create a sample lesson plan or learning module that demonstrates your teaching approach."
    };
  }

  if (category.includes("finance") || title.includes("financial")) {
    return {
      summary: "Combine technical analysis with trust, planning, and clear guidance to support better financial decisions.",
      steps: [
        { title: "Understand financial principles", description: "Build confidence in budgeting, markets, analysis, and planning." },
        { title: "Develop client-facing skills", description: "Learn how to explain recommendations with clarity and professionalism." },
        { title: "Gain practical experience", description: "Work through case studies, portfolios, or supervised projects." },
        { title: "Advance with credentials", description: "Continue building credibility through qualifications and professional growth." }
      ],
      skills: ["Financial analysis", "Planning", "Communication", "Risk awareness", "Client trust", "Compliance"],
      nextAction: "Prepare a short client-ready recommendation that shows your analytical and communication strengths."
    };
  }

  return {
    summary: "Follow a clear progression from learning the foundations to building experience and demonstrating value in the field.",
    steps: [
      { title: "Learn the essentials", description: "Build a strong base in the knowledge and tools most relevant to the role." },
      { title: "Gain hands-on experience", description: "Apply your knowledge through projects, internships, or practical work." },
      { title: "Build credibility", description: "Showcase your results with examples, evidence, and clear communication." },
      { title: "Advance steadily", description: "Use each milestone to grow into broader responsibilities and stronger opportunities." }
    ],
    skills: ["Core knowledge", "Practical experience", "Communication", "Professional growth"],
    nextAction: "Choose one focused milestone and start building momentum with a concrete learning plan."
  };
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
        <button class="btn-view" data-view="${c.id}">View roadmap</button>
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
  const roadmap = getRoadmapForCareer(c);

  document.getElementById("career-modal-eyebrow").textContent = c.category || "Career";
  document.getElementById("career-modal-title").textContent = c.title;
  document.getElementById("career-modal-view").innerHTML = `
    <div class="roadmap-shell">
      <div class="roadmap-header">
        <div class="roadmap-header-top">
          <span class="roadmap-pill">Professional roadmap</span>
          <span class="roadmap-pill roadmap-pill--accent">${escapeHtml(c.demandLevel || "Medium")} demand</span>
        </div>
        <h4>${escapeHtml(c.title)} pathway</h4>
        <p>${escapeHtml(roadmap.summary)}</p>
      </div>

      <div class="roadmap-grid">
        <div class="roadmap-card">
          <h5>Milestones</h5>
          <div class="roadmap-steps">
            ${roadmap.steps.map((step, index) => `
              <div class="roadmap-step">
                <span class="roadmap-step-badge">0${index + 1}</span>
                <div>
                  <h6>${escapeHtml(step.title)}</h6>
                  <p>${escapeHtml(step.description)}</p>
                </div>
              </div>
            `).join("")}
          </div>
        </div>

        <div class="roadmap-card">
          <h5>Core skills to focus on</h5>
          <ul class="roadmap-list">
            ${roadmap.skills.map((skill) => `<li>${escapeHtml(skill)}</li>`).join("")}
          </ul>
        </div>
      </div>

      <div class="roadmap-card roadmap-card--highlight">
        <h5>Recommended next move</h5>
        <p>${escapeHtml(roadmap.nextAction)}</p>
      </div>

      <div class="modal-detail-row"><span>Average salary</span><span>${money(c.averageSalary)}</span></div>
      <div class="modal-detail-row"><span>Demand</span><span>${escapeHtml(c.demandLevel || "Medium")}</span></div>
      <p class="roadmap-description">${escapeHtml(c.description || "No description logged yet.")}</p>
    </div>
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
// PERSONAL SKILLS
// ===========================================================
function filteredMySkills() {
  return currentUserSkills.filter((entry) => {
    const label = `${entry.skillName || ""} ${entry.skillCategory || ""}`.toLowerCase();
    const matchesQuery = label.includes(mySkillFilters.query.toLowerCase());
    const matchesCategory = mySkillFilters.category === "ALL" || entry.skillCategory === mySkillFilters.category;
    return matchesQuery && matchesCategory;
  });
}

function renderMySkills() {
  const grid = document.getElementById("my-skill-grid");
  if (!currentUser) {
    grid.innerHTML = `<div class="state-card">Please sign in to keep your own skill list here.</div>`;
    return;
  }

  const items = filteredMySkills();
  if (items.length === 0) {
    grid.innerHTML = `<div class="state-card">Your personal skill list is empty. Add your first skill to get started.</div>`;
    return;
  }

  grid.innerHTML = items.map((entry) => `
    <article class="tag-card">
      <div class="tag-card-top">
        <span class="tag-card-name">${escapeHtml(entry.skillName || "Skill")}</span>
        <span class="tag-card-category">${escapeHtml(entry.skillCategory || "General")}</span>
      </div>
      <p class="tag-card-desc">${escapeHtml(entry.proficiencyLevel || "Beginner")}</p>
      <div class="tag-card-actions">
        <button class="btn-edit" data-edit="${entry.id}">Edit</button>
        <button class="btn-delete" data-delete="${entry.id}">Remove</button>
      </div>
    </article>
  `).join("");

  grid.querySelectorAll("[data-edit]").forEach((btn) =>
    btn.addEventListener("click", () => editMySkill(Number(btn.dataset.edit)))
  );
  grid.querySelectorAll("[data-delete]").forEach((btn) =>
    btn.addEventListener("click", () => deleteMySkill(Number(btn.dataset.delete)))
  );
}

async function loadMySkills() {
  const grid = document.getElementById("my-skill-grid");
  if (!currentUser) {
    renderMySkills();
    return;
  }

  try {
    const items = await request(`/users/${currentUser.userId}/skills`);
    currentUserSkills = items || [];
    populateMySkillCategoryOptions(currentUserSkills);
    renderMySkills();
  } catch (err) {
    grid.innerHTML = `<div class="state-card state-card--error">Unable to load your skills — ${escapeHtml(err.message)}</div>`;
  }
}

function populateMySkillCategoryOptions(items) {
  const select = document.getElementById("my-skill-category-filter");
  const current = select.value;
  const categories = [...new Set(items.map((item) => item.skillCategory).filter(Boolean))].sort();
  select.innerHTML = `<option value="ALL">All categories</option>${categories.map((category) => `<option value="${escapeHtml(category)}">${escapeHtml(category)}</option>`).join("")}`;
  if (categories.includes(current)) {
    select.value = current;
  }
}

function openMySkillModal(entry = null) {
  const form = document.getElementById("my-skill-form");
  const title = document.getElementById("my-skill-modal-title");
  form.reset();
  editingUserSkillId = entry ? entry.id : null;
  title.textContent = entry ? "Update your skill" : "Add a skill";

  if (entry) {
    form.skillId.value = entry.skillId || "";
    form.proficiencyLevel.value = (entry.proficiencyLevel || "BEGINNER").toUpperCase();
  }

  populateMySkillOptions();
  document.getElementById("my-skill-form-error").hidden = true;
  openModal("my-skill-modal");
}

function editMySkill(id) {
  const entry = currentUserSkills.find((item) => item.id === id);
  if (!entry) return;
  openMySkillModal(entry);
}

async function saveMySkill(payload) {
  const path = editingUserSkillId
    ? `/users/${currentUser.userId}/skills/${editingUserSkillId}`
    : `/users/${currentUser.userId}/skills`;
  const method = editingUserSkillId ? "PUT" : "POST";
  await request(path, { method, body: JSON.stringify(payload) });
}

function deleteMySkill(id) {
  const entry = currentUserSkills.find((item) => item.id === id);
  confirmAction(`Remove "${entry ? entry.skillName : "this skill"}" from your list?`, async () => {
    try {
      await request(`/users/${currentUser.userId}/skills/${id}`, { method: "DELETE" });
      showToast("Skill removed", "success");
      await loadMySkills();
    } catch (err) {
      showToast(err.message, "error");
    }
  });
}

document.getElementById("my-skill-form").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;
  const errorEl = document.getElementById("my-skill-form-error");
  errorEl.hidden = true;

  const payload = {
    skillId: Number(form.skillId.value),
    proficiencyLevel: form.proficiencyLevel.value,
  };

  try {
    await saveMySkill(payload);
    closeModal("my-skill-modal");
    await loadMySkills();
    showToast(editingUserSkillId ? "Skill updated" : "Skill added", "success");
  } catch (err) {
    errorEl.textContent = err.message;
    errorEl.hidden = false;
  }
});

document.getElementById("my-skill-add-btn").addEventListener("click", () => openMySkillModal());
document.getElementById("my-skills-signin-btn").addEventListener("click", () => openAuthModal("login"));
document.getElementById("nav-auth-btn").addEventListener("click", () => {
  if (currentUser) {
    clearAuthState();
    showToast("Signed out", "success");
  } else {
    openAuthModal("login");
  }
});
document.getElementById("banner-auth-btn").addEventListener("click", () => openAuthModal("login"));

document.getElementById("my-skill-search").addEventListener("input", (e) => {
  mySkillFilters.query = e.target.value;
  renderMySkills();
});

document.getElementById("my-skill-category-filter").addEventListener("change", (e) => {
  mySkillFilters.category = e.target.value;
  renderMySkills();
});

document.getElementById("auth-mode-switch").addEventListener("click", (e) => {
  const button = e.target.closest("[data-mode]");
  if (!button) return;
  openAuthModal(button.dataset.mode);
});

document.getElementById("auth-form").addEventListener("submit", async (e) => {
  e.preventDefault();
  const form = e.target;
  const errorEl = document.getElementById("auth-form-error");
  errorEl.hidden = true;

  const payload = authMode === "register"
    ? {
        firstname: form.firstname.value.trim(),
        lastname: form.lastname.value.trim(),
        email: form.email.value.trim(),
        password: form.password.value,
        experienceLevel: form.experienceLevel.value.trim() || null,
      }
    : {
        email: form.email.value.trim(),
        password: form.password.value,
      };

  try {
    const authResponse = authMode === "register"
      ? await AuthAPI.register(payload)
      : await AuthAPI.login(payload);

    setAuthToken(authResponse.token);
    currentUser = await AuthAPI.me();
    renderAuthPanel();
    await loadMySkills();
    closeModal("auth-modal");
    showToast(authMode === "register" ? "Account created successfully" : "Signed in successfully", "success");
  } catch (err) {
    errorEl.textContent = err.message;
    errorEl.hidden = false;
  }
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
hydrateAuth();
