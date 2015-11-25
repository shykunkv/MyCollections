package mycolletions;

import java.util.Collections;

import mycolletions.MyLinkedList;
import map.MyHashMap;
import map.MyMap;
import map.MyTreeMap;

public class Main {

	public static void main(String[] args) {
		MyMap<Integer, String> map = new MyTreeMap();
		
		map.put(0, "hello");
		map.put(1, "aaa");
		map.put(1, "world");
		
		System.out.println(map.size());
	}

}
