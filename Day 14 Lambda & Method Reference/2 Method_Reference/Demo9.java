// disp of Sample has variable no. arguments


interface Emp
{
	void mymethod(int ... k);
}
class Sample
{
	void disp(int  ... k)
	{
		for(int temp:k)
		{
			System.out.println("in Sample disp\t"+temp);
		}
		
	}
}
public class Demo9
{
	public static void main(String args[])
	{
		
		Sample s1=new Sample();
		// using lambda
		Emp ref1=(int ... n)->{ s1.disp(n);};
System.out.println("Implementation classname is\t"+ref1.getClass().getName());
		ref1.mymethod(100,500);
		// using method reference
		Emp ref2=s1::disp;
System.out.println("Implementation classname is\t"+ref2.getClass().getName());
		ref2.mymethod(200,400);
		
	}
}
		
		
		
		



