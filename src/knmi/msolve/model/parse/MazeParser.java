package knmi.msolve.model.parse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;

public abstract class MazeParser implements IMazeParser {

	private Boolean[][] rawStructure;
	private int width, height;
	private Node entrance, exit;
	
	public Set<Node> createGraph(Boolean[][] rawStructure) {
		this.rawStructure = rawStructure;
		this.width = rawStructure[0].length;
		this.height = rawStructure.length;

		Set<Node> nodes = new HashSet<>();
		Map<String, Node> nodeMap = new HashMap<>();
		Node[][] nodesArr = new Node[height][width];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {				
				if(isEntrance(x, y) || isTurn(x, y) || isDeadEnd(x, y)) {
					Node newNode = new Node(x, y);
					Node n;
					
					if(isOnEdge(x, y)) {
						if(entrance == null) entrance = newNode;
						else if(exit == null) exit = newNode;
					}
					
					n = traverseUp(x, y, nodeMap);
					if(n != null){ 
						Node.connect(n, newNode);
					}

					n = traverseLeft(x, y, nodeMap);
					if(n != null){
						Node.connect(n, newNode);
					}
					
					nodesArr[y][x] = newNode;
					nodeMap.put(newNode.getKey(), newNode);
					nodes.add(newNode);
				}
			}
		}
		
		// Unlike the generator, we are faced with too many rows and columns
		// To make it more compact, if a row or column only has 'passageway' nodes than we can remove it
		boolean[] redundantRows = new boolean[nodesArr.length];
		boolean[] redundantCols = new boolean[nodesArr[0].length];

		
		for(int y = 0; y < nodesArr.length; y++) {
			// Check if row is redundant
			if(isRedundantRow(nodesArr, y)){
				redundantRows[y] = true;
				//nrOfRedundantRows++;
			}
		}
		
		for(int x = 0; x < nodesArr[0].length; x++) {
			// Check if column is redundant
			if(isRedundantCol(nodesArr, x)){
				redundantCols[x] = true;
				//nrOfRedundantCols++;
			}
		}
		
		// Don't make edges redundant
		redundantRows[0] = false;
		redundantRows[redundantRows.length-1] = false;
		redundantCols[0] = false;
		redundantCols[redundantCols.length-1] = false;
		
		int nrOfRedundantRows = 0;
		for(int i = 0; i < redundantRows.length; i++) {
			
			if(redundantRows[i]) nrOfRedundantRows++;
			
			for(Node n : nodesArr[i]) {
				if(n != null)
					n.setLocation(n.x, n.y - nrOfRedundantRows);
			}
		}
		
		int nrOfRedundantCols = 0;
		for(int i = 0; i < redundantCols.length; i++) {
			
			if(redundantCols[i]) nrOfRedundantCols++;
			
			for(int x = 0; x < redundantRows.length; x++) {
				Node n = nodesArr[x][i];
				if(n != null)
					n.setLocation(n.x - nrOfRedundantCols, n.y);
			}
		}
		
		width = width - nrOfRedundantCols;
		height = height - nrOfRedundantRows;

		return nodes;
	}
	
	/**
	 * Outside classes should call this to return a parsed maze
	 */
	public final Maze parse(){
		// Do some post processing
		Set<Node> nodes = createGraph(parseMaze());
		return new Maze(getWidth(), getHeight(), getEntrance(), getExit(), nodes);
	}
	
	/**
	 * Because we need some post-processing the actual maze parsing in subclasses happens here
	 */
	protected abstract Boolean[][] parseMaze();

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

	private boolean isWallAbove(int x, int y) {
		return(rawStructure[y - 1][x]);
	}
	private boolean isWallLeft(int x, int y) {
		return(rawStructure[y][x - 1]);
	}
	private boolean isWallRight(int x, int y) {
		return(rawStructure[y][x + 1]);
	}
	private boolean isWallBelow(int x, int y) {
		return(rawStructure[y + 1][x]);
	}
	private boolean isHTunnel(int x, int y) {
		return isWallAbove(x, y) && isWallBelow(x, y);
	}
	private boolean isVTunnel(int x, int y) {
		return isWallLeft(x, y) && isWallRight(x, y);
	}
	
	private boolean isDeadEnd(int x, int y) {
		return	!isOnEdge(x, y) && !rawStructure[y][x] && (
				(isHTunnel(x, y) && isWallLeft(x, y)) ||
				(isHTunnel(x, y) && isWallRight(x, y)) ||
				(isVTunnel(x, y) && isWallAbove(x, y)) ||
				(isVTunnel(x, y) && isWallBelow(x, y)));
	}
	
	private boolean isTurn(int x, int y) {
		return	!isOnEdge(x, y) && !rawStructure[y][x] && (
				(!isWallAbove(x, y) && !isWallRight(x, y)) ||
				(!isWallRight(x, y) && !isWallBelow(x, y)) ||
				(!isWallBelow(x, y) && !isWallLeft(x, y)) ||
				(!isWallLeft(x, y) && !isWallAbove(x, y)));
	}
	
	private boolean isEntrance(int x, int y) {
		return !rawStructure[y][x] && isOnEdge(x, y);
	}
	
	private boolean isOnEdge(int x, int y) {
		return (x == 0 || x == width-1) || (y == 0 || y == height-1);
	}
	
	private Node traverseUp(int x, int y, Map<String, Node> nodeMap) {
		for(int i = y - 1; i >= 0; i--) {
			if(isEntrance(x, i)) {
				return(nodeMap.get(Node.createKey(x, i)));
			}
			else if(i > 1 && isTurn(x, i)) {
				return(nodeMap.get(Node.createKey(x, i)));
			}
			// If cell is a wall
			else if(rawStructure[i][x]) {
				// Return node (if exists) from previous cell
				return(nodeMap.get(Node.createKey(x, i + 1)));
			}
		}
		return null;
	}

	private Node traverseLeft(int x, int y, Map<String, Node> nodeMap) {
		for(int i = x - 1; i >= 0; i--) {
			if(isEntrance(i, y)) {
				return(nodeMap.get(Node.createKey(i, y)));
			}
			else if(i > 1 && isTurn(i, y)) {
				return(nodeMap.get(Node.createKey(i, y)));
			}
			// If cell is a wall
			else if(rawStructure[y][i]) {
				// Return node (if exists) from previous cell
				return(nodeMap.get(Node.createKey(i + 1, y)));
			}
		}
		return null;
	}
	
	private boolean isRedundantRow(Node[][] nodes, int rowIdx) {
		for(Node node : nodes[rowIdx]) {
			if(node == null) continue;
			if(!Node.isPassageWay(node)) return false;
		}
		return true;
	}
	
	private boolean isRedundantCol(Node[][] nodes, int colIdx) {
		for(int i = 0; i < nodes.length; i++){
			Node node = nodes[i][colIdx];
			if(node == null) continue;
			if(!Node.isPassageWay(node)) return false;
		}
		return true;
	}
}
