import java.util.*;
import java.awt.*;
/**
 * Models an indiviudal roomn in the graph.
 * @author Raj Kane
 * @version 07/28/18
 */

public class Vertex extends Agent implements Comparable<Vertex>{
	private HashMap<Direction,Vertex> map;
	private int cost;
	private boolean marked;
	private boolean visible;

	public Vertex(int x0, int y0, int cost, boolean marked){
		super(x0,y0);
		this.map = new HashMap<Direction,Vertex>();
		this.cost = cost;
		this.marked = marked;
		this.visible = false;
	}

	public Vertex(int x0, int y0, int cost){
		super(x0,y0);
		this.map = new HashMap<Direction,Vertex>();
		this.cost = cost;
		this.marked = false;
		this.visible = false;
	}

	public Vertex(int x0, int y0){
		super(x0,y0);
		this.map = new HashMap<Direction,Vertex>();
		this.marked = false;
		this.visible = false;
	}


	public int getCost(){
		return this.cost;
	}

	public void setCost(int c){
		this.cost = c;
	}

	public boolean isMarked(){
		return this.marked;
	}

	public void mark(boolean m){
		this.marked = m;
	}

	public boolean isVisible(){
		return this.visible;
	}

	public void setVisible(boolean v){
		this.visible=v;
	}

	public static Direction opposite(Direction d){
		if (d == Direction.North){
			return Direction.South;
		}
		else if (d == Direction.South){
			return Direction.North;
		}
		else if (d == Direction.West){
			return Direction.East;
		}
		else{
			return Direction.West;
		}
	}

	/**
	 * Connects the current vertex to another specified vertex in the specified
	 * direction.
	 */
	public void connect(Vertex other, Direction dir){
		this.map.put(dir, other);
	}

	/**
	 * Returns the current vertex's neighbor in a specified direction.
	 */
	public Vertex getNeighbor(Direction dir){
		return this.map.get(dir);
	}

	/**
	 * Returns all the neighbors of the current vertex.
	 */
	public Collection<Vertex> getNeighbors(){
		return this.map.values();
	}

	public int compareTo(Vertex v2){
		if (this.getCost() == v2.getCost()){
			return 0;
		}
		else if (this.getCost() < v2.getCost()){
			return -1;
		}
		else{
			return 1;
		}
	}

	public void updateState(Landscape scape){;}

	@Override
	public String toString(){
		String str = "(x,y) = (" + getX() + " , " + getY()+")";
		return str;
	}

	public void draw(Graphics g, int x0, int y0, int scale) {
		if (!this.visible) {
			return;
		}
		int xpos = x0 + this.x * scale;
		int ypos = y0 + this.y * scale;
		int border = 2;
		int half = 64 / 2;
		int eighth = 64 / 8;
		int sixteenth = 64 / 16;
		if (this.cost <= 2)				// set border color to red if the wumpus is nearby
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawRect(xpos + border, ypos + border, 64 - 2*border, 64 - 2 * border);
		g.setColor(Color.black);
		if (this.map.containsKey(Direction.North))
			g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
		if (this.map.containsKey(Direction.South))
			g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth),
				  eighth, eighth + sixteenth);
		if (this.map.containsKey(Direction.West))
			g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
		if (this.map.containsKey(Direction.East))
			g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth,
				  eighth + sixteenth, eighth);
	}
}

enum Direction{North, South, East, West}
