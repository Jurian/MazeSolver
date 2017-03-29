package knmi.msolve.model.maze;

import java.util.Set;

public class Maze {

	private final Node entrance, exit;
	private final int width, height;
	private final Set<Node> nodes;
	
	public Maze(int width, int height, Node entrance, Node exit, Set<Node> nodes){
		this.entrance = entrance;
		this.exit = exit;
		this.width = width;
		this.height = height;
		this.nodes = nodes;
	}
	
	public Node getEntrance() {
		return entrance;
	}
	
	public Node getExit() {
		return exit;
	}

	public int getHeight() {
		return height;
	}
	
	public int getWidth(){
		return width;
	}

	public Set<Node> getNodes() {
		return nodes;
	}
}
