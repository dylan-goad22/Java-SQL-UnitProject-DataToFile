SELECT employee.employee_id, employee.first_name, employee.last_name, employee.email, country.country_name, project.project_name, project.project_desc
FROM employee
JOIN project
ON employee.project = project.project_id
JOIN country
ON employee.country = country.country_id;
