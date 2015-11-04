package mycolletions;

public class MyCollections {
	
	
	/*
	 * Sort input linked list 'list' with merge sort algorithm
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void sort(MyLinkedList list) {
		ListNode head= list.getHead();
		ListNode newHead = mergeSort(head);
		
		
		list.setHead(newHead);
		ListNode newTail = newHead;
		while (newTail.next() != null) newTail = newTail.next();
		list.setTail(newTail);
		
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ListNode mergeSort(ListNode head) {
		if (head == null || head.next() == null) return head;
		
		
		ListNode slow = head;
		ListNode fast = head.next().next();
		
		while (fast != null && fast.next() != null) {
			slow = slow.next();
			fast = fast.next().next();
		}
		
		ListNode temp = mergeSort(slow.next());
		slow.setNext(null);
		return merge(mergeSort(head), temp);
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ListNode merge(ListNode a, ListNode b) {
		ListNode dummy = new ListNode(Integer.MIN_VALUE);
		ListNode c = dummy;
		
		while (a != null && b != null) {
			if (a.compareTo(b) == 1) {
				c.setNext(b);
				b = b.next();
			} else {
				c.setNext(a);
				a = a.next();
			}
			c = c.next();
		}
		
		if (a != null) {
			c.setNext(a);
		} else {
			c.setNext(b);
		}
		return dummy.next();
	}
	
	
	
	/**
	 * Swap values of two nodes in linked list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void swap(MyLinkedList list, int i, int j) {
		if (i < 0 || j < 0) return;
		if (i >= list.size() || j >= list.size()) return;
		
		
		Object o = list.get(i);
		list.set(i, list.get(j));
		list.set(j, o);
	}
	
	/**
	 * Swap values of two nodes in array list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void swap(MyArrayList list, int i, int j) {
		if (i < 0 || j < 0) return;
		if (i >= list.size() || j >= list.size()) return;
		
		
		Object o = list.get(i);
		list.set(i, list.get(j));
		list.set(j, o);
	}
	
	
	
	
	/**
	 * Copy all elements from list 'src' to the end of 'dest'
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copy(MyLinkedList dest, MyLinkedList src) {
		if (dest == null || src == null) return;
		dest.getTail().setNext(src.getHead());
		dest.setTail(src.getTail());
	}
	
	/**
	 * Copy all elements from array list 'src' to the end of 'dest'
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copy(MyArrayList dest, MyArrayList src) {
		if (dest == null || src == null || src.size() == 0) return;
		dest.addAll(src);
	}
	
	
	
	/**
	 * Reverse linked list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void reverse(MyLinkedList list) {
		ListNode newHead = rev(null, list.getHead());
		list.setHead(newHead);
		
		ListNode newTail = newHead;
		while (newTail.next() != null) newTail = newTail.next();
		list.setTail(newTail);
	}
	
	/**
	 * Reverse array list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void reverse(MyArrayList list) {
		if (list == null || list.size() < 2) return;
		
		int start = 0;
		int finish = list.size() - 1;
		
		while (start < finish) {
			swap(list, start, finish);
			start++;
			finish--;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ListNode rev(ListNode prev, ListNode cur) {
		if (cur.next() == null) {
			cur.setNext(prev);
			return cur;
		} else {
			ListNode newCur = cur.next();
			cur.setNext(prev);
			return rev(cur, newCur);
		}
	}
	
	/**
	 * Sort elements in MyArrayList with quicksort algorithm
	 * Important: list items must be integers! 
	 * */
	public static void sort(MyArrayList list) {
		Object[] array = list.getArray();
		quickSort(array, 0, list.size() - 1);
	}
	
	private static void quickSort(Object[] arr, int low, int high) {
		if (arr == null || arr.length == 0)
			return;
 
		if (low >= high)
			return;
 
		// pick the pivot
		int middle = low + (high - low) / 2;
		int pivot = (int) arr[middle];
 
		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while ((int)arr[i] < pivot) {
				i++;
			}
 
			while ((int)arr[j] > pivot) {
				j--;
			}
 
			if (i <= j) {
				int temp = (int)arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (low < j)
			quickSort(arr, low, j);
 
		if (high > i)
			quickSort(arr, i, high);
	}
	
	
	/**
	 * Return index of key in list with binary search algorithm
	 * or -(insertion point + 1) if there no such element
	 * 
	 * Important: list items must be integers! 
	 */
	public static int binarySearch(MyArrayList list, int key) {
		
		int low = 0;
		int high = list.size() - 1;
		
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if ((int)list.get(mid) > key) {
				high = mid - 1;
			} else if ((int)list.get(mid) < key) {
				low = mid + 1;
			} else {
				return mid;
			}
		}
		
		return - (low + 1);
	}
	
	
	
		
}
