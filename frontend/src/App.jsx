const features = [
  {
    title: 'Personalized career paths',
    description: 'See clear growth routes based on your background, interests, and level of experience.'
  },
  {
    title: 'Skill-based guidance',
    description: 'Identify the exact skills you need to move from where you are to where you want to be.'
  },
  {
    title: 'Learning resources',
    description: 'Discover curated courses, certifications, and learning materials that match your plan.'
  }
];

function App() {
  return (
    <div className="page-shell">
      <header className="hero-card">
        <nav className="topbar">
          <div className="brand">CareerPath AI</div>
          <div className="nav-links">
            <a href="#features">Features</a>
            <a href="#journey">Your journey</a>
          </div>
        </nav>

        <div className="hero-content">
          <div>
            <p className="eyebrow">AI-powered career navigation</p>
            <h1>Build a future that fits your strengths.</h1>
            <p className="hero-copy">
              Discover careers, map your next move, and unlock learning opportunities with a modern, inspiring experience.
            </p>
            <div className="hero-actions">
              <a className="primary-btn" href="#features">Explore the platform</a>
              <a className="secondary-btn" href="#journey">See how it works</a>
            </div>
          </div>

          <div className="hero-panel">
            <div className="panel-card">
              <p className="panel-label">Career snapshot</p>
              <h3>Data Analyst</h3>
              <ul>
                <li>Strong growth in analytics</li>
                <li>High-demand tools: SQL, Python, dashboards</li>
                <li>Recommended next step: portfolio project</li>
              </ul>
            </div>
          </div>
        </div>
      </header>

      <main>
        <section id="features" className="section-card">
          <div className="section-heading">
            <p className="eyebrow">Why it stands out</p>
            <h2>Everything you need to move forward with confidence</h2>
          </div>
          <div className="feature-grid">
            {features.map((feature) => (
              <article key={feature.title} className="feature-card">
                <h3>{feature.title}</h3>
                <p>{feature.description}</p>
              </article>
            ))}
          </div>
        </section>

        <section id="journey" className="section-card journey-card">
          <div>
            <p className="eyebrow">Your next step</p>
            <h2>Turn curiosity into a clear roadmap</h2>
            <p>
              Combine insightful recommendations, role exploration, and learning resources in one calm, motivating interface.
            </p>
          </div>
          <div className="mini-list">
            <div>1. Explore careers</div>
            <div>2. Map your skills</div>
            <div>3. Start learning</div>
          </div>
        </section>
      </main>
    </div>
  );
}

export default App;
