//BINARY SEARCH TREE IMPLEMENTATION


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinarySearchTree<K,V> implements DictionaryADT<K,V> {

	//instance variables
	private Node<K,V> root;
	private int currentSize,arrayIndex;
	private long modCounter;
	private Node<K,V>[] arrayToPrint;
	private K keyToReturn;

	//constructor
	public BinarySearchTree(){
		root=null;
		keyToReturn = null;
		currentSize=arrayIndex=0;
		modCounter=0;
	}


	// Returns true if the dictionary has an object identified by
	// key in it, otherwise false.
	public boolean contains(K key){
		return findKey(key,root)!=null;
	}

	// Adds the given key/value pair to the dictionary.  Returns
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean insert(K key, V value){
		if(findKey(key,root)!=null) 
			return false;
		if(root==null)	
			root = new Node<K,V>(key,value);
		else	
			insertNode(key,value,root,null,false);
		currentSize++;
		modCounter++;
		return true;
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean remove(K key){
		if(removeNode(key,root,null,false)){
			currentSize--;
			modCounter++;
			return true;
		}
		return false;
	}

	// Returns the value associated with the parameter key.  Returns
	// null if the key is not found or the dictionary is empty.
	public V getValue(K key){

		return findValue(key,root);
	}

	// Returns the key associated with the parameter value.  Returns
	// null if the value is not found in the dictionary.  If more
	// than one key exists that matches the given value, returns the
	// first one found.
	public K getKey(V value){
		findTheKey(root,value);
		return keyToReturn;
	}

	// Returns the number of key/value pairs currently stored
	// in the dictionary
	public int size(){
		return currentSize;
	}

	// Returns true if the dictionary is at max capacity
	public boolean isFull(){
		return false;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty(){
		return currentSize==0;
	}

	// Returns the Dictionary object to an empty state.
	public void clear(){
		root=null;
		currentSize=0;
		modCounter++;
	}

	// Returns an Iterator of the keys in the dictionary, in ascending
	// sorted order.  The iterator must be fail-fast.
	public Iterator<K> keys(){	
		arrayToPrint =  new Node[currentSize];
		fillArrayInOrder(root);
		arrayIndex = 0;
		return new KeyIterator();
	}

	// Returns an Iterator of the values in the dictionary.  The
	// order of the values must match the order of the keys.
	// The iterator must be fail-fast.
	public Iterator<V> values(){
		arrayToPrint = new Node[currentSize];
		fillArrayInOrder(root);
		arrayIndex = 0;
		return new ValueIterator();
	}




	///////////////////////////////////////////////////////////////////////
	//Helper Functions													 //
	///////////////////////////////////////////////////////////////////////
	private V findValue(K key,Node<K,V> n){
		if(n==null) return null;
		if(((Comparable<K>)key).compareTo(n.key)<0)
			return findValue(key,n.leftNode);
		else if(((Comparable<K>)key).compareTo(n.key)>0)
			return findValue(key,n.rightNode);
		else
			return (V) n.value;		
	}

	private K findKey(K key,Node<K,V> n){
		if(n==null) return null;
		if(((Comparable<K>)key).compareTo(n.key)<0)
			return findKey(key,n.leftNode);
		else if(((Comparable<K>)key).compareTo(n.key)>0)
			return findKey(key,n.rightNode);
		else
			return (K) n.key;		
	}


	private Node<K,V> findNode(K key,Node<K,V> n){
		if(n==null) return null;
		if(((Comparable<K>)key).compareTo(n.key)<0)
			return findNode(key,n.leftNode);
		else if(((Comparable<K>)key).compareTo(n.key)>0)
			return findNode(key,n.rightNode);
		else
			return  n;		
	}

	private boolean removeNode(K key,Node<K,V> n, Node<K,V> parent,boolean wentLeft){

		if(n==null) return false;
		Node<K,V> successor;

		if(((Comparable<K>)key).compareTo(n.key)<0)
			removeNode(key,n.leftNode,n,true);
		else if(((Comparable<K>)key).compareTo(n.key)>0)
			removeNode(key,n.rightNode,n,false);
		else{//node found
			if(!n.hasChildren()){
				if(n == root)
					root = null;
				else{
					if(wentLeft)
						parent.leftNode = null;
					else parent.rightNode = null;
				}
			}
			else if(n.onlyHasLeftChild()){
				if(n==root)
					root=n.leftNode;
				else{
					if(wentLeft)
						parent.leftNode = n.leftNode;
					else parent.rightNode = n.leftNode;
				}
			}
			else if(n.onlyHasRightChild()){
				if(n==root)
					root=n.rightNode;
				else{
					if(wentLeft)
						parent.leftNode = n.rightNode;
					else parent.rightNode = n.rightNode;
				}
			}
			//node has two children
			else{
				if(n.rightNode.leftNode == null){
					successor = n.rightNode;
					successor.leftNode = n.leftNode;
					if(n==root)
						root = successor;
					else{
						if(wentLeft) 
							parent.leftNode = successor;
						else parent.rightNode = successor;
					}
				}
				else{
					successor = findSuccessor(n.rightNode,null);
					n.key = successor.key;
					n.value = successor.value;
				}
			}
		}
		return true;
	}


	private void insertNode(K k,V v,Node<K,V> n, Node<K,V> parent, boolean wasLeft){
		if(n==null){
			if(wasLeft) parent.leftNode = new Node<K,V>(k,v);
			else parent.rightNode = new Node<K,V>(k,v);
		}
		else if(((Comparable<K>)k).compareTo((K)n.key)<0)
			insertNode(k,v,n.leftNode,n,true);
		else
			insertNode(k,v,n.rightNode,n,false);	
	}


	private void fillArrayInOrder(Node<K,V> n){
		if(n==null) return;
		fillArrayInOrder(n.leftNode);
		arrayToPrint[arrayIndex++] =  n;
		fillArrayInOrder(n.rightNode);
	}

	private void findTheKey(Node<K,V> n, V value){
		if(n==null) return;

		if(((Comparable<V>)value).compareTo(n.value) == 0){
			keyToReturn = n.key;
			return;
		}

		findTheKey(n.leftNode,value);
		findTheKey(n.rightNode,value);				
	}

	private Node<K,V> findSuccessor(Node<K,V> n,Node<K,V> prevNode){

		if(n.leftNode == null){
			if(n.rightNode == null)
				prevNode.leftNode = null;
			else prevNode.leftNode = n.rightNode;
			return n;
		}
		return findSuccessor(n.leftNode,n);	
	}



	///////////////////////////////////////////////////////////////////////
	//Private Classes													 //
	///////////////////////////////////////////////////////////////////////
	private class Node<K,V>{
		K key;
		V value;
		Node<K,V> leftNode;
		Node<K,V> rightNode; 
		public Node(K k,V v){
			key = k;
			value = v;
			leftNode=rightNode=null;
		}

		public boolean hasChildren(){
			return (leftNode!=null || rightNode!=null);
		}

		public boolean onlyHasLeftChild(){
			return (leftNode!=null && rightNode == null);
		}

		public boolean onlyHasRightChild(){
			return (rightNode!=null && leftNode == null);
		}


	}

	private class KeyIterator<K> implements Iterator<K>{			
		private int iterIndex;		
		private int arraySize;
		private long modCheck;
		public KeyIterator(){			
			iterIndex = 0;
			arraySize = arrayToPrint.length;
			modCheck = modCounter;
		}
		public boolean hasNext(){
			if(modCheck!=modCounter)
				throw new ConcurrentModificationException();
			return iterIndex < arraySize;
		}

		public K next(){
			if(!hasNext()) throw new NoSuchElementException();
			Node<K, V> nodeI = (Node<K, V>) arrayToPrint[iterIndex++];
			return  nodeI.key;			
		}		
		public void remove(){
			throw new UnsupportedOperationException();
		}	
	}

	private class ValueIterator<V> implements Iterator<V>{			
		int iterIndex;		
		int arraySize;
		private long modCheck;
		public ValueIterator(){			
			iterIndex = 0;
			arraySize = arrayToPrint.length;
			modCheck = modCounter;
		}
		public boolean hasNext(){
			if(modCheck!=modCounter)
				throw new ConcurrentModificationException();
			return iterIndex < arraySize;
		}

		public V next(){
			if(!hasNext()) throw new NoSuchElementException();
			Node<K, V> nodeI = (Node<K, V>) arrayToPrint[iterIndex++];
			return  nodeI.value;			
		}		
		public void remove(){
			throw new UnsupportedOperationException();
		}	
	}
}
