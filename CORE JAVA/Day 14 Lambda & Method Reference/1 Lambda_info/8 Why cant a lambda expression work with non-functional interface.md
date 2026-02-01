### Why lambdas don’t work with non-functional interfaces
A non-functional interface has more than one abstract method:

``` java
interface BadInterface {
    void a();
    void b();
}
```


Now try:
``` java
BadInterface x = () -> System.out.println("Hello");
```

### 🚫 Problem:
Which method is this lambda implementing — a() or b()?

There’s no way for the compiler to know.
That ambiguity is why Java disallows lambdas for non-functional interfaces.