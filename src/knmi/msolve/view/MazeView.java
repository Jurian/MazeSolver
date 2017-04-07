package knmi.msolve.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javafx.geometry.Dimension2D;

import javax.swing.JPanel;

import knmi.msolve.model.maze.Maze;
import knmi.msolve.model.maze.Node;
import knmi.msolve.model.maze.Path;

public class MazeView extends JPanel {

	public static boolean DEBUG = false;
	private static final long serialVersionUID = 1L;

	private Maze maze;
	private Path path;
	
	public void setMaze(Maze maze) {
		this.maze = maze;
		
		int max = Math.max(maze.getWidth(), maze.getHeight());
		int min = Math.min(maze.getWidth(), maze.getHeight());

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
		
		if(maze != null) {
			int viewHeight = this.getHeight();
			int viewWidth = this.getWidth();
			
			int resoHeight = viewHeight / (maze.getHeight()+2);
			int resoWidth = viewWidth / (maze.getWidth()+2);
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, viewWidth, viewHeight);
			
			Graphics2D g2 = (Graphics2D) g;
		    RenderingHints rh = new RenderingHints(
		             RenderingHints.KEY_TEXT_ANTIALIASING,
		             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		    g2.setRenderingHints(rh);
		    

			g2.setStroke(new BasicStroke(Math.max(resoHeight, resoWidth)/2));
			g2.setColor(Color.WHITE);
			for (Node n : maze.getNodes()) {
				for(Node adj : n) {
					g2.drawLine(
						resoWidth + n.x * resoWidth + resoWidth/2, resoHeight + n.y * resoHeight + resoHeight/2, 
						resoWidth + adj.x * resoWidth + resoWidth/2, resoHeight + adj.y * resoHeight + resoHeight/2);
				}
			}
			
			
			g2.setColor(Color.RED);
			if(path != null) {
				int i = 0;
				
				for(Node n : path) {
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
			
			if(DEBUG) {
				g2.setFont(new Font("TimesRoman", Font.PLAIN, 8)); 
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

	private static Color getPathColor(float value){
		return Color.getHSBColor(value/3f, 1f, 1f);
	}
}
