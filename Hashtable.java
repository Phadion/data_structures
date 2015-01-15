/*

Name: Jose Paterno
masc0396
CS310
Prof. Alan Riggins

*/
//HASHTABLE IMPLEMENTATION


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;

public class Hashtable<K,V> implements DictionaryADT<K,V> {
	//instance variables
	private LinkedListDS<DictionaryNode<K,V>> [] list;
	private int currentSize,maxSize,tableSize;
	private long modCounter;

	public Hashtable(int n){
		currentSize = 0;
		maxSize = n;
		tableSize = (int) (maxSize*1.3f);
		modCounter = 0;		
		list = new LinkedListDS[tableSize];
		//puts an empty LinkedListDS in each element
		for(int i=0; i<tableSize;i++){
			list[i] = new LinkedListDS<DictionaryNode<K,V>>();
		}

	}

	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	public boolean contains(K key){
		return list[getHashCode(key)].contains(
				new DictionaryNode<K,V>(key,null));
	}

	// Adds the given key/value pair to the dictionary.  Returns
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean insert(K key, V value){
		if(isFull())
			return false;
		if(list[getHashCode(key)].contains(new DictionaryNode<K,V>(key,null)))
			return false;

		list[getHashCode(key)].addLast(new DictionaryNode<K,V>(key,value));
		currentSize++;
		modCounter++;
		return true;
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean remove(K key){
		if(list[getHashCode(key)].remove(new DictionaryNode<K,V>(key,null))){
			currentSize--;
			modCounter++;		
			return true;
		}
		else return false;
	}

	// Returns the value associated with the parameter key.  Returns
	// null if the key is not found or the dictionary is empty.
	public V getValue(K key){
		DictionaryNode<K,V> tmp = list[getHashCode(key)]
				.find(new DictionaryNode<K,V>(key,null));
		if(tmp==null)return null;
		return tmp.value;
	}

	// Returns the key associated with the parameter value.  Returns
	// null if the value is not found in the dictionary.  If more
	// than one key exists that matches the given value, returns the
	// first one found.
	public K getKey(V value){
		for(int i=0;i<tableSize;i++){
			if(list[i].size()==0) continue;
			DictionaryNode<K,V> dictNode=list[i].iterator().next();
			if(((Comparable<V>)value).compareTo(dictNode.value) == 0)
				return dictNode.key;					
		}	
		return null;	
	}

	// Returns the number of key/value pairs currently stored
	// in the dictionary
	public int size(){
		return currentSize;
	}

	// Returns true if the dictionary is at max capacity
	public boolean isFull(){
		return currentSize==maxSize;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty(){
		return currentSize==0;
	}

	// Returns the Dictionary object to an empty state.
	public void clear(){	
		for(LinkedListDS n : list)
			n.makeEmpty();
		currentSize = 0;
		modCounter++;
	}

	// Returns an Iterator of the keys in the dictionary, in ascending
	// sorted order.  The iterator must be fail-fast.
	public Iterator<K> keys(){
		if(new KeyIterator()==null) return null;
		return new KeyIterator();
	}

	// Returns an Iterator of the values in the dictionary.  The
	// order of the values must match the order of the keys.
	// The iterator must be fail-fast.
	public Iterator<V> values(){
		if(new ValueIterator()==null) return null;
		return new ValueIterator();
	}
	///////////////////////////////////////////////////////////////////////
	//Helper Functions													 //
	///////////////////////////////////////////////////////////////////////
	private int getHashCode(K key){
		return (key.hashCode() & 0x7FFFFFFF ) % tableSize;
		//		return key.hashCode() % tableSize;
	}

	private DictionaryNode[] shellSort(DictionaryNode [] array){
		DictionaryNode [] n =array;
		int in,out,h=1;
		DictionaryNode temp = null;
		int size = n.length;

		while(h <= size/3)
			h = h*3+1;
		while(h > 0){
			for(out=h; out < size; out++){				
				temp = n[out];
				in = out;
				if(n[in-h]==null) return null;
				while(in > h-1 
						&& (n[in-h].compareTo(temp)>0 
								|| n[in-h].compareTo(temp)==0 )){
					n[in] = n[in-h];
					in-=h;
				}
				n[in] = temp;				
			}
			h = (h-1)/3;
		}
		return n;		
	}

	///////////////////////////////////////////////////////////////////////
	//Private Classes													 //
	///////////////////////////////////////////////////////////////////////
	private class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>>{
		K key;
		V value;
		public DictionaryNode(K key,V value){
			this.key = key;
			this.value = value;
		}		
		public int compareTo(DictionaryNode<K,V> node){
			return ((Comparable<K>)key).compareTo((K)node.key);
		}
	}

	abstract class IteratorHelper<E> implements Iterator<E>{
		protected DictionaryNode<K,V>  [] nodes;
		protected int idx;
		protected long modCheck;

		public IteratorHelper(){
			nodes = new DictionaryNode[currentSize];
			idx = 0;
			int j = 0;
			modCheck = modCounter;
			for(int i = 0; i < tableSize; i++){
				for(DictionaryNode n: list[i]){
					nodes[j++] = n;
				}//end inner for loop
			}//end for loop
			nodes = (DictionaryNode<K,V>[]) shellSort(nodes);

		}//end constructor


		public boolean hasNext(){
			if(modCheck!=modCounter)
				throw new ConcurrentModificationException();	
			return idx < currentSize;
		}//end hasNext


		public abstract E next ();

		public void remove(){
			throw new UnsupportedOperationException();		
		}


	}//end class



	private class KeyIterator<K> extends IteratorHelper<K>{
		public KeyIterator(){
			super();
		}
		public K next(){
			if(!hasNext())
				throw new NoSuchElementException();			
			return (K) nodes[idx++].key;
		}		
	}//end class

	private class ValueIterator<V> extends IteratorHelper<V>{
		public ValueIterator(){
			super();
		}

		public V next(){
			if(!hasNext())
				throw new NoSuchElementException();
			return (V) nodes[idx++].value;
		}			

	}//end class	


}





