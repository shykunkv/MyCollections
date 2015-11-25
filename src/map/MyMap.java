package map;

public interface MyMap<K, V> {
	void clear();
	int size();
	boolean isEmpty();
	boolean containsKey(Object k);
	boolean containsValue(Object value);
	V get(Object k);
	V put(K key, V value);
	V remove(Object key);
	
	interface MyEntry<K, V> {
		K getKey();
		V getValue();
		V setValue(V value);
		boolean equals(Object o);
		int hashCode();
	}
	
}
