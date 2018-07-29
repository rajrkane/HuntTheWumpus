import java.awt.Graphics;
/**
 * Models an individual agent.
 * @author Raj Kane
 * @version 07/28/18
 */

public class Agent{
	public int x;
	public int y;

	public Agent(int x0, int y0){
		this.x = x0;
		this.y = y0;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public void setX(int newX){
		this.x = newX;
	}

	public void setY(int newY){
		this.y = newY;
	}

	public String toString(){
		String str = "(x,y) = (" + getX() + " , " + getY()+")";
		return str;
	}

	public void updateState(Landscape scape){;}

	public void draw(Graphics g, int x, int y, int scale){;}
	
	public void draw(Graphics g, int scale){;}
}
