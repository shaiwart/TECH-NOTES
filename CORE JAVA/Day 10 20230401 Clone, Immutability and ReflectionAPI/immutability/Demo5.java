

// A subclass cannot actually modify the values of private properties in its parent, but it could behave as though it has
 
class base
{
	private int num;
	public base(int num)
	{
		this.num=num;
	}
	public int getNum()
	{
		return num;
	}
}
class sub extends base
{
	private int num;
	public sub(int num)
	{
		super(0);
		this.num=num;
	}
	public void setNum(int num)
	{
		this.num=num;
	}
	public int getNum()
	{
		return num;
	}
}

public class Demo5
{
	static void perform(base ref)
	{
		System.out.println(ref.getNum());
	}		
	static base getBase(int data)
	{
		/*base ob1=new base(data);
		return ob1;*/

		sub ob2=new sub(data);
		ob2.setNum(200);
		return ob2;
		
	}
	public static void main(String args[])
	{
		base ref1=getBase(100);
		// invoke perform twice for "b1" 
		perform(ref1); 
		perform(ref1); 
		
	}
}
/*
to avoid above problem,
Ensure that the class can't be extended. This prevents careless or malicious subclasses from compromising the immutable behavior of the class by behaving as if the object's state has changed.
*/

