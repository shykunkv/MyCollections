package mycolletions;

public class MyQueue<E> {
	
	private MyLinkedList<E> q;
	
	
	MyQueue() {
		q = new MyLinkedList();
	}
	
	public boolean isEmpty() {
		return q.size() == 0;
	}
	
	public void offer(E value) {
		q.addLast(value);
	}
	
	public E peek() {
		return q.getFirst();
	}
	
	public E poll() {
		return q.removeFirst();
	}
}
