Great question — **running total** is one of those concepts interviewers *love* because it tests whether you truly understand **window frames**, not just ranking.

I’ll explain it **from zero**, with intuition, SQL, and interview traps.

---

# 🔁 What is a Running Total? (Plain English)

> A **running total** (also called **cumulative sum**) is a total that **keeps adding values as you move row by row**.

In other words:

```
Row 1 → sum of row 1
Row 2 → sum of row 1 + row 2
Row 3 → sum of row 1 + row 2 + row 3
...
```

You are **not resetting** the sum each row.

---

# 🧠 Real-life analogy

Your bank balance statement:

| Day | Deposit |
| --- | ------- |
| Mon | +100    |
| Tue | +50     |
| Wed | -20     |

Running balance:

* Mon → 100
* Tue → 150
* Wed → 130

That’s a **running total**.

---

# 🔑 SQL Concept Behind Running Total

Running total =

### `SUM()` + `WINDOW FRAME`

```sql
SUM(amount) OVER (
    ORDER BY date
    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
)
```

This means:

* Start from **first row**
* Keep summing up to **current row**

---

# 📌 BASIC EXAMPLE

### Table: `transactions`

| date | amount |
| ---- | ------ |
| 01   | 100    |
| 02   | 50     |
| 03   | 30     |

### Query

```sql
SELECT
    date,
    amount,
    SUM(amount) OVER (
        ORDER BY date
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS running_total
FROM transactions;
```

### Output

| date | amount | running_total |
| ---- | ------ | ------------- |
| 01   | 100    | 100           |
| 02   | 50     | 150           |
| 03   | 30     | 180           |

---

# 🧠 Why `GROUP BY` CANNOT do this

`GROUP BY` collapses rows:

```sql
SELECT SUM(amount) FROM transactions;
```

→ One row only ❌

Running total needs **row-by-row accumulation**, so we use window functions.

---

# 🧩 Running Total PER GROUP (VERY COMMON)

> “Running salary cost per department”

```sql
SELECT
    department,
    date,
    salary,
    SUM(salary) OVER (
        PARTITION BY department
        ORDER BY date
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS dept_running_total
FROM salaries;
```

🔹 Each department starts from 0
🔹 Accumulates independently

---

# ⚠️ VERY IMPORTANT INTERVIEW TRAP

### ❌ Missing `ROWS BETWEEN`

```sql
SUM(amount) OVER (ORDER BY date)
```

Depending on DB:

* Might default to **RANGE**
* Can give **wrong results** when dates repeat

✅ Safe & interview-ready:

```sql
ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
```

📌 **Say this in interview**:

> “I explicitly define the window frame to avoid RANGE vs ROWS ambiguity.”

That’s senior-level awareness.

---

# 🧠 RANGE vs ROWS (quick intuition)

| Type  | Behavior                              |
| ----- | ------------------------------------- |
| ROWS  | Counts physical rows                  |
| RANGE | Includes all equal values in ORDER BY |

For running totals → **always use ROWS**

---

# 🔥 Common Interview Variations

---

## 1️⃣ Running average

```sql
AVG(salary) OVER (
    ORDER BY date
    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
)
```

---

## 2️⃣ Month-wise cumulative revenue

```sql
SUM(revenue) OVER (
    ORDER BY month
    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
)
```

---

## 3️⃣ Detect sudden jump using running total

Combine with `LAG()`:

```sql
running_total - LAG(running_total)
```

---

# 🎯 INTERVIEW ONE-LINER (MEMORIZE)

> “A running total is a cumulative sum calculated row by row using a window function with an ordered frame from the beginning up to the current row.”

---

# ✅ When interviewer says “running total”, think:

```
SUM() + OVER + ORDER BY + UNBOUNDED PRECEDING → CURRENT ROW
```
[Explain the above query](/DATABASE/MY%20NOTES/Running%20total%20-%202.md)
---

If you want next:

* 🧪 Practice problems on running totals
* ⚠️ Common mistakes interviewers expect you to make
* 🔄 Running total vs moving average vs rolling window

Just tell me 👇
