public class AssertDemo
{
	public static void main(String args[])
	{
		System.out.println("Before checking num");
		int num=10;
		assert(num<=10);
		System.out.println("After checking num");

		assert(num>20):"num is not gt 20";
		System.out.println("After checking num");
	}
}

/*
assert boolean expression
	- here if boolean expression is true , remaining code will get executed.
	- if expression is false, then AssertionError will come.

it is recommended not to handle AssertionError (though we can handle it)
because when it comes we would like to stop the execution.
*/