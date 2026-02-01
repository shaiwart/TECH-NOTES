import java.util.*;
class base {
}
class sub extends base {
}
class sub1 extends base {
}

public class ArrayStoreExcepDemo {
	public static void main(String args[]) {
		// Arrays are Covariant: sub[] IS-A base[]
		// This allows assignment: base[] arr = new sub[3];
		base arr[]=new sub[3];

		arr[0]=new sub(); // OK: sub is compatible with the actual array type (sub[])
		arr[1]=new sub(); // OK

		// RUNTIME ERROR: ArrayStoreException
		// Although 'arr' is of type base[], the actual object on heap is sub[].
		// sub1 is a 'base', so compiler allows this line.
		// But sub1 is NOT a 'sub', so the actual array cannot hold it.
		arr[2]=new sub1();

		for(int i=0;i<arr.length;i++) {
			System.out.println(arr[i]);
		}
	}
}

/*
 * Explanation:
 * 1. Arrays in Java are Reified: They know their component type at runtime.
 * 2. Arrays are Covariant: If Sub extends Base, then Sub[] extends Base[].
 * 3. Limitations: Because of covariance, we can assign a Sub array to a Base array reference.
 *    However, if we try to store an incompatible type (like another subclass of Base)
 *    into that array, the JVM throws an ArrayStoreException at runtime.
 *
 *
 * Generics solve this by being Invariant (List<Sub> is NOT a List<Base>), preventing this error at compile time.
 *
 * --- Definitions ---
 * COVARIANT:
 * If 'Sub' is a subtype of 'Base', then 'Sub[]' IS A subtype of 'Base[]'.
 * You CAN assign a Sub array to a Base array reference.
 * Java Arrays are Covariant.
 *
 * INVARIANT:
 * If 'Sub' is a subtype of 'Base', 'List<Sub>' is NOT a subtype of 'List<Base>'.
 * They are completely different types. You CANNOT assign List<Sub> to List<Base>.
 * Java Generics are Invariant.
 *
 * List<Number> list = new ArrayList<Integer>(); // ❌ compile-time error
 * List<Integer> list = new ArrayList<Integer>(); // ✅
 */
