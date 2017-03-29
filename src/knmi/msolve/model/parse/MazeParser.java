package knmi.msolve.model.parse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import knmi.msolve.model.maze.Node;

public abstract class MazeParser implements IMazeParser {

	private Boolean[][] rawStructure;
	private int width, height;
	private Node entrance, exit;
	
	public Set<Node> createGraph(Boolean[][] rawStructure) {
		this.rawStructure = rawStructure;
		this.width = rawStructure[0].length;
		this.height = rawStructure.length;
		
		Map<String, Node> nodeMap = new HashMap<>();
		Set<Node> nodes = new HashSet<>();
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
						newNode.addNeighbor(n);
						n.addNeighbor(newNode);
					}

					n = traverseLeft(x, y, nodeMap);
					if(n != null){
						newNode.addNeighbor(n);
						n.addNeighbor(newNode);
					}
					
					nodeMap.put(newNode.getKey(), newNode);
					nodes.add(newNode);
				}
			}
		}
		return nodes;
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
}
