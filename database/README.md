# CareerPath AI Database

This folder contains the MySQL database scripts used by the CareerPath AI application.

## Files

### `schema.sql`

Creates the database structure and all required tables, relationships, foreign keys, and constraints.

### `seed.sql`

Inserts initial demonstration data used to test the application, including:

- Roles
- Users
- Careers
- Skills
- Career requirements
- User skills
- Sample roadmap data
- Learning resources
- Progress tracking data

---

## Database Setup

Open MySQL Workbench and execute the files in the following order:

1. `schema.sql`
2. `seed.sql`

The schema must always be executed before the seed file.

---

## Main Tables

The database currently contains the following main tables:

### `roles`

Stores the different user roles.

### `users`

Stores registered user information and links users to their roles.

### `skills`

Stores the skills available in the application.

Examples include:

- Java
- SQL
- React
- Spring Boot
- Git
- Docker
- REST API
- ASP.NET

### `user_skills`

Connects users with the skills they currently have and stores their skill level.

### `careers`

Stores the different career paths available in the application.

### `career_skills`

Defines which skills are required for each career and their importance level.

Importance levels can include:

- HIGH
- MEDIUM
- LOW

### `roadmaps`

Stores personalized learning roadmaps generated for users.

Each roadmap is linked to a user and a target career.

### `roadmap_steps`

Stores the individual learning steps of a roadmap.

A roadmap step can be linked to a specific skill.

### `learning_resources`

Stores learning resources associated with skills.

Resources may include:

- Documentation
- Tutorials
- Courses
- Learning paths

### `progress_tracking`

Stores the user's progress for individual roadmap steps.

Progress can range from:

- 0% — Not Started
- 1–99% — In Progress
- 100% — Completed

Each user can have only one progress record for a specific roadmap step.

---

## Main Relationships

The main database relationships are:

```text
roles
  |
  └── users
        |
        ├── user_skills ── skills
        |
        └── roadmaps ── careers
               |
               └── roadmap_steps ── skills
                        |
                        └── progress_tracking

careers
  |
  └── career_skills ── skills

skills
  |
  └── learning_resources
```

---

## Initial Data

The seed file currently includes:

- 21 skills
- Career information and career-skill requirements
- 50 career-skill relationships
- 17 learning resources
- Sample user and roadmap data
- Sample progress tracking data

The seed data is intended mainly for development and testing.

---

## Database Testing

The database scripts were tested by creating a fresh MySQL database and executing:

1. `schema.sql`
2. `seed.sql`

Both scripts can recreate the project database structure and demonstration data from scratch.

---

## Important Notes

- Do not store real database passwords inside these SQL files.
- Update `schema.sql` whenever the database structure changes.
- Update `seed.sql` when new demonstration data is required.
- Foreign key relationships should be respected when adding or deleting records.
- The database scripts should stay synchronized with the Spring Boot entities used by the backend.