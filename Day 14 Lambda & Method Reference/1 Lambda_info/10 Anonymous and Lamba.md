## Anonymous inner Class

``` java
interface EmployeeInterface {
    void disp();
}
public class AnonymousDemo {
    static EmployeeInterface getEmp() {
        // Here notice, that we are not giving name to the class.
        // So, Anonymous class
        EmployeeInterface e = new EmployeeInterface() {
            public void disp() {
                System.out.println("in disp diff way");
            }
        };
        return e;
    }

    // We can write this way also
    static EmployeeInterface getEmp() {
        return new EmployeeInterface() {
            public void disp() {
                System.out.println("in disp");
            }
        };
    }
    public static void main(String args[]) {
        EmployeeInterface e1 = AnonymousDemo.getEmp();
        e1.disp();
    }
}
```


## Lambda version

``` java
interface EmployeeInterface {
    void disp();
}

public class AnonymousDemo {

    static EmployeeInterface getEmp() {
        return () -> System.out.println("in disp");
    }

    public static void main(String args[]) {
        EmployeeInterface e1 = AnonymousDemo.getEmp();
        e1.disp();
    }
}
```



## Same thing using built-in functional interface (recommended)

#### Predicate version – Anonymous Inner Class

Instead of creating your own interface, use `Predicate<Employee>`.

```java
import java.util.function.Predicate;
public class PredicateDemo {

    static Predicate<String> getEmpPredicate() {
        return new Predicate<String>() {
            @Override
            public boolean test(String name) {
                return name != null && !name.isEmpty();
            }
        };
    }

    public static void main(String[] args) {
        Predicate<String> p = getEmpPredicate();
        System.out.println(p.test("John"));   // true
        System.out.println(p.test(""));       // false
    }
}
```

#### Predicate version – Lambda (clean & modern ✨)
```java
import java.util.function.Predicate;
public class PredicateDemo {

    static Predicate<String> getEmpPredicate() {
        return name -> name != null && !name.isEmpty();
    }

    public static void main(String[] args) {
        Predicate<String> p = getEmpPredicate();
        System.out.println(p.test("John"));   // true
        System.out.println(p.test(""));       // false
    }
}
```