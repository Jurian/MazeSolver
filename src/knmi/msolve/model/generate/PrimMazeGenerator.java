package knmi.msolve.model.generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;

public class PrimMazeGenerator extends MazeGenerator {
	
	public PrimMazeGenerator(int width, int height) {
		super(width, height);
	}

	@Override
	public Maze generate() {
	
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
	
		
		removeRedundantNodes();
		
		return new Maze(width, height, getStart(), getEnd(), getNodes());
	}
	
	class Wall {
		private final Node n1,n2;

		public Wall(Node n1, Node n2) {
			this.n1 = n1;
			this.n2 = n2;
		}
	}

}
