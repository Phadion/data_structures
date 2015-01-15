//BALANCED TREE IMPLEMENTATION



import java.util.Iterator;
import java.util.TreeMap;

public class BalancedTree<K,V> implements DictionaryADT<K,V>{
	TreeMap balancedTree;
	K key; 
	V value;

	public BalancedTree(){
		balancedTree = new TreeMap();
		key=null;
		value = null;
	}

	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	public boolean contains(K key){
		return balancedTree.containsKey(key);
	}

	// Adds the given key/value pair to the dictionary.  Returns
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean insert(K key, V value){
		if(balancedTree.containsKey(key))
			return false;
		else{
			balancedTree.put(key, value);
			return true;
		}
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean remove(K key){
		if(balancedTree.remove(key)==null) 
			return false;
		else{
			return true;
		}			
	}

	// Returns the value associated with the parameter key.  Returns
	// null if the key is not found or the dictionary is empty.
	public V getValue(K key){
		return (V) balancedTree.get(key);
	}

	// Returns the key associated with the parameter value.  Returns
	// null if the value is not found in the dictionary.  If more
	// than one key exists that matches the given value, returns the
	// first one found.
	public K getKey(V value){
		Iterator<K> keyIter = balancedTree.keySet().iterator();
		K keyIndex = keyIter.next();
		while(keyIter.hasNext()){
			if(((Comparable<V>)value).compareTo(
					(V) balancedTree.get(keyIndex))== 0)
				return keyIndex;
			
			keyIndex = keyIter.next();
		}
		return null;	
	}

	// Returns the number of key/value pairs currently stored
	// in the dictionary
	public int size(){
		return balancedTree.size();
	}

	// Returns true if the dictionary is at max capacity
	public boolean isFull(){
		return false;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty(){
		return balancedTree.isEmpty();
	}

	// Returns the Dictionary object to an empty state.
	public void clear(){
		balancedTree.clear();
	}

	// Returns an Iterator of the keys in the dictionary, in ascending
	// sorted order.  The iterator must be fail-fast.
	public Iterator<K> keys(){
		return  balancedTree.navigableKeySet().iterator();
	}

	// Returns an Iterator of the values in the dictionary.  The
	// order of the values must match the order of the keys.
	// The iterator must be fail-fast.
	public Iterator<V> values(){
		return balancedTree.values().iterator();
	}

}


