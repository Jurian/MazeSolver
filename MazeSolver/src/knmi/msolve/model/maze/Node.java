package knmi.msolve.model.maze;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Node extends Point implements Iterable<Node> {
	
	enum NodeDirection {
		TOP, RIGHT, BOTTOM, LEFT;
	}
	private static final long serialVersionUID = 1L;

	private Map<NodeDirection, Node> neighbors = new HashMap<>();

	private final String key;
	
	public Node(int x, int y) {
		super(x, y);
		key = createKey(x, y);
	}

	public Node getTop() {
		return neighbors.get(NodeDirection.TOP);
	}

	public void setTop(Node top) {
		neighbors.put(NodeDirection.TOP, top);
	}

	public Node getRight() {
		return neighbors.get(NodeDirection.RIGHT);
	}

	public void setRight(Node right) {
		neighbors.put(NodeDirection.RIGHT, right);
	}

	public Node getBottom() {
		return neighbors.get(NodeDirection.BOTTOM);
	}

	public void setBottom(Node bottom) {
		neighbors.put(NodeDirection.BOTTOM, bottom);
	}

	public Node getLeft() {
		return neighbors.get(NodeDirection.LEFT);
	}

	public void setLeft(Node left) {
		neighbors.put(NodeDirection.LEFT, left);
	}
	
	public static String createKey(int x, int y){
		return  x + "," + y;
	}
	
	public String getKey() {
		return key;
	}
	
	public String toString(){
		Node top = neighbors.get(NodeDirection.TOP);
		Node left = neighbors.get(NodeDirection.LEFT);
		Node right = neighbors.get(NodeDirection.RIGHT);
		Node bottom = neighbors.get(NodeDirection.BOTTOM);
		return "Node[x="+x+",y="+y+"]" +
				(left != null ? 	" left:   [x=" + left.x + ",y=" + left.y +"]" : "") +
				(right != null ? 	" right:  [x=" + right.x + ",y=" + right.y+"]" : "") +
				(top != null ? 		" top:    [x=" + top.x + ",y=" + top.y+"]" : "") +
				(bottom != null ? 	" bottom: [x="  + bottom.x + ",y=" + bottom.y+"]" : "");
	}

	@Override
	public Iterator<Node> iterator() {
		return neighbors.values().iterator();
	}

}
