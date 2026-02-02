Here are comprehensive SQL notes tailored for a **2.5-year experienced developer**. This level expects you to move beyond simple `SELECT` statements and understand performance, complex joins, and analytical functions.

---

### **I. Core Architecture (The "How it works" Questions)**

#### **1. Order of Execution**

SQL is declarative. You write *what* you want, but the engine executes it in a specific logical order. This is a favorite interview trick question (e.g., "Why can't I use a column alias in the `WHERE` clause?").

**Execution Order:**

1. **FROM / JOIN** (Load and merge tables)
2. **WHERE** (Filter rows—*Alias not available yet*)
3. **GROUP BY** (Aggregate rows)
4. **HAVING** (Filter aggregates)
5. **SELECT** (Choose columns—*Aliases created here*)
6. **DISTINCT** (Deduplicate)
7. **ORDER BY** (Sort—*Alias is available here*)
8. **LIMIT / OFFSET** (Pagination)

#### **2. ACID Properties**

Standard theory check.

* **A**tomicity: All or nothing. (If one part fails, the whole transaction rolls back).
* **C**onsistency: The DB moves from one valid state to another (constraints enforced).
* **I**solation: Transactions do not interfere with each other (see Isolation Levels below).
* **D**urability: Once committed, data is saved even if power fails.

---

### **II. Essential Joins & Set Operations**

#### **1. The Joins**

* **INNER JOIN**: Only matching rows.
* **LEFT JOIN**: All rows from Left, matches from Right (`NULL` if no match).
* **RIGHT JOIN**: All rows from Right, matches from Left.
* **FULL OUTER JOIN**: All rows from both sides.
* **CROSS JOIN**: Cartesian product (Row A × Row B). **Dangerous** on large tables.
* **SELF JOIN**: Joining a table to itself (e.g., "Find an employee's manager" where both are in the `Employee` table).

#### **2. NULL Handling in Joins**

* `NULL` never equals `NULL`. `NULL = NULL` is False.
* To check for nulls, always use `IS NULL` or `IS NOT NULL`.

#### **3. UNION vs UNION ALL**

* **UNION**: Combines results and **removes duplicates**. (Slower, performs a sort/hash).
* **UNION ALL**: Combines results and **keeps duplicates**. (Faster, just appends).

---

### **III. Intermediate Logic (The "Daily Work" Questions)**

#### **1. Aggregation: WHERE vs HAVING**

* **WHERE**: Filters **rows** before grouping.
* **HAVING**: Filters **groups** after grouping.
* *Bad:* `SELECT dept, AVG(sal) FROM emp WHERE AVG(sal) > 1000 GROUP BY dept` (Error!)
* *Good:* `SELECT dept, AVG(sal) FROM emp GROUP BY dept HAVING AVG(sal) > 1000`



#### **2. Subqueries vs CTEs**

* **Subquery**: Nested query. Can be hard to read.
* **CTE (Common Table Expression)**: Defined with `WITH`. More readable, reusable in the same query. **Preferred in interviews.**
```sql
WITH SalesData AS (
    SELECT id, amount FROM Sales WHERE year = 2023
)
SELECT * FROM SalesData WHERE amount > 100;

```



#### **3. DELETE vs TRUNCATE vs DROP**

* **DELETE (DML)**: Deletes rows one by one. Can have a `WHERE` clause. Slower. Can be rolled back.
* **TRUNCATE (DDL)**: Resets the table. Reallocates pages. Very fast. Cannot be rolled back (in most DBs). No `WHERE` clause.
* **DROP (DDL)**: Deletes the table structure entirely.



##### DELETE
``` sql
DELETE FROM employees;
DELETE FROM employees WHERE department = 'HR';
```

Effect on data

❌ Data rows are removed
✅ Can delete some rows (with WHERE)
❌ Slow for large tables (row-by-row operation)
✅ Can be rolled back
✅ Fires DELETE triggers

Effect on table structure

✅ Table structure remains
✅ Indexes, constraints, schema all stay intact


##### TRUNCATE
``` sql
TRUNCATE TABLE employees;
```
Effect on data

❌ All data is removed
❌ Cannot use WHERE
✅ Very fast (metadata operation)
❌ Cannot be rolled back (in most DBs)
❌ Does not fire DELETE triggers

Effect on table structure

✅ Table structure remains
✅ Indexes and constraints remain
🔄 Resets identity / auto-increment counter

##### DROP
``` sql
DROP TABLE employees;
```

Effect on data
❌ All data is permanently removed
❌ Cannot be rolled back

Effect on table structure

❌ Table structure is removed
❌ Indexes, constraints, triggers — all gone




| Feature                 | DELETE | TRUNCATE     | DROP    |
| ----------------------- | ------ | ------------ | ------- |
| Removes data            | ✅ Yes  | ✅ Yes        | ✅ Yes   |
| Removes table structure | ❌ No   | ❌ No         | ✅ Yes   |
| WHERE clause            | ✅ Yes  | ❌ No         | ❌ No    |
| Transaction rollback    | ✅ Yes  | ❌ Usually No | ❌ No    |
| Speed                   | Slow   | Very Fast    | Instant |
| Triggers fired          | ✅ Yes  | ❌ No         | ❌ No    |
| Resets identity         | ❌ No   | ✅ Yes        | ❌ N/A   |





---

### **IV. Advanced: Window Functions (The "Senior" Differentiator)**

At 2.5 years, you **must** know these. They allow calculations across rows without collapsing the result set like `GROUP BY`.

**Syntax:** `Function() OVER (PARTITION BY col1 ORDER BY col2)`

#### **1. Ranking Functions**

* **ROW_NUMBER()**: Unique count (1, 2, 3, 4) even if values are tied.
* **RANK()**: Skips numbers on ties (1, 2, 2, 4).
* **DENSE_RANK()**: No skipping on ties (1, 2, 2, 3).
* *Interview Q:* "Find the 3rd highest salary."  Use `DENSE_RANK`.



#### **2. LAG / LEAD**

Access data from the previous or next row.

* *Use Case:* "Calculate Year-Over-Year growth."
```sql
SELECT
  year,
  revenue,
  LAG(revenue) OVER (ORDER BY year) as prev_year_revenue
FROM sales;

```



---

### **V. Performance & Indexing (The "Optimization" Questions)**

#### **1. Index Types**

* **Clustered Index**: Determines the **physical order** of data on the disk. Only **one** per table (usually the Primary Key).
* **Non-Clustered Index**: A separate structure (like a book index) that points to the data rows. You can have many.

#### **2. Composite Index & The "Leftmost Prefix" Rule**

If you create an index on `(A, B, C)`:

* It works for queries on: `A` alone, `(A, B)`, or `(A, B, C)`.
* It does **NOT** work for: `B` alone, `C` alone, or `(B, C)`.

#### **3. SARGable Queries (Search ARGumentable)**

Writing queries that can actually use the index.

* **Bad (Index ignored):** `WHERE YEAR(date_column) = 2023` (Function on column hides the data).
* **Good (Index used):** `WHERE date_column BETWEEN '2023-01-01' AND '2023-12-31'`.
* **Bad:** `WHERE name LIKE '%Smith'` (Leading wildcard prevents index seek).
* **Good:** `WHERE name LIKE 'Smith%'` (Trailing wildcard is fine).

---

### **VI. Common "Scenario" Questions**

1. **Find the duplicates in a table.**
```sql
SELECT email, COUNT(*)
FROM users
GROUP BY email
HAVING COUNT(*) > 1;

```


2. **Delete duplicates but keep the latest one.**
```sql
WITH CTE AS (
   SELECT *, ROW_NUMBER() OVER(PARTITION BY email ORDER BY created_at DESC) as rn
   FROM users
)
DELETE FROM CTE WHERE rn > 1;

```


3. **Find employees earning more than their managers.**
* Standard Self-Join question.


```sql
SELECT e.name
FROM Employee e
JOIN Employee m ON e.manager_id = m.id
WHERE e.salary > m.salary;

```



**Next Step:**
Would you like me to give you a **mock problem** specifically about **indexing or window functions** to test your understanding before the interview?