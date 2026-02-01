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


---------- // RANKING FUNCTIONS DEMO // ----------
-- SELECT *,
-- 	   DENSE_RANK() OVER (
-- 		   PARTITION BY dept_id
-- 		   ORDER BY salary DESC
-- 	   ) AS rnk
-- FROM employees

-- FULL QUERY
SELECT *
FROM (
	SELECT *,
		   DENSE_RANK() OVER (
			   PARTITION BY dept_id
			   ORDER BY salary DESC
		   ) AS rnk
	FROM employees
)
WHERE rnk = 2
