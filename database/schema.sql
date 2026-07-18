CREATE DATABASE careerpath_ai;

USE careerpath_ai;

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    experienceLevel VARCHAR(50),
    createdAt DATETIME NOT NULL,
    roleId INT,
    FOREIGN KEY (roleId) REFERENCES roles(id)
);
CREATE TABLE careers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    averageSalary DECIMAL(10,2),
    demandLevel VARCHAR(50)
);
CREATE TABLE skills (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    category VARCHAR(100)
);
CREATE TABLE career_skills (
    id INT AUTO_INCREMENT PRIMARY KEY,
    careerId INT NOT NULL,
    skillId INT NOT NULL,
    importance VARCHAR(20),

    FOREIGN KEY (careerId) REFERENCES careers(id),
    FOREIGN KEY (skillId) REFERENCES skills(id),

    UNIQUE (careerId, skillId)
);
CREATE TABLE user_skills (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId Int NOT NULL,
    skillId INT NOT Null,
    level VARCHAR(50),
    FOREIGN KEY (userId) REFERENCES users(id),
    FOREIGN KEY (skillId) REFERENCES skills(id),
    UNIQUE (userId , skillId)
);
CREATE TABLE roadmaps(
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    careerId INT NOT NULL,
    title VARCHAR(150),
    status VARCHAR(50),
    createdAt DATETIME NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(id),
    FOREIGN KEY (careerId) REFERENCES careers(id)
);
CREATE TABLE roadmap_steps (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roadmapId INT NOT NULL,
    title VARCHAR(150),
    description TEXT ,
    stepOrder INT,
    status VARCHAR(50),
    FOREIGN KEY (roadmapId) REFERENCES roadmaps(id) ,
    UNIQUE(roadmapId, stepOrder)
);

CREATE TABLE learning_resources (
    id INT AUTO_INCREMENT PRIMARY KEY,
    skillId INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    url VARCHAR(255),
    type VARCHAR(50),
    provider VARCHAR(100),
    isFree BOOLEAN,

    FOREIGN KEY (skillId) REFERENCES skills(id)
);

CREATE TABLE progress_tracking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    roadmapStepId INT NOT NULL,
    status VARCHAR(50),
    completionDate DATETIME,
    progressPercentage INT,

    FOREIGN KEY (userId) REFERENCES users(id),
    FOREIGN KEY (roadmapStepId) REFERENCES roadmap_steps(id)
);
    
    
    
