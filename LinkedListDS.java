//Jose Paterno
//masc0396
package data_structures;
import java.util.Iterator;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;
import java.lang.Comparable;

public class LinkedListDS<E> implements ListADT<E> {

	protected Node<E> first,last;
	protected int currentSize;

	public LinkedListDS(){
		first = last = null;		
	}

	//  Adds the Object obj to the beginning of the list
	public void addFirst(E obj) {
		Node<E> newNode = new Node<E>(obj);		
		if(size() == 0){
			first = last = newNode;
		}
		else{
			newNode.next = first;
			first = newNode;		
		}
		currentSize ++;
	}

	//  Adds the Object obj to the end of the list
	public void addLast(E o) {
		Node<E> newNode = new Node<E>(o);
		if(size() == 0){
			first = last = newNode;
		}
		else{
			last.next = newNode;		
			last = newNode;			
		}
		currentSize++;
	}

	//  Removes the first Object in the list and returns it.
	public E removeFirst() {		
		if(first == null)return null;	
		E temp = (E)first.data;	
		first = first.next;	
		if(first == null) last = null;
		currentSize--;
		return temp;		
	}

	//  Removes the last Object in the list and returns it.
	//  Returns null if the list is empty.
	public E removeLast() {			
		Node<E> currentNode = first;
		Node<E> prevNode = null;
		
		while(currentNode != null && currentNode != last){
			prevNode = currentNode;
			currentNode = currentNode.next;
		}	
		
		if(currentNode == null) return null;
		E temp = last.data;
		
		if(first == last){
			first = last = null;
			currentSize--; 
			return temp;		
		}
		else{
			prevNode.next = null;
			last = prevNode;
			currentSize--;
			return temp;
		}
	}

	//  Returns the first Object in the list, but does not remove it.
	//  Returns null if the list is empty.
	public E peekFirst() {		
		if(size() == 0) return null;
		return (E) first.data;		
	}

	//  Returns the last Object in the list, but does not remove it.
	//  Returns null if the list is empty.
	public E peekLast() {		
		if(size() == 0) return null;
		return (E) last.data;
	}

	//  Finds and returns the Object obj if it is in the list, otherwise
	//  returns null.  Does not modify the list in any way
	public E find(E obj) {
		if(contains(obj)) return obj;
		return null;
	}

	//  Removes the first instance of the specific Object obj from the list, if it exists.
	//  Returns true if the Object obj was found and removed, otherwise false
	public boolean remove(E obj){	
		Node<E> currentNode = first;
		Node<E> prevNode=null;

		while(currentNode != null && ((Comparable<E>) obj).compareTo((E) currentNode.data)!= 0){			
			prevNode = currentNode;
			currentNode = currentNode.next;	
			if(currentNode == null) return false;
		}

		if(currentNode == first){
			removeFirst();
		}
		else if(currentNode == last){ 					
			removeLast();
		}
		else {
			prevNode.next = currentNode.next;
			currentSize--;
		}		
		return true;
	}


	//  The list is returned to an empty state.
	public void makeEmpty() {
		first = last = null;
		currentSize = 0;
	}

	//  Returns true if the list contains the Object obj, otherwise false
	public boolean contains(E obj) {
		Node<E> currentNode = first;
		while(currentNode != null && ((Comparable <E>)obj).compareTo((E) currentNode.data) != 0 ){
			currentNode = currentNode.next;		
		}	
		if(currentNode == null) 
			return false;	
		return true;		
	}

	//  Returns true if the list is empty, otherwise false
	public boolean isEmpty() {
		if(currentSize == 0) return true;
		return false;
	}

	//  Returns true if the list is full, otherwise false
	public boolean isFull() {
		return false;
	}

	//  Returns the number of Objects currently in the list.
	public int size() {		
		return currentSize;
	}


	//  Returns an Iterator of the values in the list, presented in
	//  the same order as the list.
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected class Node<E>{
		E data;
		Node<E> next;		
		public Node(E data){
			this.data = data;
			next = null;
		}		
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	class IteratorHelper implements Iterator<E>{

		Node<E> iterPointer;

		IteratorHelper(){
			iterPointer = first;
		}

		public E next(){
			if(!hasNext()) throw new NoSuchElementException();
			E tmp = (E) iterPointer.data;
			iterPointer = iterPointer.next;
			return tmp;
		}

		public boolean hasNext(){
			return iterPointer != null;
		}

		public void remove(){
			throw new UnsupportedOperationException();
		}		

	}	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
