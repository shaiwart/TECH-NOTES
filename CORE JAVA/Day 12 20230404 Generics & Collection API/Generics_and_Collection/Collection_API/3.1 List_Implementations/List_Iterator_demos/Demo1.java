// it's possible to have bidirectional traversal in case of 
// ListIterator

package core1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Demo1 {

	public static void main(String[] args) {
		List<Integer> mylist=new ArrayList<>();
		mylist.add(10);
		mylist.add(20);
		mylist.add(30);
		mylist.add(40);
		System.out.println("Using Iterator");
		Iterator itr=mylist.iterator();
		while(itr.hasNext())
		{
			System.out.println(itr.next());
		}
		System.out.println("Using ListIterator from the beginning");
		ListIterator ltr=mylist.listIterator();
		while(ltr.hasNext())
		{
			System.out.println(ltr.next());
		}
		System.out.println("Using ListIterator from the end");
		
		while(ltr.hasPrevious())
		{
			System.out.println(ltr.previous());
		}
	}

}
