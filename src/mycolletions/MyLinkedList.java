package mycolletions;

class ListNode<E> implements Comparable<E> {
     
    private E value;
    private ListNode<E> next;
     
    ListNode() {}
    
    ListNode(E value) {
    	this.value = value;
    }
    
    public E getValue() {
        return value;
    }
    
    public void setValue(E value) {
        this.value = value;
    }
    
    public boolean hasNext() {
    	if (this.next == null) return false;
    	return true;
    }
    
    public ListNode<E> next() {
        return next;
    }
    
    public void setNext(ListNode<E> next) {
        this.next = next;
    }
     
    @Override
    public int compareTo(E arg) {
        if(arg == this.value){
            return 0;
        } else {
            return 1;
        }
    }
}

public class MyLinkedList<E> {
 
    private ListNode<E> head;
    private ListNode<E> tail;
    private int size;
    
    MyLinkedList() {
    	this.size = 0;
    }
    
    
    public ListNode<E> getHead() {
    	return head;
    }
    
    public void setHead(ListNode<E> head) {
    	this.head = head;
    }
    
    public ListNode<E> getTail() {
    	return tail;
    }
    
    public void setTail(ListNode<E> tail) {
    	this.tail = tail;
    }
    
    
    public void add(E value){
         
        ListNode<E> newNode = new ListNode<E>(value);
        
        // check for empty list
        if(size == 0){
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }
     
    public void add(int index, E value){
        if (index < 0) return; 
    	if (index > size) {
    		this.addLast(value);
    		return;
    	}
    	
    	if (index == 0) {
        	this.addFirst(value);
        	return;
        }
        ListNode<E> temp = head;
        ListNode<E> refNode = null;

        
        
        while (index > 1) {
            temp = temp.next();
            index--;
        }
        
        ListNode<E> newNode = new ListNode<E>(value);
        newNode.setNext(temp.next());
        if(temp == tail) tail = newNode;
        temp.setNext(newNode);
        size++;

    }

    public void addFirst(E value) {
    	ListNode<E> newNode = new ListNode<E>(value);
    	
    	if (size == 0) {
    		head = newNode;
    		tail = newNode;
    	} else {
    		newNode.setNext(head);
    		head = newNode;
    	}
    	size++;
    }
    
    // ??? ����� ��� �����, ���� ���� ������� add(E value)
    public void addLast(E value) {
    	this.add(value);
    }
    
    
    public E getFirst() {
    	if (size > 0) return head.getValue();
    	else return null;
    }
    
    
    public E getLast() {
    	if (size > 0) return tail.getValue();
    	else return null;
    }
 
    public E get(int index) {
    	
    	if (index < 0 || index > size) return null;
    	ListNode<E> temp = head;
    	
    	while (index > 0) {
    		temp = temp.next();
    		index--;
    	}
    	
    	return temp.getValue();
    }
    
    public int size() {
    	return this.size;
    }
    
    public E removeFirst() {
    	if (size == 0) return null;
    	if (size == 1) {
    		E value = head.getValue();
    		head = null;
    		tail = null;
    		size--;
    		return value;
    	} else {
    		E value = head.getValue();
        	head = head.next();
        	size--;
        	return value;
        	
        }
    }
    
    public E removeLast() {
    	if (size == 0) return null;
    	if (size == 1) {
    		E value = head.getValue();
    		head = null;
    		tail = null;
    		size--;
    		return value;
    	} else {
    		ListNode<E> temp = head;
    		while (temp.next() != tail) temp = temp.next();
    		E value = tail.getValue();
    		temp.setNext(null);
    		tail = temp;
    		size--;
    		return value;
    	}
    }
    
    public E remove(int index) {
    	if (index < 0 || index > size) return null;
    	
    	
    	ListNode<E> temp = head;
    	
    	if (index == 0) return removeFirst();
    	if (index == size) return removeLast();
    	
    	while (index > 1) {
    		temp = temp.next();
    		index--;
    	}
    	
    	E value = temp.next().getValue();
    	temp.setNext(temp.next().next());
    	size--;
    	return value;
    }
    
    public void traverse(){
         
        ListNode<E> temp = head;
        System.out.print("[");
        while(temp != null) {
        	if (temp.next() != null) System.out.print(temp.getValue() + ", ");
        	else  System.out.print(temp.getValue());
            temp = temp.next();
        }
        System.out.print("]");
        System.out.println();
    }
     
    public int indexOf(E value) {
    	if (size == 0) return -1;
    	
    	ListNode<E> temp = head;
    	int index = 0;
    	while (temp != null) {
    		if (temp.getValue().equals(value)) return index;
    		temp = temp.next();
    		index++;
    	}
    	return -1;
    }

    public void set(int index, E value) {
    	if (size == 0 || index < 0 || index > size) return;
    	
    	
    	ListNode<E> temp = head;
    	
    	while (index > 0) {
    		temp = temp.next();
    		index--;
    	}
    	
    	temp.setValue(value);
    }
    
    public void rotateRight(int k) {
        if (head == null || head.next() == null || k == 0) return;
        
        ListNode temp = head;
        if (k >= size) k = k % size;
        if (k == 0) return;
        
        k = size - k - 1;
        
        while (k > 0) {
            if (temp.next() == null) temp = head;
            else temp = temp.next();
            k--;
        }
        
        ListNode newHead = temp.next();
        temp.setNext(null);
        
        temp = newHead;
        while (temp.next() != null) temp = temp.next();
        
        temp.setNext(head);
        
        head = newHead;
    }
    
}
 

