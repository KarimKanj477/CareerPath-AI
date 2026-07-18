USE careerpath_ai;

INSERT INTO roles (name, description)
VALUES
('Admin', 'Manages the system and application content.'),
('User', 'Uses the platform to explore careers and follow learning roadmaps.');

INSERT INTO careers (title, description, category, averageSalary, demandLevel)
VALUES
('Software Engineer', 'Designs, develops, tests, and maintains software applications.', 'Software Development', 65000.00, 'High'),
('Data Analyst', 'Analyzes data to support business decisions and create reports.', 'Data', 55000.00, 'High'),
('UI/UX Designer', 'Designs user interfaces and improves user experience for digital products.', 'Design', 50000.00, 'Medium'),
('Web Developer', 'Builds and maintains websites and web applications.', 'Web Development', 52000.00, 'High'),
('Business Analyst', 'Analyzes business needs and helps translate them into technical requirements.', 'Business', 58000.00, 'Medium'),
('Cybersecurity Analyst', 'Protects systems and data by identifying and responding to security threats.', 'Cybersecurity', 70000.00, 'High'),
('Mobile Application Developer', 'Develops mobile applications for Android and iOS platforms.', 'Mobile Development', 60000.00, 'High'),
('DevOps Engineer', 'Manages deployment, automation, infrastructure, and CI/CD pipelines.', 'DevOps', 75000.00, 'High'),
('AI / Machine Learning Engineer', 'Builds AI models and machine learning systems to solve real-world problems.', 'Artificial Intelligence', 80000.00, 'High'),
('Cloud Engineer', 'Designs and manages cloud infrastructure and cloud based services.', 'Cloud Computing', 72000.00, 'High');

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
--Software Engineer
(1, 1, 'High'),
(1, 2, 'High'),
(1, 3, 'Medium'),
(1, 4, 'High'),
(1, 9, 'High'),
(1, 20, 'High'),

--Data Analyst
(2, 2, 'High'),
(2, 8, 'High'),
(2, 14, 'High'),
(2, 17, 'High'),
(2, 18, 'Medium'),

--UI/UX Designer
(3, 13, 'High'),
(3, 18, 'High'),
(3, 17, 'Medium'),

--Web Developer
(4, 3, 'High'),
(4, 5, 'High'),
(4, 6, 'High'),
(4, 7, 'High'),
(4, 9, 'Medium'),

--Business Analyst
(5, 2, 'Medium'),
(5, 14, 'High'),
(5, 17, 'High'),
(5, 18, 'High');

INSERT INTO users
(firstname, lastname, email, password, experienceLevel, createdAt, roleId)
VALUES
('Karim','Kanj','karim@example.com','password123','Intermediate',NOW(),2),

('Ranim','Matar','Ranim@example.com','password123','Beginner',NOW(),2),

('Admin','System','admin@careerpath.ai','admin123','Advanced',NOW(),1);

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

INSERT INTO roadmaps
(userId, careerId, title, status, createdAt)
VALUES
(1, 1, 'Become a Software Engineer', 'In Progress', NOW());

INSERT INTO roadmap_steps
(roadmapId, title, description, stepOrder, status)
VALUES
(1,'Learn Java Fundamentals','Study Java syntax, object-oriented programming, collections, and exception handling.',1,'Completed'),

(1,'Learn SQL','Practice creating databases, writing SQL queries, joins, and constraints.',2,'In Progress'),

(1,'Learn Spring Boot','Develop REST APIs and backend services using Spring Boot.',3,'Not Started'),

(1,'Learn React','Build responsive frontend interfaces using React.',4,'Not Started'),

(1,'Build Full Stack Projects','Combine Spring Boot and React to develop complete web applications.',5,'Not Started');

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
(1, 1, 'Completed', NOW(), 100),

(1, 2, 'In Progress', NULL, 60),

(1, 3, 'Not Started', NULL, 0),

(1, 4, 'Not Started', NULL, 0),

(1, 5, 'Not Started', NULL, 0);
