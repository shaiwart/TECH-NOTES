


class Student
{
	// member variables or instance variables

	private int rollno,age;
	private String name;

	// member functions

	public void setRollno(int a)
	{
		//if( a is invalid, do not accept it )
		rollno=a;
	}
	public int getRollno()
	{
		return rollno;
	}
	public void setAge(int b)
	{
		age=b;
	}
	public int getAge()
	{
		return age;
	}
	public void setName(String c)
	{
		name=c;
	}
	public String getName()
	{
		return name;
	}

}

public class StudentDemo
{
	public static void main(String args[])
	{
		Student s1=new Student();  // object or instance creation
		//s1.rollno=1; // not possible , data security
		s1.setRollno(1);    
		s1.setName("Abc");
		s1.setAge(21);

		System.out.println("My First student is\t"+s1.getRollno() +"\t"+s1.getName()+"\t"+ s1.getAge());

		Student s2=new Student();  // object or instance creation
		
		s2.setRollno(2);    
		s2.setName("Amar");
		s2.setAge(23);
		

		System.out.println("My Second student is\t"+s2.getRollno() +"\t"+s2.getName()+"\t"+ s2.getAge());
		
	}
}





