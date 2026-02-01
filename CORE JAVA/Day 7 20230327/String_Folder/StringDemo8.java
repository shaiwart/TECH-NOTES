


/* When the intern method is invoked, if the pool already contains a string equal to this String object as determined by the equals(Object) method, then the string from the pool is returned
 
*/

public class StringDemo8
{
	public static void main(String args[])
	{
		String s1="hello";
		String s2=new String("hello");
		
		2000	  2000
		if(s1 == s2.intern())
		{
			System.out.println("s1 and s2 are ==");
		}
		else
		{
			System.out.println("s1 and s2 are not ==");
		}
		
	}
}