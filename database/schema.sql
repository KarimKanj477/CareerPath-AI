CREATE DATABASE IF NOT EXISTS careerpath_ai
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE careerpath_ai;

-- ---------------------------------------------------------------------
-- roles
-- ---------------------------------------------------------------------
CREATE TABLE roles (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    createdAt   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- users
-- ---------------------------------------------------------------------
CREATE TABLE users (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    firstname        VARCHAR(100) NOT NULL,
    lastname         VARCHAR(100) NOT NULL,
    email            VARCHAR(150) NOT NULL UNIQUE,
    password         VARCHAR(255) NOT NULL,
    experienceLevel  VARCHAR(50),
    roleId           INT,
    createdAt        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (roleId) REFERENCES roles(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- certifications  (was missing — README documents it, schema.sql didn't have it)
-- ---------------------------------------------------------------------
CREATE TABLE certifications (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    userId         INT NOT NULL,
    name           VARCHAR(150) NOT NULL,
    issuer         VARCHAR(150),
    issueDate      DATE,
    expiryDate     DATE,
    credentialUrl  VARCHAR(255),
    createdAt      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- careers
-- ---------------------------------------------------------------------
CREATE TABLE careers (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(100) NOT NULL,
    description   TEXT,
    category      VARCHAR(100),
    averageSalary DECIMAL(10,2),
    demandLevel   VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',
    createdAt     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    UNIQUE (title),
    CONSTRAINT chk_careers_demandLevel CHECK (demandLevel IN ('LOW','MEDIUM','HIGH'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- skills
-- ---------------------------------------------------------------------
CREATE TABLE skills (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    category    VARCHAR(100),
    createdAt   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- career_skills
-- ---------------------------------------------------------------------
CREATE TABLE career_skills (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    careerId   INT NOT NULL,
    skillId    INT NOT NULL,
    importance VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',
    createdAt  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (careerId) REFERENCES careers(id) ON DELETE CASCADE,
    FOREIGN KEY (skillId) REFERENCES skills(id) ON DELETE CASCADE,

    UNIQUE (careerId, skillId),
    CONSTRAINT chk_career_skills_importance CHECK (importance IN ('LOW','MEDIUM','HIGH'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- user_skills
-- ---------------------------------------------------------------------
CREATE TABLE user_skills (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    userId    INT NOT NULL,
    skillId   INT NOT NULL,
    level     VARCHAR(50),
    createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (skillId) REFERENCES skills(id) ON DELETE CASCADE,

    UNIQUE (userId, skillId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- roadmaps
-- ---------------------------------------------------------------------
CREATE TABLE roadmaps (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    userId    INT NOT NULL,
    careerId  INT NOT NULL,
    title     VARCHAR(150),
    status    VARCHAR(50) NOT NULL DEFAULT 'NOT_STARTED',
    createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (careerId) REFERENCES careers(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- roadmap_steps
-- ---------------------------------------------------------------------
CREATE TABLE roadmap_steps (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    roadmapId   INT NOT NULL,
    skillId     INT NULL,
    title       VARCHAR(150),
    description TEXT,
    stepOrder   INT,
    status      VARCHAR(50),
    FOREIGN KEY (roadmapId) REFERENCES roadmaps(id) ON DELETE CASCADE,
    FOREIGN KEY (skillId) REFERENCES skills(id) ON DELETE SET NULL

    UNIQUE (roadmapId, stepOrder)
);ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- learning_resources
-- ---------------------------------------------------------------------
CREATE TABLE learning_resources (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    skillId   INT NOT NULL,
    title     VARCHAR(150) NOT NULL,
    url       VARCHAR(255),
    type      VARCHAR(50),
    provider  VARCHAR(100),
    isFree    BOOLEAN NOT NULL DEFAULT TRUE,
    createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (skillId) REFERENCES skills(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- progress_tracking
-- ---------------------------------------------------------------------
CREATE TABLE progress_tracking (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    userId              INT NOT NULL,
    roadmapStepId       INT NOT NULL,
    status              VARCHAR(50) NOT NULL DEFAULT 'NOT_STARTED',
    completionDate      DATETIME,
    progressPercentage  INT NOT NULL DEFAULT 0,
    createdAt           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (roadmapStepId) REFERENCES roadmap_steps(id) ON DELETE CASCADE,

    UNIQUE (userId, roadmapStepId),
    CONSTRAINT chk_progress_percentage CHECK (progressPercentage BETWEEN 0 AND 100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- chat_history  (was missing — README documents the AI Advisor table)
-- ---------------------------------------------------------------------
CREATE TABLE chat_history (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    userId    INT NOT NULL,
    sender    VARCHAR(10) NOT NULL,   -- 'USER' or 'AI'
    message   TEXT NOT NULL,
    createdAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_chat_sender CHECK (sender IN ('USER','AI'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
