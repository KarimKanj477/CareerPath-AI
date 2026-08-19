# CareerPath AI Backend

This folder contains the Spring Boot backend of the CareerPath AI application.

The backend manages authentication, users, skills, career recommendations, personalized learning roadmaps, learning resources, and progress tracking.

---

## Technology Stack

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- Swagger / OpenAPI

---

## Main Backend Features

### Authentication and Security

The backend provides user authentication and protects application features that require a logged-in user.

Authenticated users can access their own skills, recommendations, roadmaps, and progress information.

### User Skills

The backend allows users to:

- View their current skills.
- Add skills to their profile.
- Update skill levels.
- Remove skills from their profile.

### Career Recommendations

The recommendation functionality compares the user's current skills with the skills required for each career.

It provides:

- Career match percentages.
- Matched skills.
- Missing skills.

### Personalized Roadmaps

Users can generate personalized learning roadmaps based on the skills they are missing for a selected career.

Roadmap steps are organized according to the importance of the required skills.

### Learning Resources

Learning resources are connected to skills and can be displayed as part of personalized roadmap steps.

Resources may include documentation, tutorials, courses, and learning paths.

### Progress Tracking

Users can update the progress of individual roadmap steps from 0% to 100%.

The backend automatically manages the related statuses:

- Not Started
- In Progress
- Completed

The overall roadmap status is also updated according to the progress of its steps.

---

## Database

The backend uses MySQL.

Database creation and demonstration data are available in:

```text
../database/schema.sql
../database/seed.sql
```

Run `schema.sql` before `seed.sql` when creating a fresh local database.

---

## Database Configuration

The Spring Boot application must be configured with a valid local MySQL connection.

Check the application configuration in:

```text
src/main/resources/application.properties
```

The configuration should contain the correct:

- Database URL
- Database username
- Database password

Do not commit real passwords or other sensitive credentials to a public repository.

---

## Running the Backend

Open the `backend` project in IntelliJ IDEA.

Make sure:

1. MySQL is running.
2. The CareerPath AI database has been created.
3. The Spring Boot database configuration is correct.

Then run the main Spring Boot application class from IntelliJ IDEA.

When startup is successful, the backend is ready to receive requests from the React frontend.

---

## API Documentation and Testing

Swagger / OpenAPI is used to view and test the backend endpoints.

The main backend areas available for testing include:

- Authentication
- Users
- Skills
- User skills
- Career recommendations
- Roadmaps
- Progress tracking

Protected endpoints require authentication.

---

## Main Packages

The backend is organized into layers such as:

```text
controller/
dto/
entity/
exception/
repository/
security/
service/
```

This structure separates the different responsibilities of the application and keeps the code easier to maintain.

---

## Testing

During development, backend functionality was tested through Swagger and through integration with the React frontend.

The main tested flows include:

- Authentication.
- Skill management.
- Career recommendations.
- Personalized roadmap generation.
- Learning resource retrieval.
- Progress updates.
- Authentication and ownership checks.
- Validation of invalid requests.

---

## Current Status

The main backend functionality is implemented and integrated with the frontend.

Current work focuses on final testing, configuration review, documentation, and project stabilization.