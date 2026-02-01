Below are **complete, interview-ready SQL notes** for a **~2.5 years** software developer. This covers what interviewers actually ask: queries, joins, aggregation, window functions, indexes, transactions, normalization, and common patterns.

---

## 1) Core SQL Mental Model

### Tables + Rows + Columns

* **Row** = one record
* **Column** = attribute
* **Primary Key (PK)** = uniquely identifies a row (NOT NULL + UNIQUE)
* **Foreign Key (FK)** = column referencing another table’s PK (enforces referential integrity)

### Execution order (important for tricky questions)

When you write:

```sql
SELECT ...
FROM ...
JOIN ...
WHERE ...
GROUP BY ...
HAVING ...
ORDER BY ...
LIMIT ...
```

The logical order is:
``` sql
1. FROM / JOIN (Load and merge tables)
2. WHERE (Filter rows—Alias not available yet)
3. GROUP BY (Aggregate rows)
4. HAVING (Filter aggregates) 👉 HAVING is for conditions on a group, not on individual rows
5. SELECT (Choose columns—Aliases created here)
6. DISTINCT (Deduplicate)
7. ORDER BY (Sort—Alias is available here)
8. LIMIT / OFFSET (Pagination)
```

**Key:** `WHERE` filters rows **before grouping**, `HAVING` filters groups **after grouping**.

---

## 2) Data Types & NULL

### NULL rules (very important)

* `NULL` means **unknown / missing**, not 0 or empty string.
* `NULL = NULL` is **not true**. It’s **unknown**.
* Use:

  * `IS NULL`, `IS NOT NULL`
  * `COALESCE(col, default)` to replace NULL

Example:

```sql
SELECT COALESCE(middle_name, '') FROM users;
```

---

## 3) SELECT Basics

### Projection + Filtering

> Projection = choosing which columns to display in the result set.
Filtering → “Which rows should I keep?”

```sql
SELECT id, name
FROM users
WHERE age >= 18 AND city = 'Pune';
```

### DISTINCT

```sql
SELECT DISTINCT city FROM users;
```

### IN / BETWEEN / LIKE

```sql
WHERE status IN ('ACTIVE', 'PENDING')
WHERE created_at BETWEEN '2026-01-01' AND '2026-01-31'
WHERE email LIKE '%@gmail.com'
```

---

## 4) Joins (most asked topic)

Assume:

* `orders(user_id)` references `users(id)`

### INNER JOIN (only matching)

```sql
SELECT u.id, u.name, o.id AS order_id
FROM users u
JOIN orders o ON o.user_id = u.id;
```

### LEFT JOIN (all from left + matches from right)

**Most common join in real projects**

```sql
SELECT u.id, u.name, o.id AS order_id
FROM users u
LEFT JOIN orders o ON o.user_id = u.id;
```

* users with no orders → `order_id` will be NULL.

### RIGHT JOIN

Same as LEFT but from the other side (less used).

### FULL OUTER JOIN

All from both sides (some DBs support, e.g. PostgreSQL).

### CROSS JOIN

Cartesian product (rare; used for generating combinations).

---

## 5) Aggregation & Grouping

### COUNT / SUM / AVG / MIN / MAX

``` sql
-- ORDERS TABLE
order_id | user_id | order_date | amount
---------+---------+------------+--------
101      | 1       | 2024-01-05 | 1200
102      | 1       | 2024-01-20 | 800
103      | 2       | 2024-02-10 | 1500
104      | 2       | 2024-02-18 | 2200
105      | 2       | 2024-03-01 | 1800
106      | 3       | 2024-01-15 | 600
107      | 4       | 2024-02-05 | 900
108      | 5       | 2024-03-10 | 2500
109      | 5       | 2024-03-18 | 3000
110      | 6       | 2024-02-22 | 2000
```

```sql
SELECT user_id, COUNT(*) AS total_orders
FROM orders
GROUP BY user_id;

-- RESULT
user_id | total_orders
--------+--------------
1       | 5
2       | 12
3       | 3
```

### HAVING (filter groups)

```sql
SELECT user_id, COUNT(*) AS total_orders
FROM orders
GROUP BY user_id
HAVING COUNT(*) >= 5;
```
#### HAVING in detail
👉 HAVING is for conditions on a group, not on individual rows. So you can put only things that make sense after grouping.
[What things we can write in HAVING -- detailed note](/DATABASE/sql.having.md)

### COUNT(*) vs COUNT(col)

* `COUNT(*)` counts rows (includes NULLs)
* `COUNT(col)` counts only non-NULL values

---

## 6) Subqueries (easy + common)

### IN subquery

```sql
SELECT *
FROM users
WHERE id IN (SELECT user_id FROM orders);
```

### Correlated subquery

```sql
SELECT u.*
FROM users u
WHERE EXISTS (
  SELECT 1
  FROM orders o
  WHERE o.user_id = u.id
);
```

**IN vs EXISTS (interview summary)**

* `EXISTS` often better for large datasets (can stop early)
* `IN` is fine but can behave oddly with NULLs

---

## 7) Window Functions (must-know for 2–4 YOE)

Window functions let you compute values **without collapsing rows** like GROUP BY.

### ROW_NUMBER / RANK / DENSE_RANK

**Top N per group** pattern:

```sql
SELECT *
FROM (
  SELECT
    e.*,
    ROW_NUMBER() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS rn
  FROM employees e
) t
WHERE rn <= 3;
```

* `ROW_NUMBER()` → 1,2,3,4… unique
* `RANK()` → ties share rank, skips numbers (1,1,3)
* `DENSE_RANK()` → ties share rank, no skip (1,1,2)

### Running total

```sql
SELECT
  user_id,
  created_at,
  amount,
  SUM(amount) OVER (PARTITION BY user_id ORDER BY created_at) AS running_total
FROM payments;
```

### LAG / LEAD (difference between rows)

```sql
SELECT
  user_id,
  created_at,
  amount,
  amount - LAG(amount) OVER (PARTITION BY user_id ORDER BY created_at) AS diff
FROM payments;
```

---

## 8) Set Operations

### UNION vs UNION ALL

```sql
SELECT city FROM users
UNION ALL
SELECT city FROM vendors;
```

* `UNION` removes duplicates (extra cost)
* `UNION ALL` keeps duplicates (faster)

---

## 9) Common Interview Query Patterns

### 1) Second highest salary

```sql
SELECT MAX(salary) AS second_highest
FROM employees
WHERE salary < (SELECT MAX(salary) FROM employees);
```

### 2) Duplicate rows by email

```sql
SELECT email, COUNT(*) c
FROM users
GROUP BY email
HAVING COUNT(*) > 1;
```

### 3) Users with no orders

```sql
SELECT u.*
FROM users u
LEFT JOIN orders o ON o.user_id = u.id
WHERE o.id IS NULL;
```

### 4) Delete duplicates (keep latest)

(Depends on DB; using window pattern)

```sql
DELETE FROM users
WHERE id IN (
  SELECT id
  FROM (
    SELECT id,
           ROW_NUMBER() OVER (PARTITION BY email ORDER BY created_at DESC) rn
    FROM users
  ) t
  WHERE rn > 1
);
```

### 5) Find mismatched records (anti-join)

```sql
SELECT a.*
FROM table_a a
LEFT JOIN table_b b ON b.key = a.key
WHERE b.key IS NULL;
```

---

## 10) Indexes (interview favorite)

### What is an index?

A data structure (often **B-Tree**) that speeds up reads (WHERE/JOIN/ORDER BY) at cost of:

* slower writes (INSERT/UPDATE/DELETE)
* extra storage

### When index helps

* `WHERE col = ...`
* `WHERE col BETWEEN ...`
* `JOIN ON col`
* `ORDER BY col`
* `GROUP BY col` (sometimes)

### When index might NOT help

* very small tables
* low selectivity (e.g., `gender` with only M/F)
* functions on indexed column: `WHERE LOWER(email) = ...` (unless functional index exists)

### Composite index order matters

Index `(a, b)` helps:

* `WHERE a = ...`
* `WHERE a = ... AND b = ...`
  But NOT efficiently:
* `WHERE b = ...` alone

---

## 11) Transactions & ACID (must know)

### Transaction

A set of operations that must succeed or fail together.

```sql
BEGIN;

UPDATE accounts SET balance = balance - 100 WHERE id = 1;
UPDATE accounts SET balance = balance + 100 WHERE id = 2;

COMMIT; -- or ROLLBACK;
```

### ACID

* **A**tomicity: all or nothing
* **C**onsistency: constraints always valid
* **I**solation: transactions don’t interfere incorrectly
* **D**urability: committed data survives crash

### Isolation levels (basic understanding)

* Read Uncommitted (dirty reads)
* Read Committed
* Repeatable Read
* Serializable (most strict)

Also know anomalies:

* Dirty read
* Non-repeatable read
* Phantom read

---

## 12) Locks & Deadlocks (high-level)

* Writes generally take locks.
* Deadlock: T1 waits for T2 lock, T2 waits for T1 lock → DB kills one transaction.

Avoid by:

* consistent order of updates
* small transactions
* proper indexing (reduces lock time)

---

## 13) Constraints & Keys

* `PRIMARY KEY`
* `FOREIGN KEY`
* `UNIQUE`
* `NOT NULL`
* `CHECK`
* `DEFAULT`

Example:

```sql
CREATE TABLE orders (
  id BIGINT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  amount DECIMAL(10,2) CHECK (amount >= 0),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);
```

---

## 14) Normalization (asked sometimes)

* **1NF**: atomic columns (no arrays/sets inside a cell)
* **2NF**: no partial dependency on composite key
* **3NF**: no transitive dependency (non-key depends on non-key)

Why normalize?

* less redundancy
* fewer update anomalies

When denormalize?

* analytics / performance read-heavy cases (carefully)

---

## 15) Views

A stored query:

```sql
CREATE VIEW active_users AS
SELECT * FROM users WHERE status = 'ACTIVE';
```

* Simplifies queries
* Can help security abstraction
* Not always performance (depends on DB/materialization)

---

## 16) Practical “Project SQL” Tips Interviewers Like

* Use **EXPLAIN** / query plan when performance issues happen.
* Avoid `SELECT *` in production APIs (fetch only needed columns).
* Always index FK columns (`orders.user_id`).
* Use pagination carefully:

  * `LIMIT/OFFSET` is easy but slow for deep pages
  * keyset pagination is better (e.g., `WHERE id > last_id`)

---

## 17) Quick Interview Checklist (revise this 30 mins before)

You should be able to:

* Explain INNER vs LEFT join with example
* Use GROUP BY + HAVING correctly
* Write query: “top 3 per group” using window functions
* Find duplicates + delete duplicates
* Explain index + composite index ordering
* Explain transaction + ACID + isolation anomalies (basic)
* Explain NULL behavior
* Read query and predict output

---

If you tell me **which DB** your interview expects (**MySQL / PostgreSQL / SQL Server / Oracle**) and whether it’s more **product** or **analytics**, I’ll tailor these notes into a **1-page rapid revision sheet + 25 most common interview questions with answers + practice queries**.
