
# LAG / LEAD in SQL (Window Functions)

## What problem do they solve?

Normally in SQL:

* Each row is **independent**
* You **cannot directly see previous or next row**

**`LAG` and `LEAD`** solve this by letting a row **look backward or forward** within an ordered dataset.

> 📌 They are **window functions**, not aggregate functions.

---

## Basic Definitions

### `LAG()`

> Access data from the **previous row**

```sql
LAG(column_name, offset, default_value)
OVER (PARTITION BY ... ORDER BY ...)
```

### `LEAD()`

> Access data from the **next row**

```sql
LEAD(column_name, offset, default_value)
OVER (PARTITION BY ... ORDER BY ...)
```

---

## Sample Table (Very Important)

### `employee_salary`

| emp_id | emp_name | department | salary | month |
| ------ | -------- | ---------- | ------ | ----- |
| 1      | A        | IT         | 50000  | Jan   |
| 1      | A        | IT         | 55000  | Feb   |
| 1      | A        | IT         | 60000  | Mar   |
| 2      | B        | HR         | 40000  | Jan   |
| 2      | B        | HR         | 42000  | Feb   |
| 2      | B        | HR         | 45000  | Mar   |

---

## Example 1: LAG – Compare Salary with Previous Month

### Requirement

> Show **previous month salary** for each employee.

### Query

```sql
SELECT
    emp_id,
    emp_name,
    month,
    salary,
    LAG(salary) OVER (
        PARTITION BY emp_id
        ORDER BY month
    ) AS prev_salary
FROM employee_salary;
```

### Output

| emp_id | emp_name | month | salary | prev_salary |
| ------ | -------- | ----- | ------ | ----------- |
| 1      | A        | Jan   | 50000  | NULL        |
| 1      | A        | Feb   | 55000  | 50000       |
| 1      | A        | Mar   | 60000  | 55000       |
| 2      | B        | Jan   | 40000  | NULL        |
| 2      | B        | Feb   | 42000  | 40000       |
| 2      | B        | Mar   | 45000  | 42000       |

### Key Observations

* First row → `NULL` (no previous row)
* `PARTITION BY emp_id` ensures **employee-wise comparison**
* `ORDER BY month` defines **row sequence**

---

## Example 2: Salary Growth (Very Common Interview Question)

### Requirement

> Calculate **salary increase** month over month.

### Query

```sql
SELECT
    emp_id,
    emp_name,
    month,
    salary,
    salary - LAG(salary) OVER (
        PARTITION BY emp_id
        ORDER BY month
    ) AS salary_increase
FROM employee_salary;
```

### Output

| emp_id | month | salary | salary_increase |
| ------ | ----- | ------ | --------------- |
| 1      | Jan   | 50000  | NULL            |
| 1      | Feb   | 55000  | 5000            |
| 1      | Mar   | 60000  | 5000            |
| 2      | Jan   | 40000  | NULL            |
| 2      | Feb   | 42000  | 2000            |
| 2      | Mar   | 45000  | 3000            |

📌 **This question alone appears in many interviews.**

---

## Example 3: LEAD – Look Ahead (Next Row)

### Requirement

> Show **next month salary**

### Query

```sql
SELECT
    emp_id,
    emp_name,
    month,
    salary,
    LEAD(salary) OVER (
        PARTITION BY emp_id
        ORDER BY month
    ) AS next_salary
FROM employee_salary;
```

### Output

| emp_id | month | salary | next_salary |
| ------ | ----- | ------ | ----------- |
| 1      | Jan   | 50000  | 55000       |
| 1      | Feb   | 55000  | 60000       |
| 1      | Mar   | 60000  | NULL        |
| 2      | Jan   | 40000  | 42000       |
| 2      | Feb   | 42000  | 45000       |
| 2      | Mar   | 45000  | NULL        |

---

## Example 4: Offset (Skip Rows)

### Requirement

> Compare salary with **2 months ago**

```sql
SELECT
    emp_id,
    month,
    salary,
    LAG(salary, 2) OVER (
        PARTITION BY emp_id
        ORDER BY month
    ) AS salary_2_months_ago
FROM employee_salary;
```

### Output

| emp_id | month | salary | salary_2_months_ago |
| ------ | ----- | ------ | ------------------- |
| 1      | Jan   | 50000  | NULL                |
| 1      | Feb   | 55000  | NULL                |
| 1      | Mar   | 60000  | 50000               |

---

## Example 5: Default Value (Avoid NULL)

```sql
LAG(salary, 1, 0) OVER (...)
```

If previous row doesn’t exist → return `0` instead of `NULL`.

---

## Real-World Use Cases (Interview Gold)

### 1️⃣ Month-over-Month growth

### 2️⃣ Detect increase / decrease

### 3️⃣ Compare current vs previous transaction

### 4️⃣ Time-series analysis

### 5️⃣ Ranking changes (position change)

---

## Common Interview Traps ⚠️

### ❌ Forgetting `ORDER BY`

> LAG/LEAD **must** know row order.

### ❌ Missing `PARTITION BY`

> Causes comparison across **wrong groups**.

### ❌ Confusing with aggregate functions

> `LAG` ≠ `SUM`, `AVG`

---

## One-Line Interview Explanation

> **LAG and LEAD allow accessing previous or next row values within an ordered partition, commonly used for time-based comparisons like growth or trends.**

---

* 🔥 **Top 5 interview questions using LAG/LEAD** [page link](/DATABASE/MY%20NOTES/Lag-Lead-Interview-problems.md)
* 🔥 **Practice problems with answers** [page link](/DATABASE/MY%20NOTES/Lag-Lead-Interview-problems.md)
