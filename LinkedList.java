/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the first node of the list
	 * @return The first node of the list.
	 */		
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */		
	public Node getLast() {
		return this.last;
	}
	
	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */		
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index > size || size == 0) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		ListIterator itr = iterator();
		for (int i = 0; i < index; i++){
			itr.next();
		}
		return itr.current;
	}
	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index > size){
			return;
		}
		if (index == 0){
			addFirst(block);
			if (index == size){
				addLast(block);
				size--; // it is being increased twice
			}
		}
		else if (index == size){
			addLast(block);
		}
		else{
			Node mem = new Node(block);
			ListIterator itr = iterator();
			for (int i = 0; i < index - 1; i++){
				itr.next();
			}
			mem.next = itr.current.next;
			itr.current.next = mem;
			size++;
		}		
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		Node mem = new Node(block);
		if (last != null){
			last.next = mem;
			last = mem;
		}
		else{
			last = mem;
			first = mem;
		}
		size++;
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		Node mem = new Node(block);
		if (first != null){
			mem.next = first;
			first = mem;
		}
		else{
			first = mem;
			last = mem;
		}
		
		size++;
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		Node temp = getNode(index);
		if (temp != null){
			return temp.block;
		}
		else{
			return null;
		}
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		ListIterator itr = iterator();
		for (int i = 0; i < size; i++){
			if (block.equals(itr.current.block)){
				return i;
			}
			itr.next();
		}
		return -1;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		if (node == null){
			throw new NullPointerException(
					" NullPointerException!");
		}
		if (node.equals(first)){
			first = first.next;
			if (first == null){
				last = null;
			}
		}
		else if (node.equals(last)){
			last = getNode(size - 2);
			getNode(size - 2).next = null;
			if (size - 1 == 1){
				first = last;
			}
		}
		else{
			ListIterator itr = iterator();
			for (int i = 0; i < size - 2; i++){
				if (itr.current.next.equals(node)){
					itr.current.next = node.next;
				}
				itr.next();
			}
		}		
		size--;
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		remove(getNode(index));
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		remove(getNode(indexOf(block)));
	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		String s = "";
		ListIterator itr = iterator();
		for (int i = 0; i < size; i++){
			s += itr.current.block.toString() + " ";
			if (itr.hasNext()){
				itr.next();
			}
			else{   // means there is an error
				System.out.println("error printing list:");
				return s;
			}
		}
		return s;
	}
}
