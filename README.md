# CareerPath AI

CareerPath AI is a career guidance web application that helps users manage their skills, explore career recommendations, generate personalized learning roadmaps, access learning resources, and track their learning progress.

The project was developed as part of an internship using a Spring Boot backend, React frontend, and MySQL database.

---

## Main Features

### Authentication

Users can:

- Register for a new account.
- Log in securely.
- Access protected application pages.
- Log out of the application.

Newly registered accounts automatically receive the `User` role.

JWT authentication is used to protect authenticated functionality.

### My Skills

Users can manage the skills in their profile.

They can:

- View their current skills.
- Add a new skill.
- Choose a proficiency level.
- Update an existing skill level.
- Delete a skill.

### Career Recommendations

The application compares a user's current skills with the skills required for available careers.

For each recommendation, the user can see:

- Career information.
- Match percentage.
- Matched skills.
- Missing skills.

The recommendation score takes the importance of career skills into account.

### Personalized Learning Roadmaps

Users can generate a personalized roadmap for a selected career.

The roadmap is based on the skills that the user is currently missing.

Each roadmap contains ordered learning steps that help the user work toward the selected career.

### Learning Resources

Learning resources are associated with skills.

Resources can include:

- Official documentation.
- Tutorials.
- Courses.
- Learning guides.

Relevant resources are displayed inside roadmap steps when available.

### Progress Tracking

Users can track their progress for roadmap steps.

Supported progress values include:

- 0%
- 25%
- 50%
- 75%
- 100%

The application automatically updates step and roadmap statuses:

- Not Started
- In Progress
- Completed

Progress is stored in the database and remains available after refreshing or logging in again.

---

## Technology Stack

### Backend

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT Authentication
- Swagger / OpenAPI
- Maven

### Frontend

- React
- JavaScript
- Vite
- CSS

### Database

- MySQL

### Development Tools

- IntelliJ IDEA
- MySQL Workbench
- Git
- GitHub
- Jira
- Notion

---

## Project Structure

```text
CareerPath-AI-repo/
│
├── backend/
│   └── Spring Boot backend application
│
├── frontend/
│   └── React / Vite frontend application
│
├── database/
│   ├── schema.sql
│   ├── seed.sql
│   └── README.md
│
└── README.md
```

---

## Database

The application uses the `careerpath_ai` MySQL database.

The final database contains the following tables:

| Table | Purpose |
|---|---|
| `roles` | Application user roles |
| `users` | Registered user accounts |
| `skills` | Available skills |
| `careers` | Available career paths |
| `career_skills` | Skills required for each career |
| `user_skills` | Skills belonging to each user |
| `roadmaps` | Personalized user roadmaps |
| `roadmap_steps` | Individual learning steps |
| `learning_resources` | Resources associated with skills |
| `progress_tracking` | User progress for roadmap steps |

For more information, see:

```text
database/README.md
```

---

## Database Setup

Create the database using:

```text
database/schema.sql
```

Then insert the demonstration data using:

```text
database/seed.sql
```

Run them in this order:

```text
1. schema.sql
2. seed.sql
```

The seed data includes careers, skills, career-skill mappings, learning resources, and demonstration application data.

---

## Backend Configuration

Backend configuration is located in:

```text
backend/src/main/resources/application.properties
```

Sensitive values are read from environment variables.

Required environment variables:

```text
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

Example database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/careerpath_ai
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

app.jwt.secret=${JWT_SECRET}
```

Sensitive credentials should not be committed directly to the repository.

---

## Running the Backend

Make sure:

1. MySQL is running.
2. The `careerpath_ai` database exists.
3. The required environment variables are configured.

Then run the Spring Boot application from IntelliJ IDEA.

The backend runs locally and provides the API used by the React frontend.

### Backend Tests

From the `backend` folder:

```bash
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

---

## Swagger API Documentation

Swagger / OpenAPI is available while the backend is running.

It can be used to test:

- Authentication
- Skills
- User skills
- Careers
- Career recommendations
- Roadmaps
- Progress tracking
- Administrative functionality

Protected endpoints require JWT authentication.

---

## Running the Frontend

Open the frontend folder:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

The frontend uses the backend API URL configured through:

```text
VITE_API_BASE_URL
```

---

## Frontend Verification

Run the code quality check:

```bash
npm run lint
```

Create the production build:

```bash
npm run build
```

---

## Security

The application uses Spring Security and JWT authentication.

Access is separated according to the user's role and authentication status.

### Public Access

Public functionality includes:

- Registration
- Login
- Reading available skills
- Reading available careers
- Swagger documentation

### Authenticated User Access

Authenticated users can manage:

- Their skills
- Career recommendations
- Personalized roadmaps
- Learning progress

### Administrator Access

Administrators can manage application data such as:

- Users
- Roles
- Skills
- Careers

Unauthorized requests return appropriate `401 Unauthorized` or `403 Forbidden` responses.

---

## Testing

The application was tested through both Swagger and the React frontend.

Final testing included:

- Registration and login.
- User role assignment.
- Skill creation, editing, and deletion.
- Career recommendation generation.
- Skill-gap calculation.
- Personalized roadmap generation.
- Learning resource display.
- Progress tracking and persistence.
- Authentication and authorization.
- Administrator restrictions.
- Database integrity checks.
- Frontend lint and production build.
- Backend Maven tests.
- Responsive interface testing.

---

## Development Workflow

Development was managed using:

- Git and GitHub for version control.
- Jira for sprint and task management.
- Notion for weekly progress reporting.

Development work was completed on the `internship-development` branch before final integration into `main`.

---

## Current Project Status

The main CareerPath AI functionality is implemented and integrated.

The project is currently in its final testing and stabilization stage.

Core functionality is working successfully across the frontend, backend, database, authentication, recommendation, roadmap, and progress-tracking modules.
