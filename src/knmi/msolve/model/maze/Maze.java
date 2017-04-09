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
	private final boolean polar;
	
	public Maze(int width, int height, Node entrance, Node exit, Set<Node> nodes){
		this(width, height, entrance, exit, nodes, false);
	}
	
	public Maze(int width, int height, Node entrance, Node exit, Set<Node> nodes, boolean polar){
		this.entrance = entrance;
		this.exit = exit;
		this.width = width;
		this.height = height;
		this.nodes = nodes;
		this.polar = polar;
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

	public boolean isPolar() {
		return polar;
	}
}
