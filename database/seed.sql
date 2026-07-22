USE careerpath_ai;

INSERT INTO roles (name, description)
VALUES
('STUDENT',     'University student exploring career paths and building foundational skills.'),
('PROFESSIONAL','Working professional seeking new skills or a career change.'),
('ADMIN',       'Manages the system, career catalog, and application content.');

INSERT INTO careers (title, description, category, averageSalary, demandLevel)
VALUES
('Software Engineer', 'Designs, develops, tests, and maintains software applications.', 'Software Development', 65000.00, 'HIGH'),
('Data Analyst', 'Analyzes data to support business decisions and create reports.', 'Data', 55000.00, 'HIGH'),
('UI/UX Designer', 'Designs user interfaces and improves user experience for digital products.', 'Design', 50000.00, 'MEDIUM'),
('Web Developer', 'Builds and maintains websites and web applications.', 'Web Development', 52000.00, 'HIGH'),
('Business Analyst', 'Analyzes business needs and helps translate them into technical requirements.', 'Business', 58000.00, 'MEDIUM'),
('Cybersecurity Analyst', 'Protects systems and data by identifying and responding to security threats.', 'Cybersecurity', 70000.00, 'HIGH'),
('Mobile Application Developer', 'Develops mobile applications for Android and iOS platforms.', 'Mobile Development', 60000.00, 'HIGH'),
('DevOps Engineer', 'Manages deployment, automation, infrastructure, and CI/CD pipelines.', 'DevOps', 75000.00, 'HIGH'),
('AI / Machine Learning Engineer', 'Builds AI models and machine learning systems to solve real-world problems.', 'Artificial Intelligence', 80000.00, 'HIGH'),
('Cloud Engineer', 'Designs and manages cloud infrastructure and cloud based services.', 'Cloud Computing', 72000.00, 'HIGH');

INSERT INTO skills (name, description, category)
VALUES
('Java','Programming language used to build desktop, web, and enterprise applications.','Programming Language'),
('SQL','Language used to create, query, and manage relational databases.','Database'),
('React','JavaScript library used to build interactive user interfaces for web applications.','Frontend Framework'),
('Spring Boot','Java framework used to develop REST APIs and backend applications.','Backend Framework'),
('HTML','Markup language used to structure web pages.','Web Development'),
('CSS','Style sheet language used to design and format web pages.','Web Development'),
('JavaScript','Programming language used to build interactive websites.','Programming Language'),
('Python','Programming language widely used in AI, automation, and data analysis.','Programming Language'),
('Git','Version control system used to manage source code.','Version Control'),
('GitHub','Platform used to host and collaborate on software projects.','Version Control'),
('Docker','Platform used to build, package, and run applications in containers.','DevOps'),
('MySQL','Relational database management system used to store application data.','Database'),
('Figma','Design tool used to create user interfaces, prototypes, and wireframes.','Design'),
('Power BI','Business intelligence tool used to analyze and visualize data.','Data Analytics'),
('Linux','Operating system commonly used for servers and cybersecurity.','Operating System'),
('Networking','Knowledge of computer networks and communication protocols.','Cybersecurity'),
('Problem Solving','Ability to analyze problems and develop effective solutions.','Soft Skill'),
('Communication','Ability to communicate effectively with team members and clients.','Soft Skill'),
('Cloud Computing','Knowledge of cloud platforms and cloud-based services.','Cloud'),
('REST API','Architecture used for communication between frontend and backend applications.','Backend Development');

INSERT INTO career_skills (careerId, skillId, importance)
VALUES
-- Software Engineer
(1, 1, 'HIGH'),
(1, 2, 'HIGH'),
(1, 3, 'MEDIUM'),
(1, 4, 'HIGH'),
(1, 9, 'HIGH'),
(1, 20, 'HIGH'),
-- Data Analyst
(2, 2, 'HIGH'),
(2, 8, 'HIGH'),
(2, 14, 'HIGH'),
(2, 17, 'HIGH'),
(2, 18, 'MEDIUM'),
-- UI/UX Designer
(3, 13, 'HIGH'),
(3, 18, 'HIGH'),
(3, 17, 'MEDIUM'),
-- Web Developer
(4, 3, 'HIGH'),
(4, 5, 'HIGH'),
(4, 6, 'HIGH'),
(4, 7, 'HIGH'),
(4, 9, 'MEDIUM'),
-- Business Analyst
(5, 2, 'MEDIUM'),
(5, 14, 'HIGH'),
(5, 17, 'HIGH'),
(5, 18, 'HIGH');

-- Note: passwords below are plain text placeholders for local dev seed
-- data only. The backend must store BCrypt hashes, never plain text.
INSERT INTO users
(firstname, lastname, email, password, experienceLevel, roleId)
VALUES
('Karim','Kanj','karim@example.com','$2a$10$replaceWithRealBcryptHash','Intermediate',2),
('Ranim','Matar','ranim@example.com','$2a$10$replaceWithRealBcryptHash','Beginner',2),
('Admin','System','admin@careerpath.ai','$2a$10$replaceWithRealBcryptHash','Advanced',3);

INSERT INTO user_skills (userId, skillId, level)
VALUES
(1, 1, 'Intermediate'),   -- Java
(1, 2, 'Intermediate'),   -- SQL
(1, 5, 'Intermediate'),   -- HTML
(1, 6, 'Intermediate'),   -- CSS
(1, 9, 'Beginner'),       -- Git
(1, 10, 'Intermediate'),  -- GitHub
(1, 12, 'Intermediate'),  -- MySQL
(1, 17, 'Advanced'),      -- Problem Solving
(1, 18, 'Advanced');      -- Communication

INSERT INTO certifications (userId, name, issuer, issueDate, expiryDate, credentialUrl)
VALUES
(1, 'Oracle Certified Associate, Java SE Programmer', 'Oracle', '2024-05-01', NULL, 'https://example.com/credential/123');

INSERT INTO roadmaps
(userId, careerId, title, status)
VALUES
(1, 1, 'Become a Software Engineer', 'IN_PROGRESS');

INSERT INTO roadmap_steps
(roadmapId, skillId, title, description, stepOrder, status)
VALUES
(1, 1, 'Learn Java Fundamentals','Study Java syntax, object-oriented programming, collections, and exception handling.',1,'COMPLETED'),
(1, 2, 'Learn SQL','Practice creating databases, writing SQL queries, joins, and constraints.',2,'IN_PROGRESS'),
(1, 4, 'Learn Spring Boot','Develop REST APIs and backend services using Spring Boot.',3,'NOT_STARTED'),
(1, 3, 'Learn React','Build responsive frontend interfaces using React.',4,'NOT_STARTED'),
(1, NULL, 'Build Full Stack Projects','Combine Spring Boot and React to develop complete web applications.',5,'NOT_STARTED');

INSERT INTO learning_resources
(skillId, title, url, type, provider, isFree)
VALUES
(1, 'Java Documentation', 'https://docs.oracle.com/en/java/', 'Documentation', 'Oracle', TRUE),
(2, 'SQL Tutorial', 'https://www.w3schools.com/sql/', 'Tutorial', 'W3Schools', TRUE),
(3, 'React Official Documentation', 'https://react.dev/', 'Documentation', 'React', TRUE),
(4, 'Spring Boot Documentation', 'https://spring.io/projects/spring-boot', 'Documentation', 'Spring', TRUE),
(11, 'Docker Getting Started', 'https://docs.docker.com/get-started/', 'Documentation', 'Docker', TRUE);

INSERT INTO progress_tracking
(userId, roadmapStepId, status, completionDate, progressPercentage)
VALUES
(1, 1, 'COMPLETED', NOW(), 100),
(1, 2, 'IN_PROGRESS', NULL, 60),
(1, 3, 'NOT_STARTED', NULL, 0),
(1, 4, 'NOT_STARTED', NULL, 0),
(1, 5, 'NOT_STARTED', NULL, 0);

INSERT INTO chat_history (userId, sender, message)
VALUES
(1, 'USER', 'What skills do I still need to become a Software Engineer?'),
(1, 'AI', 'Based on your profile, focus next on Spring Boot and React — you already have a solid Java and SQL foundation.');
