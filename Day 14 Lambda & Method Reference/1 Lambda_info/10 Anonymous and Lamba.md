``` java
interface EmployeeInterface {
    void disp();
}
public class AnonymousDemo {
    static EmployeeInterface getEmp() {
        return new EmployeeInterface() {
            public void disp() {
                System.out.println("in disp");
            }
        };
    }
    /*static EmployeeInterface getEmp()
    {
            EmployeeInterface e=new EmployeeInterface()
            {
                    public void disp()
                    {
                            System.out.println("in disp diff way");
                    }
            };
            return e;
    }*/
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

