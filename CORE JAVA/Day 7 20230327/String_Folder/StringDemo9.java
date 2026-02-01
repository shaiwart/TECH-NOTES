


/* When the intern method is invoked, if the pool already contains a string equal to this String object as determined by the equals(Object) method, then the string from the pool is returned. Otherwise, this String object is added to the pool and a reference to this String object is returned. 
 
*/

public class StringDemo9
{
	public static void main(String args[])
	{
		String s2=new String("hello");
		
		String s1=s2.intern();
		if(s1 == s2)
		{
			System.out.println("s1 and s2 are ==");
		}
		else
		{
			System.out.println("s1 and s2 are not ==");
		}
		
	}
}