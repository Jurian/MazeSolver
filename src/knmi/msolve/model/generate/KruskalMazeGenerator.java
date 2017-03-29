package knmi.msolve.model.generate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;

public class KruskalMazeGenerator extends MazeGenerator {

	public KruskalMazeGenerator(int width, int height) {
		super(width, height);
	}

	@Override
	public Maze generate() {
		List<Wall> walls = new ArrayList<>();
		Map<Node, LinkedList<Node>> nodeMap = new HashMap<>();
		Set<Node> visited = new HashSet<>();
		
		for(Node node : nodes) {
			visited.add(node);

			LinkedList<Node> ll = new LinkedList<>();
			ll.add(node);
			nodeMap.put(node, ll);
			
			for(Node adj : node){
				if(!visited.contains(adj)){
					walls.add(new Wall(node, adj));
				}
			}
			// Set up walls around this cell
			node.clearNeighbors();
		}
		
		// For each wall, in some random order
		Collections.shuffle(walls);
		for(Wall wall : walls) {
			
			Node n1 = wall.n1;
			Node n2 = wall.n2;
			
			LinkedList<Node> disjointSet1 = nodeMap.get(n1);
			LinkedList<Node> disjointSet2 = nodeMap.get(n2);
			
			// If the cells divided by this wall belong to distinct sets
			if(disjointSet1 != disjointSet2) {
			    // Remove the current wall.
				Node.connect(n1, n2);
				
			    // Merge the sets of the formerly divided cells
				// Try adding the smaller set to the larger one
				LinkedList<Node> newSet;
				if(disjointSet1.size() < disjointSet2.size()) {
					disjointSet2.addAll(disjointSet1);
					newSet = disjointSet2;
				} else {
					disjointSet1.addAll(disjointSet2);
					newSet = disjointSet1;
				}
				
				// Update the reference for each node in the merged set
				for(Node n : newSet) {
					nodeMap.put(n, newSet);
				}
			}
			
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
