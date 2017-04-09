package knmi.msolve.model.generate;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import knmi.msolve.model.maze.Node;

/**<p>
 * This algorithm is a randomized version of the depth-first search algorithm
 * using backtracking.
 * </p>
 * <p>
 * Frequently implemented with a stack, this approach is one of the simplest ways to generate a maze using a computer. 
 * Consider the space for a maze being a large grid of cells (like a large chess board), each cell starting with four walls. 
 * Starting from a random cell, the computer then selects a random neighboring cell that has not yet been visited. 
 * The computer removes the wall between the two cells and marks the new cell as visited, and adds it to the stack to facilitate backtracking. 
 * The computer continues this process, with a cell that has no unvisited neighbors being considered a dead-end. When at a dead-end it backtracks 
 * through the path until it reaches a cell with an unvisited neighbor, continuing the path generation by visiting this new, 
 * unvisited cell (creating a new junction). This process continues until every cell has been visited, causing the computer to backtrack all 
 * the way back to the beginning cell. We can be sure every cell is visited.
 * </p>
 * @author baasj
 *
 */
public class BackTrackingMazeGenerator extends MazeGenerator {
	
	private Set<Node> visited = new HashSet<>();
	
	public BackTrackingMazeGenerator(int width, int height, boolean polar) {
		super(width, height, polar);
	}

	@Override
	public void generateMaze() {

		Queue<Node> stack = Collections.asLifoQueue(new ArrayDeque<>());
		Map<Node, Node> path = new HashMap<>();
		
		stack.add(nodesArr[R.nextInt(height)][R.nextInt(width)]);
		
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
