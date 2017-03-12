package knmi.msolve.model.solve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;
import knmi.msolve.model.maze.Path;

public class AStarMazeSolver extends MazeSolver {
	
	private static final int COST_INF = Integer.MAX_VALUE;
	
	 // The set of nodes already evaluated.
	private final Set<Node> closedSet  = new HashSet<>();
    // The set of currently discovered nodes that are not evaluated yet.
    // Initially, only the start node is known.
	private final Set<Node> openSet  = new HashSet<>();
    // For each node, which node it can most efficiently be reached from.
    // If a node can be reached from many nodes, cameFrom will eventually contain the
    // most efficient previous step.
	private final Map<Node, Node> cameFrom = new HashMap<>();	
	 // For each node, the cost of getting from the start node to that node.
	private final Map<Node, Double> gScore = new HashMap<>();	
    // For each node, the total cost of getting from the start node to the goal
    // by passing by that node. That value is partly known, partly heuristic.
	private final Queue<NodeF> fScore = new PriorityQueue<>();
	
	private final NodeF start, end;
	
	public AStarMazeSolver(Maze maze) {
		super(maze);
		
		start = new NodeF(maze.getEntrance());
		end = new NodeF(maze.getExit());
		start.fScore = heuristicCostEstimate(start.node, end.node);

		openSet.add(start.node);
		gScore.put(start.node, 0D);
		fScore.add(start);
	}
	
	private double heuristicCostEstimate(Node start, Node end){
		return start.distance(end);
	}

	@Override
	public Path solve() {

		while(!openSet.isEmpty()) {
			
			// The node in openSet having the lowest fScore value
			Node current = fScore.poll().node;
			while(!openSet.contains(current)) {
				current = fScore.poll().node;
			}
			
	        if(current == end.node){
	        	return reconstructPath(current);
	        }  
			
		    openSet.remove(current);
		    closedSet.add(current);
		    
		    for(Node neighbor : new NodeNeighborIterator(current)) {
		    	// Ignore the neighbor which is already evaluated
		    	if(closedSet.contains(neighbor)) continue;
		    	
	            // The distance from start to a neighbor
	            double tentativeGScore = gScore.get(current) + heuristicCostEstimate(current, neighbor);
	            
	            // Discover a new node
	            if(!openSet.contains(neighbor)) {
	            	openSet.add(neighbor);
	            }else if(tentativeGScore >= gScore.get(neighbor)){
	            	continue; // This is not a better path.
	            }
	    		
	            // This path is the best until now. Record it!
	            cameFrom.put(neighbor, current);
	            gScore.put(neighbor, tentativeGScore);
	            fScore.add(new NodeF(neighbor, heuristicCostEstimate(neighbor, end.node)));
	           
		    }
			
		}
		
		return null;
	}

	private Path reconstructPath(Node current) {
	    Path p = new Path();
	    p.add(current);
	    
	    while(cameFrom.containsKey(current)){
	    	current = cameFrom.get(current);
	    	p.add(current);
	    }
	    
	    return p;
	}

	// Wrapper for Node
	private class NodeF implements Comparable<NodeF> {
		
		private final Node node;
		private double fScore;
		
		public NodeF(Node node){
			this(node, COST_INF);
		}
		
		public NodeF(Node node, double fScore){
			this.node = node;
			this.fScore = fScore;
		}

		@Override
		public int compareTo(NodeF n) {
			return Double.compare(this.fScore, n.fScore);
		}

	}
	
	private class NodeNeighborIterator implements Iterable<Node>{
		
		private final Node node;
		
		public NodeNeighborIterator(Node node){
			this.node = node;
		}
		
		@Override
		public Iterator<Node> iterator() {
			List<Node> neighbors = new ArrayList<>();
			if(node.getTop() != null) {
				neighbors.add(node.getTop());
			}
			if(node.getRight() != null) {
				neighbors.add(node.getRight());
			}
			if(node.getBottom() != null) {
				neighbors.add(node.getBottom());
			}
			if(node.getLeft() != null) {
				neighbors.add(node.getLeft());
			}
			return neighbors.iterator();
		}
	}
}
