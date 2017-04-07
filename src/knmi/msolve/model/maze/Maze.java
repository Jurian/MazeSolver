package knmi.msolve.model.maze;

import java.util.Set;

/**
 * Internal representation of a maze. The maze is stored as a graph with the nodes stored in a set.
 * @author baasj
 *
 */
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
