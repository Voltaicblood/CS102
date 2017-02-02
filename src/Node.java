public class Node {
	Node next;
	Object datum;
	
	public Node(){
		next = null;
		datum = null;
	}

	public Node(Node next, Object datum){
		this.next = next;
		this.datum = datum;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
	
	public Object getDatum() {
		return datum;
	}

	public void setDatum(Object datum) {
		this.datum = datum;
	}
	
}