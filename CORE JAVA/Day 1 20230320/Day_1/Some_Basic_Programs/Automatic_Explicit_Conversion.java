public class Automatic_Explicit_Conversion
{
	public static void main(String[] args)
	{
		// integral types
		byte a=10;
		
		int c=30;
		
		c=a; // automatic conversion
		System.out.println(c);
		
		// a=c; // not possible,explicit cast is needed
		a=(byte)c;  // explicit cast
		System.out.println(a);
		
		double d=4.8; // automatic cast
		System.out.println(d);
		// float d=3.5;  // not possible bec, 3.5 is by default double
		float e=3.5f;  // explicit cast
		float f=(float)3.5;
		System.out.println(e+"\t"+f);
	}
}
