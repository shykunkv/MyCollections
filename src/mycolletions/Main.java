package mycolletions;

import java.util.Collections;
import mycolletions.MyLinkedList;


public class Main {

	public static void main(String[] args) {
		MyArrayList list1 = new MyArrayList();
		
		list1.add(1);
		list1.add(2);
		list1.add(3);
		list1.add(4);
		
		
		System.out.println(MyCollections.binarySearch(list1, 6));
		
	}

}
