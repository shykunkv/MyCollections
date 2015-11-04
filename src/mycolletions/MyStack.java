package mycolletions;

public class MyStack<E> {
	
	private MyLinkedList<E> st;
	
	
	MyStack() {
		st = new MyLinkedList();
	}
	
	public boolean isEmpty() {
		return st.size() == 0; 
	}
	
	public void push(E value) {
		st.addFirst(value);
	}
	
	public E pop() {
		return st.removeFirst();
	}
}
