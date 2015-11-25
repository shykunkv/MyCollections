package map;

import java.util.HashMap;

public class MyHashMap<K, V> implements MyMap<K, V> {

	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	private static final int MAXIMUM_CAPACITY = 1 << 30;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	
	private SimpleEntry[] table;
	private int size;
	private float loadFactor;
	private int threshold;
	
	public MyHashMap(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0 || loadFactor <= 0) 
				throw new IllegalArgumentException("Invadil capacity or load factor");
		if (initialCapacity > MAXIMUM_CAPACITY) 
			initialCapacity = MAXIMUM_CAPACITY;
		int capacity = 1;
		
		while (capacity < initialCapacity) {
			capacity <<= 1;
		}
		
		this.loadFactor = loadFactor;
		this.threshold = (int) (capacity * loadFactor);
		this.table = new SimpleEntry[capacity];
	}
	
	public MyHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}
	
	
	public MyHashMap() {
		this.loadFactor = DEFAULT_LOAD_FACTOR;
		this.threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
		this.table = new SimpleEntry[DEFAULT_INITIAL_CAPACITY];
	}
	
	static int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return  h ^ (h >>> 7) ^ (h >>> 4);
	}
	
	static int indexFor(int h, int length) {
		return h & (length - 1);
	}
	
	
	private void resize(int newCapacity) {
		SimpleEntry[] oldTable = table;
		int oldCapacity = oldTable.length;
		
		if (oldCapacity == MAXIMUM_CAPACITY) {
			threshold = Integer.MAX_VALUE;
			return;
		}
		
		SimpleEntry[] newTable = new SimpleEntry[newCapacity];
		transfer(newTable);
		table = newTable;
		threshold = (int) (newCapacity * loadFactor);
	}
	
	private void transfer(SimpleEntry[] newTable) {
		SimpleEntry[] src = table;
		int newCapacity = newTable.length;
		for (int i = 0; i < src.length; i++) {
			SimpleEntry<K, V> e = src[i];
			if (e != null) {
				src[i] = null;
				do {
					SimpleEntry<K, V> next = e.next;
					int j = indexFor(e.hash, newCapacity);
					e.next = newTable[j];
					newTable[j] = e;
					e = next;
				} while (e != null);
			}
		}
	}
	
	
	@Override
	public void clear() {
		SimpleEntry[] tab = table;
		for (int i = 0; i < tab.length; i++) {
			tab[i] = null;
		}
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	
	private SimpleEntry<K, V> getEntry(Object key) {
		int hash = (key == null) ? 0 : hash(key.hashCode());
		SimpleEntry<K, V> e = table[indexFor(hash, table.length)];
		
		while (e != null) {
			Object k;
			if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
				return e;
			e = e.next;
		}
		
		return null;
	}
	
	@Override
	public boolean containsValue(Object value) {
		if (value == null)
			return containsNullValue();
		
		SimpleEntry[] tab = table;
		for(int i = 0; i < table.length; i++) {
			for (SimpleEntry e = tab[i]; e != null; e = e.next) {
				if (value.equals(e.value))
					return true;
			}
		}
		return false;
	}

	
	private boolean containsNullValue() {
		SimpleEntry[] tab = table;
		for (int i = 0; i < tab.length; i++)
			for (SimpleEntry e = table[i]; e != null; e = e.next) 
				if (e.value == null)
					return true;
		
		return false;
	}
	
	@Override
	public V get(Object key) {
		if (key == null) {
			return getForNullKey();
		} else {
			int hash = hash(key.hashCode());
			SimpleEntry<K,V> e = table[indexFor(hash, table.length)];
			while (e != null) {
				Object k;
				if (e.hash == hash && ((k = e.key) == key || key.equals(k))) return e.value;
				e = e.next;
			}
		}
		return null;
	}
	
	private V getForNullKey() {
		SimpleEntry<K,V> e = table[0];
		while (e != null) {
			if (e.key == null)
				return e.value;
			e = e.next;
		}
		return null;
	}
	
	@Override
	public V put(K key, V value) {
		if (key == null) 
			return putForNullKey(value);
		
		int hash = hash(key.hashCode());
		int i = indexFor(hash, table.length);
		
		SimpleEntry<K,V> e = table[i];
		while (e != null) {
			Object k;
			if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
				V oldValue = e.value;
				e.value = value;
				return oldValue;
			}
			e = e.next;
		}
		
		
		addEntry(hash, key, value, i);
		return null;
	}

	private V putForNullKey(V value) {
		SimpleEntry<K, V> e = table[0];
		while (e != null) {
			if (e.key == null) {
				V oldValue = e.value;
				e.value = value;
				return oldValue;
			}
			e = e.next;
		}
		addEntry(0, null, value, 0);
		return null;
	}
	
	
	private void addEntry(int hash, K key, V value, int bucketIndex) {
		SimpleEntry<K, V> e = table[bucketIndex];
		table[bucketIndex] = new SimpleEntry<K, V>(hash, key, value, e);
		if (size++ >= threshold)
			resize(2 * table.length);
	}
	
	@Override
	public V remove(Object key) {
		SimpleEntry<K, V> e = removeEntryForKey(key);
		return (e == null ? null : e.value);
	}
	
	
	private SimpleEntry<K, V> removeEntryForKey(Object key) {
		int hash = (key == null) ? 0 : hash(key.hashCode());
		int i = indexFor(hash, table.length);
		
		SimpleEntry<K, V> prev = table[i];
		SimpleEntry<K, V> e = prev;
		
		while (e != null) {
			SimpleEntry<K, V> next = e.next;
			Object k;
			if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
				size--;
				if (prev == e)
					table[i] = next;
				else 
					prev.next = next;
				return e;
			}
			prev = e;
			e = next;
		}
		
		return e;
	}
	
	static class SimpleEntry<K, V> implements MyMap.MyEntry<K, V> {

		final K key;
		V value;
		SimpleEntry<K, V> next;
		final int hash;
		
		
		public SimpleEntry(int hash, K key,V value, SimpleEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
			this.hash = hash;
		}
		
		
		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}
		
	}

}
