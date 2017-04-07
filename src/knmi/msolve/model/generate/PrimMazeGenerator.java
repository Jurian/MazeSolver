package knmi.msolve.model.generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import knmi.msolve.model.maze.Node;

/**
 * <p> This algorithm is a randomized version of Prim's algorithm. </p>
 * 
 * <p>
 * In computer science, Prim's algorithm is a greedy algorithm that finds a minimum spanning tree for a weighted undirected graph. 
 * This means it finds a subset of the edges that forms a tree that includes every vertex, where the total weight of all the edges 
 * in the tree is minimized. The algorithm operates by building this tree one vertex at a time, from an arbitrary starting vertex, 
 * at each step adding the cheapest possible connection from the tree to another vertex.
 * </p>
 * @author baasj
 *
 */
public class PrimMazeGenerator extends MazeGenerator {
	
	public PrimMazeGenerator(int width, int height) {
		super(width, height);
	}

	@Override
	protected void generateMaze() {
	
		Map<Node, List<Wall>> wallMap = new HashMap<>();
		Set<Node> visited = new HashSet<>();
		List<Wall> walls = new ArrayList<>();
		
		for(Node node : nodes) {
			visited.add(node);
			List<Wall> wallList = new ArrayList<>();
			for(Node adj : node){
				wallList.add(new Wall(node, adj));
			}
			wallMap.put(node, wallList);
			node.clearNeighbors();
		}
		visited.clear();
		
		// Pick a cell, mark it as part of the maze
		visited.add(getStart());
		// Add the walls of the cell to the wall list
		walls.addAll(wallMap.get(getStart()));
		
		// While there are walls in the list
		while(walls.size() > 0) {
			// Pick a random wall
			Wall w = walls.get(R.nextInt(walls.size()));
			
			// If only one of the two cells that the wall divides is visited
			if(visited.contains(w.n1) ^ visited.contains(w.n2)) {
				
				// Make the wall a passage
				Node.connect(w.n1, w.n2);
				
				Node unvisited;
				if(visited.contains(w.n1)) {
					unvisited = w.n2;
				}else {
					unvisited = w.n1;
				}
				// Mark the unvisited cell as part of the maze
				visited.add(unvisited);
				
				// Add the neighboring walls of the cell to the wall list
				walls.addAll(wallMap.get(unvisited));
			}
			
			// Remove the wall from the list
			walls.remove(w);
		}
	}
	
	class Wall {
		private final Node n1,n2;

		public Wall(Node n1, Node n2) {
			this.n1 = n1;
			this.n2 = n2;
		}
	}

}
