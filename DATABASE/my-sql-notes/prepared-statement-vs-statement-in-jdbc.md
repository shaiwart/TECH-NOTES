In JDBC, a **`PreparedStatement`** is often faster than a regular **`Statement`** mainly because of **how the database handles parsing, compilation, and execution**.

Here’s the breakdown 👇

---

## 1. Precompilation and reuse (biggest reason)

### `Statement`

```java
Statement stmt = conn.createStatement();
stmt.executeQuery("SELECT * FROM users WHERE id = 5");
stmt.executeQuery("SELECT * FROM users WHERE id = 10");
```

* Each SQL string is treated as a **new query**
* The database must **parse → validate → optimize → compile** every time
* Even if only values change, the DB repeats the work

---

### `PreparedStatement`

```java
PreparedStatement ps =
    conn.prepareStatement("SELECT * FROM users WHERE id = ?");
ps.setInt(1, 5);
ps.executeQuery();

ps.setInt(1, 10);
ps.executeQuery();
```

* SQL is **parsed and compiled once**
* Only the **parameter values change**
* The database reuses the **execution plan**

✅ Less CPU work on the database
✅ Faster repeated execution

---

## 2. Better execution plan caching

Databases cache execution plans internally.

* `Statement` → different SQL text each time → cache miss
* `PreparedStatement` → same SQL text → cache hit

This matters **a lot** in:

* Loops
* Batch inserts/updates
* High-traffic applications

---

## 3. Network and protocol efficiency

With prepared statements:

* The SQL is sent once
* Only parameter values are sent afterward

This reduces:

* Network traffic
* SQL parsing overhead on the server

---

## 4. Faster batch operations

```java
PreparedStatement ps =
    conn.prepareStatement("INSERT INTO orders VALUES (?, ?)");

for (...) {
    ps.setInt(1, id);
    ps.setBigDecimal(2, price);
    ps.addBatch();
}
ps.executeBatch();
```

Batching with `PreparedStatement` is **significantly faster** than batching with `Statement`.

---

## 5. Bonus: security (not speed, but important)

While not a performance reason, it’s worth noting:

* `PreparedStatement` **prevents SQL injection**
* `Statement` does not

---

## When is `PreparedStatement` NOT faster?

* If the query is executed **only once**
* Very simple queries where parsing cost is negligible

Even then, the performance difference is usually small—but `PreparedStatement` is still the best practice.

---

## TL;DR

**`PreparedStatement` is faster because:**

* SQL is parsed and compiled once
* Execution plans are reused
* Less DB CPU and network overhead
* Much faster for repeated and batch operations

If you want, I can also explain **what exactly happens inside the database engine** step by step, or compare it with **stored procedures**.
