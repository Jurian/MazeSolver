package knmi.msolve.model.generate;

import knmi.msolve.model.maze.Node;

/**
 * <p>
 * Mazes can be created with recursive division, an algorithm which works as follows: Begin with the maze's space with no walls. 
 * Call this a chamber. Divide the chamber with a randomly positioned wall (or multiple walls) where each wall contains a randomly 
 * positioned passage opening within it. Then recursively repeat the process on the subchambers until all chambers are minimum sized. 
 * This method results in mazes with long straight walls crossing their space, making it easier to see which areas to avoid.
 * </p>
 * @author baasj
 *
 */
public class RecursiveDivisionMazeGenerator extends MazeGenerator {

	public RecursiveDivisionMazeGenerator(int width, int height) {
		super(width, height);
	}

	@Override
	protected void generateMaze() {
		// Begin with the maze's space with no walls. Call this a chamber. 
		// Divide the chamber with a randomly positioned wall (or multiple walls) 
		// where each wall contains a randomly positioned passage opening within it. 
		subDivideX(R.nextInt(width-1), 0, nodesArr.length, 0, nodesArr[0].length);
		// Then recursively repeat the process on the subchambers until all chambers are minimum sized. 
	}
	

	private void subDivideX(int loc, int yMin, int yMax, int xMin, int xMax) {

		int height = Math.abs(yMax - yMin);
		if (height >= 2) {
			// Create a vertical border
			for (int i = yMin; i < yMax; i++) {
				// System.out.println(i + " " + loc);
				Node.disconnect(nodesArr[i][loc], nodesArr[i][loc + 1]);
			}
			// Make a hole in a random location
			int hole = R.nextInt(height) + yMin;
			Node.connect(nodesArr[hole][loc], nodesArr[hole][loc + 1]);

			int newLoc1 = R.nextInt(height-1) + yMin ;
			int newLoc2 = R.nextInt(height-1) + yMin ;
			subDivideY(newLoc1, xMin, loc + 1, yMin, yMax);
			subDivideY(newLoc2, loc + 1, xMax, yMin, yMax);
		}
	}

	private void subDivideY(int loc, int xMin, int xMax, int yMin, int yMax) {

		int width = Math.abs(xMax - xMin);
		if (width >= 2) {
			// Create a horizontal border
			for (int i = xMin; i < xMax; i++) {
				Node.disconnect(nodesArr[loc][i], nodesArr[loc + 1][i]);
			}
			// Make a hole in a random location
			int hole = R.nextInt(width) + xMin;
			Node.connect(nodesArr[loc][hole], nodesArr[loc + 1][hole]);

			
			int newLoc1 = R.nextInt(width-1) + xMin  ;
			int newLoc2 = R.nextInt(width-1) + xMin  ;
			subDivideX(newLoc1, yMin, loc + 1, xMin, xMax);
			subDivideX(newLoc2, loc + 1, yMax, xMin, xMax);
		}
	}

}
