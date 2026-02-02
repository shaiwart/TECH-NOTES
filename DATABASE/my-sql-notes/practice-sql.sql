-- Create the table
-- CREATE TABLE employees (
--     id      INTEGER PRIMARY KEY,
--     name    VARCHAR(50),
--     dept_id INTEGER,
--     salary  INTEGER
-- );

-- Insert the data
-- INSERT INTO employees (id, name, dept_id, salary) VALUES
-- (1, 'A', 10, 50000),
-- (2, 'B', 10, 70000),
-- (3, 'C', 20, 60000),
-- (4, 'D', 20, 60000);
-- (5, 'E', 10, 80000),
-- (6, 'F', 10, 65000),
-- (7, 'G', 10, 90000),
-- (8, 'H', 20, 75000),
-- (9, 'I', 20, 82000),
-- (10, 'J', 20, 55000),
-- (11, 'K', 30, 60000),
-- (12, 'L', 30, 72000),
-- (13, 'M', 30, 95000),
-- (14, 'N', 30, 88000);


-- 
-- select *
-- from employees
-- order by dept_id


-- 1️⃣ RANKING WINDOW FUNCTIONS (MOST IMPORTANT)
-- SELECT *,
-- 	   DENSE_RANK() OVER (
-- 		   PARTITION BY dept_id
-- 		   ORDER BY salary DESC
-- 	   ) AS rnk
-- FROM employees

-- FULL QUERY
-- SELECT *
-- FROM (
-- 	SELECT *,
-- 		   DENSE_RANK() OVER (
-- 			   PARTITION BY dept_id
-- 			   ORDER BY salary DESC
-- 		   ) AS rnk
-- 	FROM employees
-- ) t
-- WHERE rnk = 2

-- 2️⃣ AGGREGATE WINDOW FUNCTIONS
-- 	SELECT *,
-- 		   AVG(salary) OVER (
-- 			   PARTITION BY dept_id
-- 			   -- ORDER BY name
-- 		   ) AS avg_salary,
		   
-- 		   MIN(salary) OVER (
-- 			   PARTITION BY dept_id
-- 		   ) AS min_salary,
		   
-- 		   MAX(salary) OVER (
-- 			   PARTITION BY dept_id
-- 		   ) AS max_salary,
		   
-- 		   SUM(salary) OVER (
-- 			   PARTITION BY dept_id
-- 		   ) AS total_salary_of_dept,
		   
-- 		   COUNT(*) OVER (
-- 			   PARTITION BY dept_id
-- 		   ) AS total_emp
-- 	FROM employees


-- 3️⃣ VALUE / ANALYTIC WINDOW FUNCTIONS
-- SELECT *,
-- 	   FIRST_VALUE(salary) OVER (
-- 		   PARTITION BY dept_id 
-- 		   ORDER BY salary DESC
-- 	   ) AS first_value
-- FROM employees



-- 4️⃣ LAG/LEAD
-- CREATE TABLE employee_salary (
--     emp_id    INTEGER,
--     month     DATE,
--     salary    INTEGER
-- );

-- INSERT INTO employee_salary (emp_id, month, salary) VALUES
-- -- Employee A (id = 1)
-- (1, '2024-01-01', 50000),
-- (1, '2024-02-01', 52000),
-- (1, '2024-03-01', 55000),

-- -- Employee B (id = 2)
-- (2, '2024-01-01', 70000),
-- (2, '2024-02-01', 70000),
-- (2, '2024-03-01', 75000),

-- -- Employee C (id = 3)
-- (3, '2024-01-01', 60000),
-- (3, '2024-02-01', 63000),
-- (3, '2024-03-01', 63000),

-- -- Employee D (id = 4)
-- (4, '2024-01-01', 60000),
-- (4, '2024-02-01', 60000),
-- (4, '2024-03-01', 60000),

-- -- Employee E (id = 5)
-- (5, '2024-01-01', 80000),
-- (5, '2024-02-01', 85000),
-- (5, '2024-03-01', 90000);


---------- // Previous month salary
SELECT *, LAG(salary) OVER(
	PARTITION BY emp_id
	ORDER BY month
	) AS prev_salary
FROM employee_salary

---------- // Salary diff
-- SELECT *, salary - LAG(salary) OVER(
-- 	PARTITION BY emp_id
-- 	ORDER BY month
-- 	) AS salary_diff
-- FROM employee_salary


---------- // Skip months
-- SELECT *, LAG(salary, 2) OVER(
-- 	PARTITION BY emp_id
-- 	ORDER BY month
-- 	) AS 2_month_old_salary
-- FROM employee_salary

---------- // Default value instead of NULL
SELECT *, LAG(salary, 1, 0) OVER(
	PARTITION BY emp_id
	ORDER BY month
	)
FROM employee_salary