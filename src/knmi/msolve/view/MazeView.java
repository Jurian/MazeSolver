package knmi.msolve.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;
import knmi.msolve.model.maze.Path;

public class MazeView extends JPanel {

	public static boolean DEBUG = true;
	private static final long serialVersionUID = 1L;

	private Maze maze;
	private Path path;
	
	public void setMaze(Maze maze) {
		this.maze = maze;
		
		int max = Math.max(maze.getWidth(), maze.getHeight());
		int min = Math.min(maze.getWidth(), maze.getHeight());

		// Either the width or height (or both) is 1024 pixels
		// The other side is scaled appropriately
		if(maze.getWidth() >= maze.getHeight()) {
			this.setPreferredSize(new Dimension( 1024 , (int) (min/(double)max * 1024)  ));
		} else {
			this.setPreferredSize(new Dimension( (int) (min/(double)max * 1024) , 1024  ));
		}
		
		if(path != null) path = null;
	}
	
	public void setPath(Path path) {
		this.path = path;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// This component can exist without a maze, so don't do anything if this is the case
		if(maze != null) {
			int viewHeight = this.getHeight();
			int viewWidth = this.getWidth();
			
			// Fill the component with black
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, viewWidth, viewHeight);
			
			// Set up a more advanced drawing object
			Graphics2D g2 = (Graphics2D) g;
		    RenderingHints rh = new RenderingHints(
		             RenderingHints.KEY_TEXT_ANTIALIASING,
		             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		    g2.setRenderingHints(rh);

			if(maze.isPolar()) {
				
				int resoHeight = viewHeight / (maze.getHeight());
				int resoWidth = viewWidth / (maze.getWidth());
				g2.setStroke(new BasicStroke(Math.max(resoHeight, resoWidth)/4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				
				// Draw the connections between nodes
				g2.setColor(Color.WHITE);
				
				for (Node n : maze.getNodes()) {
					int r = n.x;
					double t = 2*Math.PI / (4+4*n.x) * n.y;
					
					double x = (r * Math.sin(t)) + 6;
					double y = (r * Math.cos(t)) + 6;
					/*
					g2.fillOval(
							(int) (resoWidth + x * resoWidth + resoWidth/2), 
							(int) (resoHeight + y * resoHeight + resoHeight/2), 
							resoWidth/4, 
							resoHeight/4);
					*/
					double tDeg = Math.toDegrees(t);
					t = (tDeg + 180) % 360;
					t = (t + 90) % 360;
					/*
					g2.drawString(
							""+Math.round(t), 
							(int) (resoWidth + x * resoWidth + resoWidth/2), 
							(int) (resoHeight + y * resoHeight + resoHeight/2));
					*/
					
					
					for(Node adj : n) {
						int r2 = adj.x;
						double t2 = 2*Math.PI / (4+4*adj.x) * adj.y;
						
						double x2 = (r2 * Math.sin(t2)) + 6;
						double y2 = (r2 * Math.cos(t2)) + 6;
						
						double t2Deg = Math.toDegrees(t2);
						t2 = (t2Deg + 180) % 360;
						t2 = (t2 + 90) % 360;
						
						Rectangle rec = new Rectangle();
						rec.setFrameFromDiagonal(
								resoWidth + Math.min(x, x2) * resoWidth + resoWidth/2, 
								resoHeight + Math.min(y, y2) * resoHeight + resoHeight/2, 
								resoWidth +Math.max(x, x2) * resoWidth + resoWidth/2, 
								resoHeight + Math.max(y, y2) * resoHeight + resoHeight/2);
						
						//g2.draw(rec);
						/*
						g2.drawArc(
								rec.x, 
								rec.y, 
								rec.width, 
								rec.height, 
								(int) t , 
								(int) (t-t2) );
						*/
						
						g2.drawLine(
							(int) (resoWidth + x * resoWidth + resoWidth/2), 
							(int) (resoHeight + y * resoHeight + resoHeight/2), 
							(int) (resoWidth + x2 * resoWidth + resoWidth/2), 
							(int) (resoHeight + y2 * resoHeight + resoHeight/2));
					}
	
				}

				
			} else {

				int resoHeight = viewHeight / (maze.getHeight());
				int resoWidth = viewWidth / (maze.getWidth());
				g2.setStroke(new BasicStroke(Math.max(resoHeight, resoWidth)/2));

				// Draw the connections between nodes
				g2.setColor(Color.WHITE);
				for (Node n : maze.getNodes()) {
					for(Node adj : n) {
						g2.drawLine(
							resoWidth + n.x * resoWidth + resoWidth/2, 
							resoHeight + n.y * resoHeight + resoHeight/2, 
							resoWidth + adj.x * resoWidth + resoWidth/2, 
							resoHeight + adj.y * resoHeight + resoHeight/2);
					}
				}
				
				// If a solution path exists, draw it
				g2.setColor(Color.RED);
				if(path != null) {
					int i = 0;
					for(Node n : path) {
						// Start out with red and the closer we get to the end, the more green
						g2.setColor(getPathColor(i / (float) path.length()));
						if(i > 0) {
							Node prev = path.getNodes().get(i-1);
							g2.drawLine(resoWidth + n.x * resoWidth + resoWidth/2,
										resoHeight + n.y * resoHeight + resoHeight/2, 
										resoWidth + prev.x * resoWidth + resoWidth/2,
										resoHeight + prev.y * resoHeight + resoHeight/2);
						}
						i++;
					}
				}
				
				// Print node coordinates, useful for debugging
				if(DEBUG) {
					g2.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
					g2.setColor(Color.BLUE);
					for (Node n : maze.getNodes()) {
						g2.drawString(
								n.x + ", " + n.y, 
								resoWidth + n.x * resoWidth + resoWidth/2, 
								resoHeight + n.y * resoHeight + resoHeight/2);
					}
				}
			}
		}
	}

	/**
	 * Returns a color between red and green
	 * @param value
	 * @return
	 */
	private static Color getPathColor(float frac){
		return Color.getHSBColor(frac/3f, 1f, 1f);
	}
}
