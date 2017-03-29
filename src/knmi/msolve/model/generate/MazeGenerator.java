package knmi.msolve.model.generate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import knmi.msolve.model.maze.Node;

public abstract class MazeGenerator implements IMazeGenerator {
	
	protected static final Random R = new Random();
	protected final int width, height;
	protected final Node start, end;
	protected final Set<Node> nodes = new HashSet<>();
	
	public MazeGenerator(int width, int height){
		this.width = width;
		this.height = height;
		
		Node[][] graph = new Node[height-2][width-2];
		// Instantiate all nodes
		for(int y = 0; y < graph.length; y++){
			for(int x = 0; x < graph[y].length; x++){
				graph[y][x] = new Node(x + 1, y +1 );
				nodes.add(graph[y][x]);
			}
		}

		int borderWidth = width-2;
		int borderHeight = height-2;
		
		// Create all edges between nodes
		for(int y = 0; y < graph.length; y++){
			for(int x = 0; x < graph[y].length; x++){
				Node n = graph[y][x];
				
				if(y > 0) n.addNeighbor(graph[y-1][x]);
				if(y < borderHeight-1) n.addNeighbor(graph[y+1][x]);
				if(x < borderWidth-1) n.addNeighbor(graph[y][x+1]);
				if(x > 0) n.addNeighbor(graph[y][x-1]);
			}
		}
		

		// Pick some random point along the edges, but not the corners
		// The number of cells that qualify for a start or end node
		int borderCandidates = 2*borderWidth + 2*borderHeight;
		// Pick a random starting location
		int startIdx = R.nextInt(borderCandidates);
		// Set end as the opposite mirrored location
		int endIdx = (startIdx + borderCandidates / 2) % borderCandidates;

		
		if(startIdx <= borderWidth) { // Top border
			start = new Node(startIdx, 0);
		}else if(startIdx <= borderWidth + borderHeight) { // Right border
			start = new Node(width-1, startIdx - borderWidth);
		}else if(startIdx <= 2*borderWidth + borderHeight) { // Bottom border
			start = new Node((width-1)- (startIdx - (borderWidth + borderHeight)) , height-1);
		}else { // Left border
			start = new Node(0, (height-1) - (startIdx - (2*borderWidth + borderHeight)));
		}
		
		if(endIdx <= borderWidth) { // Top border
			end = new Node(endIdx, 0);
		}else if(endIdx <= borderWidth + borderHeight) { // Right border
			end = new Node(width-1, endIdx - borderWidth);
		}else if(endIdx <= 2*borderWidth + borderHeight) { // Bottom border
			end = new Node((width-1) - (endIdx - (borderWidth + borderHeight)) , height-1);
		}else { // Left border
			end = new Node(0, (height-1) - (endIdx - (2*borderWidth + borderHeight)));
		}

		nodes.add(start);
		nodes.add(end);
		
		// Connect the start and end nodes to the graph
		if(start.y == 0) {
			Node.connect(start, graph[0][start.x-1]);
		} else if(start.y == height-1){
			Node.connect(start, graph[ graph.length-1 ][start.x-1]);
		} else if(start.x == 0){
			Node.connect(start, graph[start.y-1][0]);
		} else if(start.x == width-1){
			Node.connect(start, graph[start.y-1][ graph[0].length-1 ]);
		}
		
		if(end.y == 0) {
			Node.connect(end, graph[0][end.x-1]);
		} else if(end.y == height-1){
			Node.connect(end, graph[ graph.length-1 ][end.x-1]);
		} else if(end.x == 0){
			Node.connect(end, graph[end.y-1][0]);
		} else if(end.x == width-1){
			Node.connect(end, graph[end.y-1][ graph[0].length-1 ]);
		}
		
	}
	
	public void removeRedundantNodes() {
		// Remove redundant nodes
		Iterator<Node> i = nodes.iterator();
		while (i.hasNext()) {
			Node node = i.next(); 
			if(node.neighborCount() == 2) {
				Node n1 = node.getNeighbor(0);
				Node n2 = node.getNeighbor(1);
				if((n1.x == n2.x) || (n1.y == n2.y)){
					n1.removeNeighbor(node);
					n2.removeNeighbor(node);
					Node.connect(n1, n2);
					i.remove();
				}
			}
		}
	}

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
