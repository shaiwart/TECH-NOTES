package package1;

/*
with class lock
*/
import java.util.concurrent.*;

class MyApp1 implements Runnable {
	synchronized static void disp() {
		for (int i = 0; i < 5; i++) {
			System.out.println("Hello " + i + "-" + Thread.currentThread());
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
		}
	}

	public void run() {
		disp();
	}
}

public class ExecutorDemo3 {
	public static void main(String args[]) {
		// MyApp1 m=new MyApp1();
		ExecutorService exec = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 3; i++) {
			// exec.execute(m);
			exec.execute(new MyApp1());
		}
		exec.shutdown();
	}
}