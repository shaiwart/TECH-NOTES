

## The 3 simple rules that remove confusion

### Rule 1: Intermediate operations don’t run

These are **lazy**:

* `filter`
* `map`
* `sorted`
* `distinct`

They only build the pipeline.

### Rule 2: Terminal operation runs the stream

These **trigger execution**:

* `collect`
* `forEach`
* `findFirst / findAny`
* `count`
* `reduce`

### Rule 3: Each stream can be used only once

After this runs:

```java
stream.findFirst();
```

You cannot reuse `stream` again — you must create a new stream from `names`.



### 🔍 Why streams work this way (simple mental model)
Think of a stream like a pipe of water 🚿:
	- Water flows once
	- After it finishes → pipe is empty
	- You cannot replay the same water

A collection is like a bucket 🪣:
	- You can take water again and again