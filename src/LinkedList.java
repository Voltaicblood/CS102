
public class LinkedList implements ListInterface{
	Node head = null;
	
	public LinkedList(){head = null;}
	
	public Node getHead(){
		return head;
	}

	public boolean isEmpty() {
		return (head==null);
	}

	public int size() {
		return size(head);
	}
	
	private int size(Node current){
		if (current == null) return 0;
		return 1 + size(current.getNext());
	}

	public Object get(int index) {
		if ((index < 0) || (index >= size()))
			throw new IndexOutOfBoundsException();
		return get(index, head);
	}
	
	private Object get(int index, Node current){
		if(index == 0)
			return current.getDatum();
		return get((index - 1), current.getNext());
	}
	
	public void add(int index, Object datum) {
		head = add(index, datum, head);
	}
	
	private Node add(int index, Object datum, Node current){
		if (index == 0){
			Node splice = new Node();
			splice.setDatum(datum);
			splice.setNext(current);
			return splice;
		}
		if (current == null)
			throw new IndexOutOfBoundsException();
		current.setNext(add(index-1, datum, current.getNext()));
		return current;
	}

	public Object remove(int index) {
		Object removedData = get(index);
		head = remove(index, head);
		return removedData;
	}
	
	private Node remove(int index, Node current){
		if (current == null)
			throw new IndexOutOfBoundsException();
		if (index == 0)
			return current.getNext();
		current.setNext(remove(index-1, current.getNext()));
		return current;
	}

	public void removeAll() {
		head = null;
	}
	
	public boolean isSortedAlph(){
		return isSortedAlph(head);
	}
	
	private boolean isSortedAlph(Node current){
		if (current.getNext() == null) return true;
		String currentCallsign = ((Station) current.getDatum()).getCallSign();
		String nextCallsign = ((Station) current.getNext().getDatum()).getCallSign();
		if (currentCallsign.compareTo(nextCallsign) > 0) return false;
		return isSortedAlph(current.getNext());
	}
	
	public void alphabeticalSort(){
		while (!isSortedAlph()){
			head = alphabeticalSort(head);
		}
	}
	
	private Node alphabeticalSort(Node current){
		Node next = current.getNext();
		if (next == null) return current;
		String currentCallsign = ((Station) current.getDatum()).getCallSign();
		String nextCallsign = ((Station) next.getDatum()).getCallSign();
		int comparison = currentCallsign.compareTo(nextCallsign);
		if (comparison > 0){
			current.setNext(next.getNext());
			next.setNext(alphabeticalSort(current));
			return next;
		}
		alphabeticalSort(next);
		return current;
	}
}
