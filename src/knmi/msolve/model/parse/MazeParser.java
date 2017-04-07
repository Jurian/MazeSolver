package knmi.msolve.model.parse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;

public abstract class MazeParser implements IMazeParser {

	private Boolean[][] rawStructure;
	private int width, height;
	private Node entrance, exit;
	private Set<Node> nodes = new HashSet<>();
	
	public Set<Node> createGraph(Boolean[][] rawStructure) {
		this.rawStructure = rawStructure;
		this.width = rawStructure[0].length;
		this.height = rawStructure.length;
		
		/*
		for(int y = 0; y < rawStructure.length; y++) {
			for(int x = 0; x < rawStructure[y].length; x++) {
				// Leave wall nodes as null
				if(!rawStructure[y][x]) nodesArr[y][x] = new Node(x, y);
			}
		}
		
		// Connect neighboring nodes
		for(int y = 0; y < nodesArr.length; y++) {
			for(int x = 0; x < nodesArr[y].length; x++) {
				Node n = nodesArr[y][x];
				
				if(n != null) {
					
					if(y > 0 && nodesArr[y-1][x] != null){
						Node.connect(n, nodesArr[y-1][x]);
					} 
					
					if(y < height-1 && nodesArr[y+1][x]!= null) {
						Node.connect(n, nodesArr[y+1][x]);
					}
					
					if(x < width-1 && nodesArr[y][x+1] != null) {
						Node.connect(n, nodesArr[y][x+1]);
					}
					
					if(x > 0 && nodesArr[y][x-1] != null) {
						Node.connect(n, nodesArr[y][x-1]);
					}
				}
				

			}
		}
		
		// Unlike the generator, we are faced with too many rows and columns
		// To make it more compact, if a row or column only has 'passageway' nodes than we can remove it
		boolean[] redundantRows = new boolean[nodesArr.length];
		boolean[] redundantCols = new boolean[nodesArr[0].length];
		int nrOfRedundantRows = 0;
		int nrOfRedundantCols = 0;
		
		for(int y = 0; y < nodesArr.length; y++) {
			// Check if row is redundant
			if(isRedundantRow(nodesArr, y)){
				redundantRows[y] = true;
				nrOfRedundantRows++;
			}
		}
		
		for(int x = 0; x < nodesArr[0].length; x++) {
			// Check if column is redundant
			if(isRedundantCol(nodesArr, x)){
				redundantCols[x] = true;
				nrOfRedundantCols++;
			}
		}
		
		// Make a smaller array without the redundant rows and columns
		Node[][] test = new Node[nodesArr.length - nrOfRedundantRows][nodesArr[0].length - nrOfRedundantCols];
		int a,b;
		for(int y = 0; y < nodesArr.length; y++) {
			if(redundantRows[y]) continue;
			
			for(int x = 0; x < nodesArr[y].length; x++) {
				
				if(redundantCols[x]) continue;
				
				
				
			}
		}
		*/
		
		
		Map<String, Node> nodeMap = new HashMap<>();
		Node[][] nodesArr = new Node[height][width];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {				
				if(isEntrance(x, y) || isTurn(x, y) || isDeadEnd(x, y)) {
					Node newNode = new Node(x-1, y-1);
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
		int nrOfRedundantRows = 0;
		int nrOfRedundantCols = 0;
		
		for(int y = 0; y < nodesArr.length; y++) {
			// Check if row is redundant
			if(isRedundantRow(nodesArr, y)){
				redundantRows[y] = true;
				nrOfRedundantRows++;
			}
		}
		
		for(int x = 0; x < nodesArr[0].length; x++) {
			// Check if column is redundant
			if(isRedundantCol(nodesArr, x)){
				redundantCols[x] = true;
				nrOfRedundantCols++;
			}
		}
		
		//redundantRows = Arrays.copyOfRange(redundantRows, 1, redundantRows.length-1);
		//redundantCols = Arrays.copyOfRange(redundantCols, 1, redundantCols.length-1);
		redundantRows[0] = false;
		redundantRows[redundantRows.length-1] = false;
		redundantCols[0] = false;
		redundantCols[redundantCols.length-1] = false;
		
		/*
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
		
		
		for(int y = 0; y < redundantRows.length; y++){
			if(redundantRows[y]){
				// This row serves no purpose
				for(Node redundant : nodesArr[y]){
					Node n1 = redundant.getNeighbor(0);
					Node n2 = redundant.getNeighbor(1);
					
					n1.removeNeighbor(redundant);
					n2.removeNeighbor(redundant);
					Node.connect(n1, n2);
				}
			}
		}
		*/
		
		//removeRedundantNodes();
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
	
	
	/**
	 * Some nodes are a gateway between two other nodes and are in a straight line.
	 * These nodes are redundant and can be removed for smaller storage and faster traversal
	 */
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
