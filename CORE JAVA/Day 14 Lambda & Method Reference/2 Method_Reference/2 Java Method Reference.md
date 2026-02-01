## Java Method Reference `::`
- Method reference in Java 8 is the ability to use a method as an argument for a matching functional interface.
- `::` (double colon) is the operator used for method reference in Java.
- An interface with only one abstract method is called a functional interface. For example, `Comparable, Runnable, AutoCloseable` are some functional interfaces in Java.
- ⭐️ You use lambda expressions to create anonymous methods. Sometimes, however, a lambda expression does nothing but call an existing method. In those cases, it's often clearer to refer to the existing method by name.
- Method references enable you to do this; they are compact, easy-to-read lambda expressions for methods that already have a name.

Syntax: `<classname or instancename>::<methodname>`

The method in the functional interface and the passing method reference should match for the argument and returntype. Method reference can be done for both static and instance methods.

## When to use method reference :
When a Lambda expression is invoking already defined method and The method in the functional interface and the passing method reference match for the argument and returntype, you can replace it with reference to that method.

Let us consider the following example to understand this. In AutoCloseable interface we have the method of signature as,
`void close throws Exception()`
In our example below we have the method with signature,
`void close()`

- So these two methods are matching in terms of argument and returntype (type match).
- Therefore we can use the method we have written for the functional interface AutoCloseable as a method reference.

##### Method Reference Example
``` java
public class MethodReferenceExample {
    ​void close() {
        ​System.out.println("Close.");
    }

    ​public static void main(String[] args) throws Exception {
        ​MethodReferenceExample referenceObj = new MethodReferenceExample();
        ​try (AutoCloseable ac = referenceObj::close) {
                ​System.out.println("Implementation class for AutoCloseable is\t" + ac.getClass().getName());
        }
    }
}
```