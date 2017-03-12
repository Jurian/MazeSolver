package knmi.msolve.model.maze;

import java.awt.Point;

public class Node extends Point {
	
	private static final long serialVersionUID = 1L;

	private Node top, right, bottom, left;
	private final String key;
	
	public Node(int x, int y) {
		super(x, y);
		key = createKey(x, y);
	}

	public Node getTop() {
		return top;
	}

	public void setTop(Node top) {
		this.top = top;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getBottom() {
		return bottom;
	}

	public void setBottom(Node bottom) {
		this.bottom = bottom;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}
	
	public static String createKey(int x, int y){
		return  x + "," + y;
	}
	
	public String getKey() {
		return key;
	}
	
	public String toString(){
		return "Node[x="+x+",y="+y+"]" +
				(left != null ? 	" left:   [x=" + left.x + ",y=" + left.y +"]" : "") +
				(right != null ? 	" right:  [x=" + right.x + ",y=" + right.y+"]" : "") +
				(top != null ? 		" top:    [x=" + top.x + ",y=" + top.y+"]" : "") +
				(bottom != null ? 	" bottom: [x="  + bottom.x + ",y=" + bottom.y+"]" : "");
	}

}
