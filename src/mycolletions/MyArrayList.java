package mycolletions;

import java.util.Arrays;

public class MyArrayList {
	
	private Object[] array;
	private int size;
	
	
	/*
	 * Create new dynamic array with default size 
	 * */
	public MyArrayList() {
		this.array = new Object[10];
		this.size = 0;
	}
	
	public Object[] getArray() {
		return array;
	}
	
	
	/*
	 * Create new dynamic array with size = capacity
	 * */
	public MyArrayList(int capacity) {
		this.array = new Object[capacity];
		this.size = 0;
	}
	
	
	/*
	 * Add new object to the end of array
	 * */
	public void add(Object obj){
        if (array.length - size <= 1){
        	ensureCapacity(array.length + 1);
        }
        array[size++] = obj;
    }
	
	
	/*
	 * Return object from array from position 'index' or null
	 * */
	public Object get(int index) {
		if (index >= size) return null;
		else return array[index];
	}
	
	
	/*
	 * Remove object from 'position' in array
	 * */
	public Object remove(int index) {
		if (index >= size) return null;
		
		Object value = array[index];
		int move = size - index - 1;
		if (move > 0) 
			System.arraycopy(array, index+1, array, index, move);
		size--;
		return value;
	}
	
	
	/*
	 * Set new value 'value' to object in 'index' position
	 * */
	public void set(int index, Object value) {
		if (index >= size) return;
		array[index] = value;
	}
	
	
	/*
	 * Return size of array
	 * */
	public int size() {
		return size;
	}
	
	/*
	 * Add new object to the array on 'index' position
	 * */
	public void add(int index, Object obj){
        if (array.length - size <= 1){
        	ensureCapacity(array.length + 1);
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = obj;
        size++;
    }
	
	
	
	/*
	 * Add all objects in array 'c' to the end of array
	 * */
	public void addAll(Object[] c) {
		ensureCapacity(size + c.length);
		System.arraycopy(c, 0, array, size, c.length);
		size += c.length;
	}
	
	
	
	public void addAll(MyArrayList list) {
		ensureCapacity(size + list.size());
		for (int i = 0; i < list.size(); i++) {
			Object t = list.get(i);
			this.add(t);
		}
	}
	
	
	
	/*
	 * Add all objects in array 'c' starting from position 'index'
	 * */
	public void addAll(int index, Object[] c) {
		ensureCapacity(size + c.length);
		
		int moved = size - index; 
		if (moved > 0) {
			System.arraycopy(array, index, array, index + c.length, moved);
		}
		System.arraycopy(c, 0, array, index, c.length);
		size += c.length;
	}
	
	public void addAll(int index, MyArrayList list) {
		ensureCapacity(size + list.size());
		int k = index;
		for (int i = size + list.size() + 1; i > index + list.size(); i--) {
			array[i] = array[i-1];
		}
		for (int i = 0; i < list.size(); i++) {
			Object t = list.get(i);
			this.add(k++ ,t);
		}
	}
	
	
	/*
	 * Increase capacity of array (if necessary)
	 * */
	public void ensureCapacity(int minCapacity) {
		int oldCapacity = array.length;
		if (minCapacity > oldCapacity) {
			Object[] oldArray = array;
			int newCapacity = (oldCapacity * 3) / 2 + 1;
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			array = Arrays.copyOf(oldArray, newCapacity);
		}
	}
	
	
	/*
	 * Print array to console
	 * */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < size; i++) {
			if (i < size -1 ) sb.append(array[i] + ", ");
			else sb.append(array[i]);
		}
		sb.append("]");
		return sb.toString();
	}
}
