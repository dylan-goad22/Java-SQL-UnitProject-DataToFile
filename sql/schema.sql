--CREATE TABLE country (
--    country_id SERIAL PRIMARY KEY,
--    country_name VARCHAR(50) NOT NULL
--);
--
--CREATE TABLE project (
--    project_id SERIAL PRIMARY KEY,
--    project_name VARCHAR(50) NOT NULL,
--    project_desc TEXT NOT NULL
--);

CREATE TABLE employee (
    employee_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(319) NOT NULL,
    country INT,
    project INT,
    FOREIGN KEY (country) REFERENCES country(country_id)
    ON DELETE CASCADE,
    FOREIGN KEY (project) REFERENCES project(project_id)
    ON DELETE CASCADE
);