package knmi.msolve.model.parse;

import java.awt.image.BufferedImage;
import java.util.Set;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;

public class ImageMazeParser extends MazeParser {

	private final BufferedImage img;

	public ImageMazeParser(BufferedImage img) {
		this.img = img;
	}

	@Override
	public Maze parse() {
		
		int[][] pixels = getPixelData();

		// Find wall color (assume top left pixel is wall)
		int wallColor = pixels[0][0];
		// Find width of walls (assumed constant)
		int wallWidth = 1;
		while (pixels[wallWidth][wallWidth] == wallColor) {
			wallWidth++;
		}
		// Find path width (assumed constant)
		int pathWidth = pixels.length - wallWidth*2;
		// Scan image row by row
		for(int y = wallWidth; y < pixels.length - wallWidth; y++) {

			int currentPathWidth = 0;
			
			for(int x = wallWidth; x < pixels[y].length - wallWidth; x++) {
				int pixelColor = pixels[x][y];
				
				if(pixelColor == wallColor) {
					
					if(currentPathWidth > 0 && currentPathWidth < pathWidth) {
						pathWidth = currentPathWidth;
					}
					
				} else {
					currentPathWidth++;
				}
			}
			
		}
		// Search the other way too, column wise
		for(int y = wallWidth; y < pixels.length - wallWidth; y++) {

			int currentPathWidth = 0;
			
			for(int x = wallWidth; x < pixels[y].length - wallWidth; x++) {
				int pixelColor = pixels[y][x];
				
				if(pixelColor == wallColor) {
					
					if(currentPathWidth > 0 && currentPathWidth < pathWidth) {
						pathWidth = currentPathWidth;
					}
					
				} else {
					currentPathWidth++;
				}
			}
			
		}

		Boolean[][] data;
		
		if(pathWidth == wallWidth) {
			data = new Boolean[pixels.length / wallWidth][pixels[0].length / wallWidth];

			for (int y = 0; y < pixels.length / wallWidth; y++) {
				for (int x = 0; x < pixels[y].length / wallWidth; x++) {
					data[y][x] = pixels[y * wallWidth][x * wallWidth] == wallColor;
				}
			}
			
		} else {

			data = new Boolean[((pixels.length - wallWidth) / (wallWidth + pathWidth) * 2) + 1][((pixels[0].length - wallWidth) / (wallWidth + pathWidth) * 2) + 1];

			int a = 0;
			boolean skipY = false;
			for(int y = 0; y < pixels.length; y = skipY ? y + wallWidth : y + pathWidth){
				int b = 0;
				boolean skipX = false;
				
				for(int x = 0; x < pixels[y].length ; x = skipX ? x + wallWidth : x + pathWidth) {

					data[a][b] = pixels[y][x] == wallColor;

					b++;
					skipX = !skipX;
				}
				a++;
				skipY = !skipY;
			}
		}
		
		Set<Node> nodes = createGraph(data);
		
		return new Maze(getWidth(), getHeight(), getEntrance(), getExit(), nodes);
	}
	
	private int[][] getPixelData() {
		int width = img.getWidth();
		int height = img.getHeight();
		int[][] result = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				result[row][col] = img.getRGB(col, row);
			}
		}
		return result;
	}

}
