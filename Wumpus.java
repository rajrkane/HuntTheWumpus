import java.awt.*;
/**
 * Models the wumpus.
 * @author Raj Kane
 * @version 07/28/18
 */

public class Wumpus extends Agent{
	private Vertex location;
	private boolean visible;
	private boolean alive;

	public Wumpus(Vertex location){
		super(location.getX(), location.getY());
		this.location = location;
		this.visible = false;
		this.alive = true;
	}

	public Vertex getLocation(){
		return this.location;
	}

	public void setLocation(Vertex v){
		this.location = v;
		this.setX(v.getX());
		this.setY(v.getY());
	}

	public boolean isAlive(){
		return this.alive;
	}
	
	/**
	 * Sets the living status of the wumpus.
	 */
	public void setAlive(boolean alive){
		this.alive = alive;
	}

	public boolean isVisible(){
		return this.visible;
	}

	/**
	 * Sets the visibility status of the wumpus.
	 */
	public void setVisible(boolean visible){
		this.visible = visible;
	}

	public void updateState(Landscape scape){;}

	/**
	 * Draws the wumpus if it is visible.
	 */
	public void draw(Graphics g, int x0, int y0, int scale){
		if(!this.visible){
			return;
		}
		g.setColor(Color.cyan);
		g.fillOval(x0 + this.getX() * scale, y0 + this.getY() * scale, 20, 20);
	}
}
