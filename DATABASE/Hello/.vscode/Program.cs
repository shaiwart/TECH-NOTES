using system;
namespace ConsoleApp2find
{
    internal class Program
    {
        static void Main(string[] args)
        {            Console.WriteLine("Hello, World!");
            Mylist mylist=new Mylist();
           Employee e1= mylist.MyFind((arr) => arr.Salary == 6000);
        }
    }
}
    