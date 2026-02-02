
# 🪟 What is a Window Function? (1-liner)

> A **window function** performs a calculation **across a set of rows related to the current row**, **without collapsing rows** like `GROUP BY`.

---

# 🧠 BIG PICTURE CLASSIFICATION

All window functions fall into **3 major categories**:

| Category            | Purpose                      |
| ------------------- | ---------------------------- |
| 🔢 Ranking          | Assign position/order        |
| 🧮 Aggregate        | Aggregate without collapsing |
| 🧭 Value / Analytic | Look at nearby rows          |

👉 Interviewers expect this classification.

# 📌 WHAT YOU SHOULD MEMORIZE (MINIMUM SET)

✔ `ROW_NUMBER()`
✔ `RANK()`
✔ `DENSE_RANK()`
✔ `SUM() OVER`
✔ `AVG() OVER`
✔ `LAG()` / `LEAD()`

If you know these well → **you outperform 80% candidates**.

---

# 1️⃣ RANKING WINDOW FUNCTIONS (MOST IMPORTANT)

Used for:

* top / nth / highest / lowest
* per group ranking

---

### `ROW_NUMBER()`

* Unique sequence
* No ties

```sql
ROW_NUMBER() OVER (PARTITION BY dept ORDER BY salary DESC)
```

📌 Use when:

* “top 3 employees”
* “second latest order”

---

### `RANK()`

* Same value → same rank
* Skips numbers

```text
Salary: 100, 100, 90 → ranks: 1, 1, 3
```

📌 Use when:

* Ties allowed
* Rank gaps acceptable

---

### `DENSE_RANK()`

* Same value → same rank
* No gaps

```text
Salary: 100, 100, 90 → ranks: 1, 1, 2
```

📌 Use when:

* “top 3 distinct salaries”

---

### `NTILE(n)`

* Divides rows into **n buckets**

```sql
NTILE(4) OVER (ORDER BY salary DESC)
```

📌 Use when:

* Quartiles
* Performance bands
* Percentile grouping

---

# 2️⃣ AGGREGATE WINDOW FUNCTIONS

These are **same aggregates as GROUP BY**, but:

* Row count stays the same
* Aggregate is repeated per row

---

### `SUM() OVER`

```sql
SUM(salary) OVER (PARTITION BY dept)
```

➡️ Total department salary shown on each row

---

### `AVG() OVER`

```sql
AVG(salary) OVER (PARTITION BY dept)
```

➡️ Dept average per employee

---

### `COUNT() OVER`

```sql
COUNT(*) OVER (PARTITION BY dept)
```

➡️ Employees per department

---

### `MIN() OVER`, `MAX() OVER`

```sql
MAX(salary) OVER (PARTITION BY dept)
```

📌 Use when:

* Compare row vs group aggregate
* “employees earning above dept average”

---

# 3️⃣ VALUE / ANALYTIC WINDOW FUNCTIONS

Used to **peek at nearby rows**.

---

### `LAG()`

Previous row value

```sql
LAG(salary) OVER (PARTITION BY emp_id ORDER BY date)
```

📌 Use when:

* Salary increase detection
* Change tracking

---

### `LEAD()`

Next row value

```sql
LEAD(salary) OVER (PARTITION BY emp_id ORDER BY date)
```

---

### `FIRST_VALUE()`

First row in window

```sql
FIRST_VALUE(salary) OVER (PARTITION BY dept ORDER BY salary DESC)
```

---

### `LAST_VALUE()`

Last row in window
⚠️ Needs frame clause (important interview trap)

```sql
LAST_VALUE(salary) OVER (
  PARTITION BY dept
  ORDER BY salary
  ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
)
```

---

# 4️⃣ DISTRIBUTION / STATISTICAL FUNCTIONS

(Seen in Postgres / Oracle / some MySQL versions)

---

### `PERCENT_RANK()`

Relative rank (0 → 1)

```sql
PERCENT_RANK() OVER (ORDER BY salary)
```

---

### `CUME_DIST()`

Cumulative distribution

```sql
CUME_DIST() OVER (ORDER BY salary)
```

---

### `PERCENTILE_CONT()`

Continuous percentile

```sql
PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY salary)
```

---

# 5️⃣ WINDOW FRAME (ADVANCED BUT IMPORTANT)

Defines **how many rows** participate.

```sql
ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
```

Common frames:

| Frame               | Meaning       |
| ------------------- | ------------- |
| UNBOUNDED PRECEDING | From start    |
| CURRENT ROW         | Until current |
| UNBOUNDED FOLLOWING | Until end     |

📌 Used in:

* running totals
* cumulative averages

---

# 🧠 COMMON INTERVIEW QUESTIONS USING NON-RANKING WINDOWS

| Question                             | Function       |
| ------------------------------------ | -------------- |
| Salary increased from previous month | `LAG()`        |
| Running total                        | `SUM() OVER`   |
| Compare with department average      | `AVG() OVER`   |
| Find duplicates                      | `ROW_NUMBER()` |
| Growth percentage                    | `LAG()` + math |

---

# 🎯 INTERVIEW-READY SUMMARY (SAY THIS)

> “Window functions allow calculations across related rows without collapsing data. They are broadly classified into ranking, aggregate, and analytic functions, and are commonly used for top-N queries, comparisons within groups, and trend analysis.”
