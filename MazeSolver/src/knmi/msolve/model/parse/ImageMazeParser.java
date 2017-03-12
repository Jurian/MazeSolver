package knmi.msolve.model.parse;

import java.awt.image.BufferedImage;

import knmi.msolve.model.maze.Maze;

public class ImageMazeParser implements IMazeParser {

	private final BufferedImage img;
	
	public ImageMazeParser(BufferedImage img) {
		this.img = img;
	}
	
	@Override
	public Maze parse() {
		int wall = img.getRGB(0, 0);
		// Find maze resolution
		int res = 1;
		while(img.getRGB(res, res) == wall) {
			res++;
		}
		
		Boolean[][] data = new Boolean[img.getHeight() / res][img.getWidth() / res];
		
		for(int y = 0; y < img.getHeight() / res; y++){
			for(int x = 0; x < img.getWidth() / res; x++){
				data[y][x] = img.getRGB(x * res, y * res) == wall;
			}
		}

		return new Maze(data);
	}

}
