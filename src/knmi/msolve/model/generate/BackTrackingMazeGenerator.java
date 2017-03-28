package knmi.msolve.model.generate;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;


public class DepthFirstMazeGenerator extends MazeGenerator {

	private static final Random R = new Random(1);
	private Set<Node> visited = new HashSet<>();
	private Queue<Node> stack = Collections.asLifoQueue(new ArrayDeque<>());
	private Map<Node, Node> path = new HashMap<>();
	
	public DepthFirstMazeGenerator(int width, int height) {
		super(width, height);
	}

	@Override
	public Maze generate() {

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
		
		for(Node node : visited) {
			node.clearNeighbors();
		}
		
		for(Node node : visited) {
			Node pathTo = path.get(node);
			if(pathTo != null) {
				node.addNeighbor(pathTo);
				pathTo.addNeighbor(node);
			}
		}
		System.out.println(visited.size());
		Iterator<Node> i = visited.iterator();
		while (i.hasNext()) {
			Node node = i.next(); 
			if(node.neighborCount() == 2) {
				Node n1 = node.getNeighbor(0);
				Node n2 = node.getNeighbor(1);
				if((n1.x == n2.x) || (n1.y == n2.y)){
					n1.removeNeighbor(node);
					n2.removeNeighbor(node);
					n1.addNeighbor(n2);
					n2.addNeighbor(n1);
					i.remove();
				}
			}
		}
		System.out.println(visited.size());
		
		for(Node node : visited) {
			System.out.println(node);
		}
		
		
		return null;
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
