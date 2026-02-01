package core1;

class Outer
{
	private int num1=10;
	int num2=20;
	protected int num3=30;
	public int num4=40;
	
	static int num5=50;
	
	void outerDisp()
	{
		System.out.println(num1+"\t"+num2+"\t"+num3+"\t"+num4);
	}

	static class inner
	{
		void innerDisp()
		{
			System.out.println(num5);
		}
	}
}

public class Demo
{
	public static void main(String args[])
	{
		Outer o1=new Outer();
		Outer.inner i=new Outer.inner();
		
		o1.outerDisp();
		i.innerDisp();
	}
}


















			




						































	









	









	









	
	
		


	

	
	
	


































