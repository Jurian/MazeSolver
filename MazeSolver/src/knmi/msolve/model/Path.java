package knmi.msolve.model;

import java.awt.Point;
import java.util.List;

public class Path {

	private final List<Point> points;
	
	public Path(List<Point> points) {
		this.points = points;
	}

	public List<Point> getPoints() {
		return points;
	}
}
