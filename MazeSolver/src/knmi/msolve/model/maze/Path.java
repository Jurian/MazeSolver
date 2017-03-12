package knmi.msolve.model.maze;

import java.util.ArrayList;
import java.util.List;

public class Path {

	private final List<Node> nodes = new ArrayList<>();

	public List<Node> getNodes() {
		return nodes;
	}
	
	public void add(Node n){
		nodes.add(n);
	}
}
