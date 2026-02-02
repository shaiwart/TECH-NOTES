Perfect interview question. This is **NOT about memorizing one query** — it’s about understanding **ranking + partitioning + filtering**.
Once this clicks, you can solve *any* “top-N per group” problem confidently.

---

## Problem statement (generalized)

> **For each department, return the top 3 highest-paid employees.**

Translated into SQL thinking:

* **Group** → department
* **Order within group** → salary DESC
* **Pick top N per group** → 3

---

## Why this is NOT solvable with plain `GROUP BY`

Many people try:

```sql
SELECT department, MAX(salary)
FROM employees
GROUP BY department;
```

❌ **Wrong** because:

* `GROUP BY` collapses rows
* You lose individual employee rows
* You only get **one aggregate value**, not top-3 employees

👉 This is why **window functions** exist.

---

## Core concept you MUST understand (interview gold)

### **“Rank rows *within a group* without collapsing them”**

That’s exactly what **window functions** do.

---

## Step 1: ⭐️⭐️⭐️ Understand the mental model

Think in **two layers**:

### Layer 1 – Rank employees *inside each department*

> “Who is 1st, 2nd, 3rd highest salary **within the same department**?”

[See the example](#see-the-images-)

### Layer 2 – Filter

> “Keep only rank ≤ 3”

---

## Step 2: Window functions (the real hero)

### Key syntax

```sql
<window_function>() OVER (
    PARTITION BY column
    ORDER BY column DESC
)
```

* `PARTITION BY` → defines the group (department)
* `ORDER BY` → defines ranking logic (salary DESC)
* `OVER()` → **does NOT collapse rows**

---

## Step 3: Ranking functions (very important)

### 1️⃣ `ROW_NUMBER()`

* Always unique ranking
* No ties
* Best when question says **“exactly top N”**

### 2️⃣ `RANK()`

* Ties get same rank
* Skips numbers (1,1,3)

### 3️⃣ `DENSE_RANK()`

* Ties get same rank
* No gaps (1,1,2)

🔴 **Interviewers LOVE asking which one to use and why**

---

## Step 4: Correct solution (standard & interview-safe)

```sql
SELECT *
FROM (
	SELECT *,
		   DENSE_RANK() OVER (
			   PARTITION BY dept_id
			   ORDER BY salary DESC
		   ) AS rnk
	FROM employees
)
WHERE rnk <= 3
```

---

## Step-by-step explanation (this is what you say in interview)

### 1️⃣ Inner query

```sql
ROW_NUMBER() OVER (PARTITION BY department ORDER BY salary DESC)
```

This:

* Resets ranking **for each department**
* Orders salaries from highest to lowest
* Assigns:

  * 1 → highest paid
  * 2 → second highest
  * 3 → third highest

### 2️⃣ Outer query

```sql
WHERE rn <= 3
```

Filters only **top 3 per department**

``` sql
SELECT *,
		DENSE_RANK() OVER (
			PARTITION BY dept_id
			ORDER BY salary DESC
		) AS rank
FROM employees

-- OUTPUT WE GET. 👉 SEE THE LAST rank COLUMN.
-- We created this column and gave rank to it.
-- We will use this rank for row filtering later.
id  name dep_id  salary  rank
1	"A"	  10	 90000	  1
7	"G"	  10	 90000	  1
5	"E"	  10	 80000	  2
2	"B"	  10	 70000	  3
6	"F"	  10	 65000	  4
9	"I"	  20	 82000	  1
8	"H"	  20	 75000	  2
3	"C"	  20	 60000	  3
4	"D"	  20	 60000	  3
10	"J"	  20	 55000	  4
13	"M"	  30	 95000	  1
14	"N"	  30	 88000	  2
12	"L"	  30	 72000	  3
11	"K"	  30	 60000	  4
```
## See the images :
![alt text](/DATABASE/MY%20NOTES/images//1%20ROW_NUMBER.png)
![alt text](/DATABASE/MY%20NOTES/images//2%20RANK.png)
![alt text](/DATABASE/MY%20NOTES/images//3%20DENSE_RANK.png)

---

## Step 5: Example walkthrough (mental execution)

### Input table

| emp | dept | salary |
| --- | ---- | ------ |
| A   | HR   | 90k    |
| B   | HR   | 80k    |
| C   | HR   | 80k    |
| D   | HR   | 70k    |
| E   | IT   | 120k   |
| F   | IT   | 100k   |
| G   | IT   | 90k    |

### After window function

| emp | dept | salary | rn |
| --- | ---- | ------ | -- |
| A   | HR   | 90k    | 1  |
| B   | HR   | 80k    | 2  |
| C   | HR   | 80k    | 3  |
| D   | HR   | 70k    | 4  |
| E   | IT   | 120k   | 1  |
| F   | IT   | 100k   | 2  |
| G   | IT   | 90k    | 3  |

After `rn <= 3` → correct result.

---

## Step 6: When interviewer twists the question 😈

### 🔹 “Include ties in top 3 salaries”

Use **`DENSE_RANK()`**

```sql
DENSE_RANK() OVER (
    PARTITION BY department
    ORDER BY salary DESC
)
```

Why?

* Same salary = same rank
* No skipping

---

### 🔹 “Top 3 DISTINCT salaries per department”

Use **`DENSE_RANK()`**
(Not `ROW_NUMBER()`)

---

### 🔹 “Second highest salary per department”

```sql
WHERE rn = 2
```

---

### 🔹 “Top N per group” (generic rule)

> **Whenever you hear**:

* top / nth / highest / lowest
* per group / per department / per category

➡️ **Window function + ranking + filter**

---

## Step 7: Alternative (old SQL – avoid unless asked)

Using correlated subqueries (inefficient, ugly):

```sql
SELECT *
FROM employees e1
WHERE (
    SELECT COUNT(*)
    FROM employees e2
    WHERE e2.department = e1.department
      AND e2.salary > e1.salary
) < 3;
```

❌ Not recommended unless interviewer explicitly forbids window functions.

---

## Step 8: Interview-ready summary (memorize this)

> “To get top N rows per group, I use a window function with `PARTITION BY` to create independent rankings inside each group, then filter on the rank in an outer query. This avoids collapsing rows like `GROUP BY`.”

Say this and interviewer **knows you understand SQL deeply**.
