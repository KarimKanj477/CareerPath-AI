CREATE DATABASE IF NOT EXISTS careerpath_ai
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE careerpath_ai;

-- ---------------------------------------------------------------------
-- roles
-- ---------------------------------------------------------------------
CREATE TABLE roles (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- users
-- ---------------------------------------------------------------------
CREATE TABLE users (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    firstname       VARCHAR(100) NOT NULL,
    lastname        VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    experienceLevel VARCHAR(50),
    createdAt       DATETIME NOT NULL,
    roleId          INT,

    FOREIGN KEY (roleId)
        REFERENCES roles(id)
       
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
CREATE TABLE careers (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(100) NOT NULL,
    description    TEXT,
    category       VARCHAR(100),
    average_salary DECIMAL(10,2),
    demand_level   VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- skills
-- ---------------------------------------------------------------------
CREATE TABLE skills (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    category    VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- career_skills
-- ---------------------------------------------------------------------
CREATE TABLE career_skills (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    careerId   INT NOT NULL,
    skillId    INT NOT NULL,
    importance VARCHAR(20),

    FOREIGN KEY (careerId)
        REFERENCES careers(id),
       

    FOREIGN KEY (skillId)
        REFERENCES skills(id),
      

    UNIQUE (careerId, skillId)
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

    FOREIGN KEY (userId) REFERENCES users(id) ,
    FOREIGN KEY (skillId) REFERENCES skills(id) ,

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
    status    VARCHAR(50),
    createdAt DATETIME NOT NULL,

    FOREIGN KEY (userId)
        REFERENCES users(id)
        ,

    FOREIGN KEY (careerId)
        REFERENCES careers(id)
       
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

                               FOREIGN KEY (roadmapId)
                                   REFERENCES roadmaps(id),

                               FOREIGN KEY (skillId)
                                   REFERENCES skills(id)
                                   ON DELETE SET NULL,

                               UNIQUE (roadmapId, stepOrder)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
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
                                    isFree    BOOLEAN,

                                    FOREIGN KEY (skillId)
                                        REFERENCES skills(id)
                                        
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- progress_tracking
-- ---------------------------------------------------------------------
CREATE TABLE progress_tracking (
                                   id                 INT AUTO_INCREMENT PRIMARY KEY,
                                   userId             INT NOT NULL,
                                   roadmapStepId      INT NOT NULL,
                                   status             VARCHAR(50),
                                   completionDate     DATETIME,
                                   progressPercentage INT,

                                   CONSTRAINT uk_progress_user_step
                                       UNIQUE (userId, roadmapStepId),

                                   FOREIGN KEY (userId)
                                       REFERENCES users(id),

                                   FOREIGN KEY (roadmapStepId)
                                       REFERENCES roadmap_steps(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



