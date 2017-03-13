package knmi.msolve.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;
import knmi.msolve.model.maze.Path;

public class MazeView extends JPanel {

	private static final long serialVersionUID = 1L;

	private Maze maze;
	private Path path;
	
	public void setMaze(Maze maze) {
		this.maze = maze;
		//this.setSize(maze.getWidth() * 10, maze.getHeight() * 10);
		this.setPreferredSize(new Dimension(maze.getWidth() * 10, maze.getHeight() * 10));
		if(path != null) path = null;
	}
	
	public void setPath(Path path) {
		this.path = path;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(maze != null) {
			int viewHeight = this.getHeight();
			int viewWidth = this.getWidth();
			
			int resoHeight = viewHeight / maze.getHeight();
			int resoWidth = viewWidth / maze.getWidth();
			
			Boolean[][] structure = maze.getRawStructure();
			
			g.setColor(Color.BLACK);
			g.clearRect(0, 0, viewWidth, viewHeight);
			
			for(int y = 0; y < maze.getHeight(); y++){
				for(int x = 0; x < maze.getWidth();  x++){
					g.setColor(structure[y][x] ? Color.BLACK : Color.WHITE );
					g.fillRect(x * resoWidth, y * resoHeight, resoWidth, resoHeight);
				}
			}
			
			if(path != null) {
				g.setColor(Color.RED);
				int i = 0;
				for(Node n : path) {
					g.fillOval(n.x * resoWidth, n.y * resoHeight, resoWidth, resoHeight);
					
					if(i > 0) {
						Node prev = path.getNodes().get(i-1);
						g.drawLine(	n.x * resoWidth + resoWidth/2,
									n.y * resoHeight + resoHeight/2, 
									prev.x * resoWidth + resoWidth/2,
									prev.y * resoHeight + resoHeight/2);
					}
					
					i++;
				}
			}
			
		}

	}

}
