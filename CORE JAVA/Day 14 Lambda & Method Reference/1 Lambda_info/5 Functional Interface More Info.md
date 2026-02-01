
### Info
- An interface with exactly one abstract method is called Functional Interface.
- `@FunctionalInterface` annotation is added so that we can mark an interface as functional interface. It is not mandatory to use it, but it’s best practice to use it with functional interfaces to avoid addition of extra methods accidentally.
- If the interface is annotated with `@FunctionalInterface` annotation and we try to have more than one abstract method, it throws compiler error.

##### Advantages
- The major benefit of functional interface is that we can use lambda expressions to instantiate them and avoid using bulky anonymous class implementation.


- Java 8 Collections API has rewritten and new Stream API is provided that uses a lot of functional interfaces.
- ⭐️ Java 8 has defined a lot of functional interfaces in java.util.function package, some of the useful ones are `Consumer, Supplier, Function and Predicate`.
- java.lang.Runnable is a great example of functional interface with single abstract method run().
