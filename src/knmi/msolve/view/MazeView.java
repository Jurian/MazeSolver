package knmi.msolve.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
			

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, viewWidth, viewHeight);
			
			Graphics2D g2 = (Graphics2D) g;
		    RenderingHints rh = new RenderingHints(
		             RenderingHints.KEY_TEXT_ANTIALIASING,
		             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		    g2.setRenderingHints(rh);
		    
			g2.setColor(Color.WHITE);
			g2.setStroke(new BasicStroke(Math.max(resoHeight, resoWidth)/2));
			
			for (Node n : maze.getNodes()) {
				for(Node adj : n) {
					g2.drawLine(
						n.x * resoWidth + resoWidth/2, n.y * resoHeight + resoHeight/2, 
						adj.x * resoWidth + resoWidth/2, adj.y * resoHeight + resoHeight/2);
				}
			}
			
			
			g2.setColor(Color.RED);
			if(path != null) {
				int i = 0;
				for(Node n : path) {
					
					g2.setColor(Color.RED);
					if(i > 0) {
						Node prev = path.getNodes().get(i-1);
						g2.drawLine(n.x * resoWidth + resoWidth/2,
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
