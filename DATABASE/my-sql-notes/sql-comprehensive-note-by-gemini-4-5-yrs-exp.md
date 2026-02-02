Here are SQL notes tailored for a **Senior/Lead Developer (4-5 years experience)**.

At this level, the interview shifts from "How do I write this query?" to **"How does the database execute this, and why is it slow?"** You are expected to understand internals, locking strategies, and architectural trade-offs.

---

### **I. Database Internals & Architecture**

You must understand what happens *after* you hit enter.

#### **1. Query Execution Lifecycle**

1. **Parser:** Checks syntax and semantics (Do these columns exist?). Generates a Parse Tree.
2. **Optimizer (The Brain):**
* This is the most critical component. It creates multiple **Execution Plans**.
* It uses **Statistics** (row counts, data distribution histograms) to estimate the "Cost" (CPU + I/O).
* *Interview Key:* "Stale statistics" are often the cause of sudden performance drops.


3. **Executor:** Runs the chosen plan (e.g., retrieving pages from disk/buffer pool).

#### **2. Physical Storage**

* **Pages (Blocks):** The smallest unit of data (usually 8KB or 16KB). DBs read/write entire pages, not individual rows.
* **Buffer Pool:** The cache in RAM. Tuning this is often the #1 performance fix.
* **WAL (Write Ahead Log):** Changes are written to the log *before* the actual data file to ensure Durability (ACID).

---

### **II. Advanced Indexing & Tuning (The "Performance" Section)**

At 4-5 years, you don't just "add an index." You design them.

#### **1. Index Structures**

* **B-Tree (Balanced Tree):** The default. Great for ranges (`=`, `>`, `<`). O(log N).
* **Hash Index:** Key-Value lookups only (`=`). O(1). No ranges.
* **Bitmap Index:** Good for low-cardinality columns (Gender, Boolean) in Warehouses (OLAP), bad for frequent updates (OLTP) due to locking.

#### **2. Critical Indexing Concepts**

* **Index Scan vs. Index Seek:**
* **Seek:** Pinpoint specific rows (Fast).
* **Scan:** Reading the entire index leaf nodes (Slow, but faster than Table Scan).


* **Covering Index:**
* If your index contains **all** columns requested in the `SELECT` and `WHERE`, the DB never touches the actual table (Heap). It serves data directly from the index.
* *Optimization:* `SELECT id FROM Users WHERE email = '...'` is faster than `SELECT *` because `id` is likely in the index leaf.


* **Selectivity:** An index on `Gender` (50/50 split) is useless (the optimizer will ignore it and scan the table). High selectivity (unique values) is good.

#### **3. Join Algorithms (Why is my join slow?)**

* **Nested Loop Join:** Efficient for small datasets or when joined columns are indexed.
* **Hash Join:** Builds a hash table in memory for the smaller table. Fast for large, unsorted sets (requires memory).
* **Merge Join:** Very fast but requires both inputs to be **sorted** first.

---

### **III. Concurrency, Locking & Isolation Levels**

This is the biggest differentiator for senior roles. You must explain *Anomalies*.

#### **1. The Anomalies**

* **Dirty Read:** Reading uncommitted data from another transaction.
* **Non-Repeatable Read:** You read a row twice in one transaction, but get different values (someone updated it).
* **Phantom Read:** You run a range query twice, but get a different number of rows (someone inserted/deleted a row).

#### **2. Isolation Levels (Trade-off: Consistency vs. Throughput)**

| Isolation Level | Dirty Read? | Non-Repeatable? | Phantom? | Performance | Use Case |
| --- | --- | --- | --- | --- | --- |
| **Read Uncommitted** | Yes | Yes | Yes | Highest | Logging, Analytics (approx numbers). |
| **Read Committed** | No | Yes | Yes | High | Default for Postgres, SQL Server, Oracle. |
| **Repeatable Read** | No | No | Yes | Medium | Default for MySQL. Financial apps. |
| **Serializable** | No | No | No | Lowest | Strict consistency required. |

#### **3. Deadlocks**

* **Scenario:** Transaction A locks Row 1, wants Row 2. Transaction B locks Row 2, wants Row 1.
* **Solution:** Consistent locking order, shorter transactions, or `NOLOCK` hints (risky).

---

### **IV. Advanced SQL Patterns**

#### **1. Recursive CTEs (Hierarchical Data)**

Used for Org Charts, Folder Structures, or Graph traversal.

```sql
WITH RECURSIVE Hierarchy AS (
    -- Anchor Member (The Root)
    SELECT id, name, manager_id, 1 as level
    FROM employees WHERE manager_id IS NULL

    UNION ALL

    -- Recursive Member (The Children)
    SELECT e.id, e.name, e.manager_id, h.level + 1
    FROM employees e
    INNER JOIN Hierarchy h ON e.manager_id = h.id
)
SELECT * FROM Hierarchy;

```

#### **2. Advanced Window Functions**

* **`ROWS BETWEEN` (Running Totals / Moving Averages):**
```sql
-- 3-day moving average
AVG(sales) OVER (ORDER BY date ROWS BETWEEN 2 PRECEDING AND CURRENT ROW)

```


* **`NTILE(n)`**: Distributes rows into `n` buckets (e.g., Deciles/Quartiles).

#### **3. `EXISTS` vs `IN**`

* **`IN`**: Scans the list. Good for small static lists. Handles `NULL` poorly.
* **`EXISTS`**: "Semijoin". Stops scanning as soon as a match is found. Generally faster for large subqueries.

---

### **V. System Design / Scaling (The "Architect" View)**

#### **1. Partitioning**

Splitting a large table into smaller physical pieces.

* **Horizontal Partitioning (Sharding):** Split by row (e.g., `Users_US`, `Users_EU`).
* **Vertical Partitioning:** Split by column (e.g., Move `Blob` columns to a separate table to keep the main table "lean" for caching).

#### **2. Normalization vs. Denormalization**

* **Normalization (3NF):** Reduces redundancy, good for Write-Heavy (OLTP).
* **Denormalization:** Adds redundancy (pre-joining tables), good for Read-Heavy (OLAP/Reporting) to avoid expensive joins.

#### **3. OLTP vs OLAP**

* **OLTP (Online Transaction Processing):** Short, fast transactions. Row-oriented storage. (e.g., PostgreSQL, MySQL).
* **OLAP (Online Analytical Processing):** Complex aggregations. Column-oriented storage. (e.g., Snowflake, Redshift).

---

### **Summary Checklist for Interview**

1. **Explain Plan:** Can you read one? (Look for "Table Scan" vs "Index Seek").
2. **Indexing:** Do you know the "Leftmost Prefix" rule?
3. **Locks:** Do you know the difference between Optimistic (Versioning) and Pessimistic (Locking) concurrency?
4. **Schema:** Can you design a schema for "Uber" or "Instagram"? (Think many-to-many relationships).

Would you like a simplified explanation of **"Optimistic vs Pessimistic Locking"**? It is a very common follow-up question.