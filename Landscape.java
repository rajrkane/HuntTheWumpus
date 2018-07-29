import java.util.*;
import java.awt.Graphics;
/**
 * Sets up the landscape
 * @author Raj Kane
 * @version 07/28/18
 */

public class Landscape{
	private LinkedList<Agent> coords;
	private LinkedList<Agent> forelist;
	private LinkedList<Agent> backlist;
	private Graph graph;
	private int height;
	private int width;

	public Landscape(int height, int width){
		this.coords = new LinkedList<Agent>();
		this.height = height;
		this. width = width;
	}

	public void draw(Graphics g){
		for (Agent b: this.backlist){
			b.draw(g, 1);
		}
		for (Agent f: this.forelist){
			f.draw(g, 1);
		}
	}

	/**
	 * Add an agent to the linked list.
	 */
	public void add(Agent a){this.coords.addFirst(a);}

	public void clear(){
		this.forelist.clear();
		this.backlist.clear();
	}

	public int getHeight(){
		return this.height;
	}
	public int getWidth(){
		return this.width;
	}

	public ArrayList<Agent> getAgents(){
		ArrayList<Agent> list = new ArrayList<Agent>();
		for (Agent a:this.coords){
			list.add(a);
		}
		return list;
	}

	public void advance(){;}
}
