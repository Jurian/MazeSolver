package knmi.msolve.model.generate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;

/**<p>
 * Builds on top of the IMazeGenerator interface. Concrete maze generator implementations should subclass this abstract class.
 * </p>
 * @author baasj
 *
 */
public abstract class MazeGenerator implements IMazeGenerator {
	
	protected static final Random R = new Random();
	/**
	 * Size of the maze, including borders
	 */
	protected final int width, height;
	/**
	 * Start- and end-points of the maze
	 */
	protected final Node start, end;
	/**
	 * Set of all nodes in this maze, useful for some maze generation algorithms
	 */
	protected final Set<Node> nodes = new HashSet<>();
	/**
	 * All nodes represented as a two dimensional array, useful for some maze generation algorithms
	 */
	protected final Node[][] nodesArr;
	
	public MazeGenerator(int width, int height){
		this.width = width;
		this.height = height;
		
		// Don't count border as part of the maze
		//int borderlessWidth = width-2;
		//int borderlessHeight = height-2;
		
		// Represent graph temporarily as a two dimensional array for easy and fast indexing
		nodesArr = new Node[height][width];
		// Instantiate all nodes
		for(int y = 0; y < nodesArr.length; y++){
			for(int x = 0; x < nodesArr[y].length; x++){
				nodesArr[y][x] = new Node(x, y);
				nodes.add(nodesArr[y][x]);
			}
		}

		// Create all edges between nodes
		for(int y = 0; y < nodesArr.length; y++){
			for(int x = 0; x < nodesArr[y].length; x++){
				Node n = nodesArr[y][x];
				
				if(y > 0) Node.connect(n, nodesArr[y-1][x]);
				if(y < height-1) Node.connect(n, nodesArr[y+1][x]);
				if(x < width-1) Node.connect(n, nodesArr[y][x+1]);
				if(x > 0) Node.connect(n, nodesArr[y][x-1]);
			}
		}

		// Pick some random point along the edges, but not the corners
		// The number of cells that qualify for a start or end node
		int borderCandidates = 2*width + 2*height;
		// Pick a random starting location
		int startIdx = R.nextInt(borderCandidates);
		// Set end as the opposite mirrored location
		int endIdx = (startIdx + borderCandidates / 2) % borderCandidates;

		
		if(startIdx <= width) { // Top border
			start = new Node(startIdx, -1);
		}else if(startIdx <= width + height) { // Right border
			start = new Node(width, startIdx - width);
		}else if(startIdx <= 2*width + height) { // Bottom border
			start = new Node(width- (startIdx - (width + height)) , height);
		}else { // Left border
			start = new Node(-1, height - (startIdx - (2*width + height)));
		}
		
		if(endIdx <= width) { // Top border
			end = new Node(endIdx, -1);
		}else if(endIdx <= width + height) { // Right border
			end = new Node(width, endIdx - width);
		}else if(endIdx <= 2*width + height) { // Bottom border
			end = new Node(width - (endIdx - (width + height)) , height);
		}else { // Left border
			end = new Node(-1, height - (endIdx - (2*width + height)));
		}

		nodes.add(start);
		nodes.add(end);

		// Connect the start and end nodes to the graph
		if(start.y == -1) {
			Node.connect(start, nodesArr[0][start.x]);
		} else if(start.y == height){
			Node.connect(start, nodesArr[ nodesArr.length-1 ][start.x]);
		} else if(start.x == -1){
			Node.connect(start, nodesArr[start.y][0]);
		} else if(start.x == width){
			Node.connect(start, nodesArr[start.y][ nodesArr[0].length-1 ]);
		}
		
		if(end.y == -1) {
			Node.connect(end, nodesArr[0][end.x]);
		} else if(end.y == height){
			Node.connect(end, nodesArr[ nodesArr.length-1 ][end.x]);
		} else if(end.x == -1){
			Node.connect(end, nodesArr[end.y][0]);
		} else if(end.x == width){
			Node.connect(end, nodesArr[end.y][ nodesArr[0].length-1 ]);
		}
		
	}
	
	/**
	 * Some nodes are a gateway between two other nodes and are in a straight line.
	 * These nodes are redundant and can be removed for smaller storage and faster traversal
	 */
	public void removeRedundantNodes() {
		// Remove redundant nodes
		Iterator<Node> i = nodes.iterator();
		while (i.hasNext()) {
			Node node = i.next(); 
			if(Node.isPassageWay(node)){
				Node n1 = node.getNeighbor(0);
				Node n2 = node.getNeighbor(1);

				n1.removeNeighbor(node);
				n2.removeNeighbor(node);
				Node.connect(n1, n2);
				i.remove();
			}
		}
	}
	
	/**
	 * Outside classes should call this to return a generated maze
	 */
	public final Maze generate(){
		generateMaze();
		// Do some post processing
		removeRedundantNodes();
		return new Maze(width, height, getStart(), getEnd(), getNodes());
	}
	
	/**
	 * Because we need some post-processing the actual maze generation in subclasses happens here
	 */
	protected abstract void generateMaze();

	public Set<Node> getNodes() {
		return nodes;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Node getStart(){
		return start;
	}
	
	public Node getEnd(){
		return end;
	}
}
