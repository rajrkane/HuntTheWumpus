import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
/**
 * Models the player, aka the hunter.
 * @author Raj Kane
 * @version 07/28/18
 */

public class Hunter extends Agent{
	private Vertex location;
	private boolean armed;

	public Hunter(Vertex location){
		super(location.getX(), location.getY());
		this.location = location;
		this.location.setVisible(true);
		this.armed = false;
	}

	/**
	 * Sets the location of the hunter.
	 */
	public void setLocation(Vertex v){
		this.location = v;
		this.setX(v.getX()); this.setY(v.getY());
		this.location.setVisible(true);
	}

	public Vertex getLocation(){
		return this.location;
	}

	/**
	 * Sets the armed status of the hunter.
	 */
	public void setArmed(boolean armed){
		this.armed = armed;
	}

	public boolean isArmed(){
		return this.armed;
	}

	@Override
	public void updateState(Landscape scape){;}

	/**
	 * Draws the hunter as a green circle
	 */
	public void draw(Graphics g, int x0, int y0, int scale){
		g.setColor(Color.green);
		g.fillOval(x0 + this.getX() * scale, y0 + this.getY() * scale, 20, 20);
	}
}
