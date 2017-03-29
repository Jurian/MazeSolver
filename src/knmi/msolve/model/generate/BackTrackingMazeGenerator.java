package knmi.msolve.model.generate;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;


public class BackTrackingMazeGenerator extends MazeGenerator {
	
	private Set<Node> visited = new HashSet<>();
	
	public BackTrackingMazeGenerator(int width, int height) {
		super(width, height);
	}

	@Override
	public Maze generate() {

		
		Queue<Node> stack = Collections.asLifoQueue(new ArrayDeque<>());
		Map<Node, Node> path = new HashMap<>();
		
		stack.add(getStart());
		while(!stack.isEmpty()) {
			Node current = stack.peek();
			visited.add(current);
			
			// If the node at the top of the stack has untried neighbors
			if (nodeHasUntriedNeighbors(current)) {
				// Pick a random unvisited neighbor
				int dir;
				do {
					dir = R.nextInt(4);
				} while (isNodeVisited(current, dir));
				Node neighbor = current.getNeighbor(dir);

				// Push the next untried neighbor onto the stack
				stack.add(neighbor);

				// Remove the wall between the current node and the chosen node
				path.put(neighbor, current);
				
			} else {
				// pop the node off the stack
				stack.poll();
			}
		}
		
		// Put walls everywhere
		for(Node node : visited) {
			node.clearNeighbors();
		}
		
		// Clear walls that are on a path
		for(Node node : visited) {
			Node pathTo = path.get(node);
			if(pathTo != null) {
				Node.connect(node, pathTo);
			}
		}
		
		removeRedundantNodes();
		
		return new Maze(width, height, getStart(), getEnd(), getNodes());
	}
	
	private boolean nodeHasUntriedNeighbors(Node n) {
		for(Node neighbor : n) {
			if(!visited.contains(neighbor)) return true;
		}
		return false;
	}
	
	private boolean isNodeVisited(Node n, int dir) {
		Node neighbor = n.getNeighbor(dir);
		if(neighbor == null) return true;
		return visited.contains(neighbor);
	}

}
