INSERT INTO roles (id, name, description) VALUES (1, 'ADMIN', 'Administrator role');
INSERT INTO roles (id, name, description) VALUES (2, 'USER', 'Regular user role');

INSERT INTO careers (id, title, description, category, averageSalary, demandLevel) VALUES
(1, 'Software Engineer', 'Build reliable web and mobile applications with modern engineering practices.', 'Technology', 120000.00, 'HIGH'),
(2, 'Data Analyst', 'Turn data into clear insights that support business decisions.', 'Data', 85000.00, 'HIGH'),
(3, 'UX Designer', 'Design intuitive digital experiences that blend usability and aesthetics.', 'Design', 95000.00, 'MEDIUM'),
(4, 'Cybersecurity Analyst', 'Protect systems and data from evolving threats.', 'Security', 105000.00, 'HIGH'),
(5, 'Mechanical Engineer', 'Design and improve machines, systems, and industrial equipment.', 'Engineering', 98000.00, 'HIGH'),
(6, 'Civil Engineer', 'Plan and oversee infrastructure projects like roads, bridges, and public works.', 'Engineering', 92000.00, 'HIGH'),
(7, 'Business Analyst', 'Bridge the gap between business needs and technical solutions.', 'Business', 89000.00, 'HIGH'),
(8, 'Marketing Manager', 'Lead campaigns, brand strategy, and customer growth initiatives.', 'Business', 86000.00, 'MEDIUM'),
(9, 'Registered Nurse', 'Provide patient care, health education, and support in clinical settings.', 'Health', 87000.00, 'HIGH'),
(10, 'Physician Assistant', 'Support doctors in diagnosing, treating, and caring for patients.', 'Health', 126000.00, 'HIGH'),
(11, 'Teacher', 'Inspire students and shape learning in classrooms and educational programs.', 'Education', 64000.00, 'MEDIUM'),
(12, 'Financial Advisor', 'Help individuals and businesses make strong financial decisions.', 'Finance', 94000.00, 'HIGH');

INSERT INTO skills (id, name, description, category) VALUES
(1, 'Java', 'Core language for backend application development.', 'Programming'),
(2, 'Spring Boot', 'Framework for building production-grade Java services.', 'Frameworks'),
(3, 'SQL', 'Language for querying and managing relational data.', 'Data'),
(4, 'UI/UX Design', 'Designing user-centered interfaces and flows.', 'Design'),
(5, 'AutoCAD', 'Drafting and design software used for engineering and architecture work.', 'Engineering'),
(6, 'Project Management', 'Planning, organizing, and leading work across teams and deadlines.', 'Business'),
(7, 'Patient Care', 'Providing compassionate support and care in healthcare settings.', 'Health'),
(8, 'Financial Analysis', 'Reviewing financial data to guide business and investment decisions.', 'Finance');
