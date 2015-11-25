package map;

import java.util.Comparator;
import java.util.Map;

public class MyTreeMap<K, V> implements MyMap<K, V> {

	
	private final Comparator<? super K> comparator;
	private MyEntry<K, V> root = null;
	private int size = 0;
	
	
	public MyTreeMap() {
		comparator = null;
	}
	
	
	public MyTreeMap(Comparator<? super K> comparator) {
		this.comparator = comparator;
	}
	
	@Override
	public void clear() {
		size = 0;
		root = null;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public boolean containsKey(Object k) {
		return getEntry(k) != null;
	}
	
	private MyEntry<K,V> getEntry(Object key) {
         if (comparator != null)
             return getEntryUsingComparator(key);
         if (key == null)
             throw new NullPointerException();
         
         Comparable<? super K> k = (Comparable<? super K>) key;
         MyEntry<K,V> p = root;
         while (p != null) {
             int cmp = k.compareTo(p.key);
             if (cmp < 0)
                 p = p.left;
             else if (cmp > 0)
                 p = p.right;
             else
                 return p;
         }
         return null;
     }
	
	
	private MyEntry<K,V> getEntryUsingComparator(Object key) {
         K k = (K) key;
         Comparator<? super K> cpr = comparator;
         if (cpr != null) {
             MyEntry<K,V> p = root;
             while (p != null) {
                 int cmp = cpr.compare(k, p.key);
                 if (cmp < 0)
                     p = p.left;
                 else if (cmp > 0)
                     p = p.right;
                 else
                     return p;
             }
         }
         return null;
     }
	
	
	@Override
	public boolean containsValue(Object value) {
		for (MyEntry<K, V> e = getFirstEntry(); e != null; e = successor(e))
			if (valEquals(value, e.value))
				return true;
		return false;
	}
	
	
	private boolean valEquals(Object a, Object b) {
		return (a == null ? b == null : a.equals(b));
	}
	
	private MyEntry<K, V> getFirstEntry() {
		MyEntry<K, V> e = root;
		if (e != null) {
			while (e.left != null) 
				e = e.left;	
		}
		return e;
	}
	
	
	private static <K, V> MyEntry<K, V> successor(MyEntry<K, V> t) {
		if (t == null)
			return null;
		else if (t.right != null) {
			MyEntry<K, V> e = t.right;
			while (e.left != null)
				e = e.left;
			return e;
		} else {
			MyEntry<K, V> par = t.parent;
			MyEntry<K, V> ch = t;
			
			while (par != null && ch == par.right) {
				ch = par;
				par = par.parent;
			}
			return par;
		}
		
		
	}
	
	@Override
	public V get(Object k) {
		MyEntry<K, V> p = getEntry(k);
		return (p == null ? null : p.value);
	}

	@Override
	public V put(K key, V value) {
		MyEntry<K,V> t = root;
		if (t == null) {
			root = new MyEntry<K, V>(key, value, null);
	        size = 1;
	        return null;
		}
		         
		int cmp;     
		MyEntry<K,V> parent;
		// split comparator and comparable paths
		Comparator<? super K> cpr = comparator;
		
		if (cpr != null) {
			do {
				parent = t;
		        cmp = cpr.compare(key, t.key);
		        if (cmp < 0)
		            t = t.left;
		        else if (cmp > 0)
		            t = t.right;
		        else
		            return t.setValue(value);
			} while (t != null);
		}
		else {
		    if (key == null)
		        throw new NullPointerException();
		    Comparable<? super K> k = (Comparable<? super K>) key;
		    do {
		        parent = t;
		        cmp = k.compareTo(t.key);
		        if (cmp < 0)
		            t = t.left;
		        else if (cmp > 0)
		            t = t.right;
		        else
		            return t.setValue(value);
		    } while (t != null);
		}
		
		MyEntry<K,V> e = new MyEntry<>(key, value, parent);
		
		if (cmp < 0)
		    parent.left = e;
		else
		    parent.right = e;
		
		fixAfterInsertion(e);
		size++;
		return null;
	}
	
	/**
	 * LEFT ROTATE
	 * */
	private void rotateLeft(MyEntry<K, V> e) {
		if (e != null) {
			MyEntry<K, V> r = e.right;
			e.right = r.left;
			if (r.left != null)
				r.left.parent = e;
			
			r.parent = e.parent;
			if (e.parent == null)
				root = r;
			else if (e.parent.left == e) 
				e.parent.left = r;
			else 
				e.parent.right = r;
			
			r.left = e;
			e.parent = r;
		}
	}
	
	/**
	 * RIGHT ROTATE
	 * */
	private void rotateRight(MyEntry<K, V> e) {
		if (e != null) {
			MyEntry<K, V> l = e.right;
			e.right = l.right;
			if (l.right != null)
				l.right.parent = e;
			
			l.parent = e.parent;
			if (e.parent == null)
				root = l;
			else if (e.parent.right == e) 
				e.parent.right =l;
			else 
				e.parent.left = l;
			
			l.right = e;
			e.parent = l;
		}
	}
	
	
	
	private static <K,V> boolean colorOf(MyEntry<K,V> p) {
        return (p == null ? BLACK : p.color);
    }

    private static <K,V> MyEntry<K,V> parentOf(MyEntry<K,V> p) {
        return (p == null ? null: p.parent);
    }

    private static <K,V> void setColor(MyEntry<K,V> p, boolean c) {
        if (p != null)
            p.color = c;
    }

    private static <K,V> MyEntry<K,V> leftOf(MyEntry<K,V> p) {
        return (p == null) ? null: p.left;
    }

    private static <K,V> MyEntry<K,V> rightOf(MyEntry<K,V> p) {
        return (p == null) ? null: p.right;
    }
	
	
	
	
	private void fixAfterInsertion(MyEntry<K, V> x) {
		x.color = RED;
	    while (x != null && x != root && x.parent.color == RED) {
	            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
	                MyEntry<K,V> y = rightOf(parentOf(parentOf(x)));
	                if (colorOf(y) == RED) {
	                    setColor(parentOf(x), BLACK);
	                    setColor(y, BLACK);
	                    setColor(parentOf(parentOf(x)), RED);
	                    x = parentOf(parentOf(x));
	                } else {
	                    if (x == rightOf(parentOf(x))) {
	                        x = parentOf(x);
	                        rotateLeft(x);
	                    }
	                    setColor(parentOf(x), BLACK);
	                    setColor(parentOf(parentOf(x)), RED);
	                    rotateRight(parentOf(parentOf(x)));
	                }
	            } else {
	                MyEntry<K,V> y = leftOf(parentOf(parentOf(x)));
	                if (colorOf(y) == RED) {
	                    setColor(parentOf(x), BLACK);
	                    setColor(y, BLACK);
	                    setColor(parentOf(parentOf(x)), RED);
	                    x = parentOf(parentOf(x));
	                } else {
	                    if (x == leftOf(parentOf(x))) {
	                        x = parentOf(x);
	                        rotateRight(x);
	                    }
	                    setColor(parentOf(x), BLACK);
	                    setColor(parentOf(parentOf(x)), RED);
	                    rotateLeft(parentOf(parentOf(x)));
	                }
	            }
	        }
	        root.color = BLACK;
	}
	
	
		
	@Override
	public V remove(Object key) {
		MyEntry<K, V> p = getEntry(key);
		if (p == null)
			return null;
		
		V oldValue = p.value;
		deleteEntry(p);
		return oldValue;
	}
	
	
	private void deleteEntry(MyEntry<K, V> p) {
		 size--;
 		// If strictly internal, copy successor's element to p and then make p
        // point to successor.
        if (p.left != null && p.right != null) {
             MyEntry<K,V> s = successor (p);
             p.key = s.key;
             p.value = s.value;
             p = s;
         } // p has 2 children
 
         // Start fixup at replacement node, if it exists.
         MyEntry<K,V> replacement = (p.left != null ? p.left : p.right);
 
         if (replacement != null) {
             // Link replacement to parent
             replacement.parent = p.parent;
             if (p.parent == null)
                 root = replacement;
             else if (p == p.parent.left)
                 p.parent.left  = replacement;
             else
                 p.parent.right = replacement;
 
             // Null out links so they are OK to use by fixAfterDeletion.
             p.left = p.right = p.parent = null;
 
             // Fix replacement
             if (p.color == BLACK)
                 fixAfterDeletion(replacement);
         } else if (p.parent == null) { // return if we are the only node.
             root = null;
         } else { //  No children. Use self as phantom replacement and unlink.
             if (p.color == BLACK)
                 fixAfterDeletion(p);
 
             if (p.parent != null) {
                 if (p == p.parent.left)
                     p.parent.left = null;
                 else if (p == p.parent.right)
                     p.parent.right = null;
                 p.parent = null;
             }
         }
	}
	
	
	private void fixAfterDeletion(MyEntry<K,V> x) {
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                MyEntry<K,V> sib = rightOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    sib = rightOf(parentOf(x));
                }

                if (colorOf(leftOf(sib))  == BLACK &&
                    colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sib)) == BLACK) {
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    rotateLeft(parentOf(x));
                    x = root;
                }
            } else { // symmetric
                MyEntry<K,V> sib = leftOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(sib)) == BLACK &&
                    colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }

        setColor(x, BLACK);
   }
	
	
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	
	static final class MyEntry<K, V> implements Map.Entry<K, V> {
		
		
		K key;
		V value;
		MyEntry<K, V> left = null;
		MyEntry<K, V> right = null;
		MyEntry<K, V> parent;
		boolean color = BLACK;
		
		MyEntry(K key, V value, MyEntry<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
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
		public V setValue(V arg0) {
			V oldValue = this.value;
			this.value = arg0;
			return oldValue;
		}
		
	}

}
