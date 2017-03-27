package knmi.msolve.model.generate;

import java.util.Random;

import knmi.msolve.model.maze.Node;

public abstract class MazeGenerator implements IMazeGenerator {
	
	private static final Random R = new Random();
	protected final int width, height;
	protected final Node[][] graph;
	protected Node start, end;
	
	public MazeGenerator(int width, int height){
		this.width = width;
		this.height = height;
		
		graph = new Node[height][width];
		// Instantiate all nodes
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				graph[y][x] = new Node(x + 1, y + 1);
			}
		}

		// Create all edges between nodes
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				Node n = graph[y][x];
				
				if(y > 0) n.addNeighbor(graph[y-1][x]);
				if(y < height-1) n.addNeighbor(graph[y+1][x]);
				if(x < width-1) n.addNeighbor(graph[y][x+1]);
				if(x > 0) n.addNeighbor(graph[y][x-1]);
			}
		}

		// Pick some random point along the edges, but not the corners
		int startIdx = R.nextInt(2*width+2*height-2*4);
		int endIdx;
		do { // Make sure that start and end are spaced apart
			endIdx = R.nextInt(2*width+2*height-2*4);
		} while(Math.abs(endIdx - startIdx) < Math.max(width, height) / 1.5);
		
		
		int i = 0;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				// Skip cells not on edge
				if(x > 0 && x < width-1 && y > 0 && y < height-1) continue;
				// Skip cells in corners
				if(x == 0 && y == 0) continue;
				if(x == 0 && y == height-1) continue;
				if(x == width-1 && y == 0) continue;
				if(x == width-1 && y == height-1) continue;
				
				if(i == startIdx) {
					start = graph[y][x];
				} else if(i == endIdx) {
					end = graph[y][x];
				}
				i++;
			}
		}
	}
	

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Node[][] getGraph(){
		return graph;
	}
	
	public Node getStart(){
		return start;
	}
	
	public Node getEnd(){
		return end;
	}
}
