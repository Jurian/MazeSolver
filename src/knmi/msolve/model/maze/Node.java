package knmi.msolve.model.maze;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Node extends Point implements Iterable<Node> {
	
	private static final long serialVersionUID = 1L;

	private List<Node> neighbors = new ArrayList<>();

	private final String key;
	
	public Node(int x, int y) {
		super(x, y);
		key = createKey(x, y);
	}
	
	public int neighborCount(){
		return neighbors.size();
	}
	
	public void clearNeighbors() {
		neighbors.clear();
	}
	
	public void removeNeighbor(Node n){
		neighbors.remove(n);
	}
	
	public Node getNeighbor(int i) {
		if(i >= neighbors.size()) return null;
		return neighbors.get(i);
	}
	
	public void addNeighbor(Node n) {
		if(!neighbors.contains(n))
			neighbors.add(n);
	}
	
	public static String createKey(int x, int y) {
		return  x + "," + y;
	}
	
	public String getKey() {
		return key;
	}
	
	public String toString() {
		return (int) getX() + "," + (int) getY() + " -> " + Arrays.toString(neighbors.stream().map(n -> n.x + "," + n.y).toArray());
	}

	@Override
	public Iterator<Node> iterator() {
		return neighbors.iterator();
	}
	
	public static void connect(Node n1, Node n2){
		n1.addNeighbor(n2);
		n2.addNeighbor(n1);
	}
	
	public static void disconnect(Node n1, Node n2){
		n1.removeNeighbor(n2);
		n2.removeNeighbor(n1);
	}

}
