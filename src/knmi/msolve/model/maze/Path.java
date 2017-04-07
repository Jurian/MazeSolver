package knmi.msolve.model.maze;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A solution to a maze is represented as a list of nodes to traverse.
 * @author baasj
 *
 */
public class Path implements Iterable<Node> {

	private final List<Node> nodes = new ArrayList<>();

	public List<Node> getNodes() {
		return nodes;
	}
	
	public void add(Node n){
		nodes.add(n);
	}

	@Override
	public Iterator<Node> iterator() {
		return nodes.iterator();
	}
	
	public int length() {
		return nodes.size();
	}
}
