Perfect. This is **pure interview ammo** now.
I’ll give you **Top 5 frequently asked interview questions**, each with:

* ✔️ clear problem statement
* ✔️ sample table + data
* ✔️ correct SQL query
* ✔️ expected output
* ✔️ interviewer tips

No fluff. Let’s go.

---

# 🔥 Top 5 Interview Questions Using LAG / LEAD

---

## 1️⃣ Month-over-Month Growth (MOST COMMON)

### ❓ Question

> Find **month-over-month salary growth** for each employee.

---

### Table: `employee_salary`

| emp_id | month | salary |
| ------ | ----- | ------ |
| 1      | Jan   | 50000  |
| 1      | Feb   | 55000  |
| 1      | Mar   | 60000  |

---

### ✅ SQL

```sql
SELECT
    emp_id,
    month,
    salary,
    salary - LAG(salary) OVER (
        PARTITION BY emp_id
        ORDER BY month
    ) AS salary_growth
FROM employee_salary;
```

---

### 📤 Output

| emp_id | month | salary | salary_growth |
| ------ | ----- | ------ | ------------- |
| 1      | Jan   | 50000  | NULL          |
| 1      | Feb   | 55000  | 5000          |
| 1      | Mar   | 60000  | 5000          |

---

### 🧠 Interview Tip

> If you hear *growth*, *difference*, *change* → think **LAG**

---

## 2️⃣ Detect Consecutive Days Activity

### ❓ Question

> Find users who logged in on **consecutive days**.

---

### Table: `user_logins`

| user_id | login_date |
| ------- | ---------- |
| 1       | 2024-01-01 |
| 1       | 2024-01-02 |
| 1       | 2024-01-04 |
| 2       | 2024-01-01 |
| 2       | 2024-01-02 |

---

### ✅ SQL

```sql
SELECT
    user_id,
    login_date
FROM (
    SELECT
        user_id,
        login_date,
        LAG(login_date) OVER (
            PARTITION BY user_id
            ORDER BY login_date
        ) AS prev_login
    FROM user_logins
) t
WHERE login_date = prev_login + INTERVAL '1 day';
```

---

### 📤 Output

| user_id | login_date |
| ------- | ---------- |
| 1       | 2024-01-02 |
| 2       | 2024-01-02 |

---

### 🧠 Interview Tip

> Date comparison + sequence → **LAG**

---

## 3️⃣ Find Increasing Sales Trend

### ❓ Question

> Find dates where **sales increased compared to previous day**.

---

### Table: `daily_sales`

| sale_date  | amount |
| ---------- | ------ |
| 2024-01-01 | 100    |
| 2024-01-02 | 120    |
| 2024-01-03 | 90     |
| 2024-01-04 | 150    |

---

### ✅ SQL

```sql
SELECT
    sale_date,
    amount
FROM (
    SELECT
        sale_date,
        amount,
        LAG(amount) OVER (ORDER BY sale_date) AS prev_amount
    FROM daily_sales
) t
WHERE amount > prev_amount;
```

---

### 📤 Output

| sale_date  | amount |
| ---------- | ------ |
| 2024-01-02 | 120    |
| 2024-01-04 | 150    |

---

### 🧠 Interview Tip

> Comparison of adjacent rows → **window function**

---

## 4️⃣ Compare Current Value with Next Value (LEAD)

### ❓ Question

> Find **days after which sales dropped**.

---

### Table: `daily_sales`

| sale_date  | amount |
| ---------- | ------ |
| 2024-01-01 | 200    |
| 2024-01-02 | 180    |
| 2024-01-03 | 220    |

---

### ✅ SQL

```sql
SELECT
    sale_date,
    amount
FROM (
    SELECT
        sale_date,
        amount,
        LEAD(amount) OVER (ORDER BY sale_date) AS next_amount
    FROM daily_sales
) t
WHERE amount > next_amount;
```

---

### 📤 Output

| sale_date  | amount |
| ---------- | ------ |
| 2024-01-01 | 200    |

---

### 🧠 Interview Tip

> “Next row” or “future value” → **LEAD**

---

## 5️⃣ Find First Occurrence After Change

### ❓ Question

> Find rows where **salary increased compared to previous month**.

---

### Table: `employee_salary`

| emp_id | month | salary |
| ------ | ----- | ------ |
| 1      | Jan   | 50000  |
| 1      | Feb   | 48000  |
| 1      | Mar   | 52000  |

---

### ✅ SQL

```sql
SELECT
    emp_id,
    month,
    salary
FROM (
    SELECT
        emp_id,
        month,
        salary,
        LAG(salary) OVER (
            PARTITION BY emp_id
            ORDER BY month
        ) AS prev_salary
    FROM employee_salary
) t
WHERE salary > prev_salary;
```

---

### 📤 Output

| emp_id | month | salary |
| ------ | ----- | ------ |
| 1      | Mar   | 52000  |

---

---

# 🧪 Practice Problems (With Answers)

---

## Practice 1️⃣

> Find **difference between current and previous order amount**.

### Table: `orders`

| order_id | order_date | amount |
| -------- | ---------- | ------ |
| 1        | 2024-01-01 | 100    |
| 2        | 2024-01-02 | 150    |
| 3        | 2024-01-03 | 130    |

### ✅ Answer

```sql
SELECT
    order_id,
    amount - LAG(amount) OVER (ORDER BY order_date) AS diff
FROM orders;
```

---

## Practice 2️⃣

> Show **previous login date** for each user.

### ✅ Answer

```sql
SELECT
    user_id,
    login_date,
    LAG(login_date) OVER (
        PARTITION BY user_id
        ORDER BY login_date
    ) AS prev_login
FROM user_logins;
```

---

## Practice 3️⃣

> Find **users who stopped activity** (no next login).

### ✅ Answer

```sql
SELECT
    user_id,
    login_date
FROM (
    SELECT
        user_id,
        login_date,
        LEAD(login_date) OVER (
            PARTITION BY user_id
            ORDER BY login_date
        ) AS next_login
    FROM user_logins
) t
WHERE next_login IS NULL;
```

---

## Practice 4️⃣

> Compare salary with **2 months ago**.

### ✅ Answer

```sql
SELECT
    emp_id,
    month,
    salary - LAG(salary, 2) OVER (
        PARTITION BY emp_id
        ORDER BY month
    ) AS diff_2_months
FROM employee_salary;
```

---

## Practice 5️⃣

> Find **sudden drops (>20%)** in sales.

### ✅ Answer

```sql
SELECT
    sale_date,
    amount
FROM (
    SELECT
        sale_date,
        amount,
        LAG(amount) OVER (ORDER BY sale_date) AS prev_amount
    FROM daily_sales
) t
WHERE amount < prev_amount * 0.8;
```

---

## 🎯 Final Interview Cheat Line

> **LAG and LEAD are used for comparing adjacent rows within an ordered partition, commonly applied in time-series analysis, trend detection, and change tracking.**

---

If you want next:

* 🚀 **LAG/LEAD vs SELF JOIN (why window functions are better)**
* 🚀 **Advanced problems (running streaks, churn, retention)**
* 🚀 **SQL window function roadmap for interviews**

Just say the word 👌
