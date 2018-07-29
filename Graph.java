import java.util.*;
import java.util.Comparator;
/**
 * @author Raj Kane
 * @version 07/28/18
 */

public class Graph{
	private ArrayList<Vertex> graph;

	public Graph(){
		this.graph = new ArrayList<Vertex>();
	}

	public int vertexCount(){
		return this.graph.size();
	}

	/**
	 * Adds two vertices in the specified direction, if they are not already added.
	 */
	public void addEdge(Vertex v1, Direction dir, Vertex v2){
		if (!this.graph.contains(v1)){
			this.graph.add(v1);
		}
		if (!this.graph.contains(v2)){
			this.graph.add(v2);
		}
		v1.connect(v2, dir);
		v2.connect(v1, v2.opposite(dir));
	}

	/**
	 * Uses Dijsktra's algorithm to find the least cost of getting to the specified
	 * vertex.
	 */
	public void shortestPath(Vertex v0){
		for (Vertex vertex: this.graph){
			vertex.mark(false);
			vertex.setCost(Integer.MAX_VALUE);
		}
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>(this.graph.size(), new GraphComparator());
		v0.setCost(0);
		q.offer(v0);
		while (q.peek() != null){
			Vertex v = q.poll();
			v.mark(true);
			for (Vertex w: v.getNeighbors()){
				if(!w.isMarked() && v.getCost() + 1 < w.getCost()){
					w.setCost(v.getCost() + 1);
					q.add(w);
				}
			}
		}
	}

	/**
	 * Sets the graph as a specified arraylist of vertices
	 */
	public void setGraph(ArrayList<Vertex> V){
		this.graph = V;
	}

	public ArrayList<Vertex> getGraph(){
		return this.graph;
	}

	/**
	 * Generates the graph according a specified landscape. Edges are added
	 * quasi-randomly.
	 */
	public void generateGraph(Landscape scape){
		this.graph = new ArrayList<Vertex>();
		Random rand = new Random();
		Vertex[][] array = new Vertex[8][8];
		for (int i = 0; i<8; i++){
			for (int j=0; j<8; j++){
				array[i][j] = new Vertex(i,j);}}
		for (int i = 0; i<8; i++){
			for (int j=0; j<8; j++){
				boolean bool = true;
				if (j<7 && rand.nextInt(4)<3){
					bool = false;
					array[i][j].connect(array[i][j+1], Direction.South);
					array[i][j+1].connect(array[i][j], Direction.North);
				}
				if (i<7 && (rand.nextInt(4)<3 || bool)){
					array[i][j].connect(array[i+1][j], Direction.East);
					array[i+1][j].connect(array[i][j], Direction.West);
				}
				graph.add(array[i][j]);
			}
		}
		for (Vertex v: graph){
			scape.add(v);							//all vertices from are added to background
		}
	}

	public static void main(String[] args) {
		;
	}
}

class GraphComparator implements Comparator<Vertex>{
	public GraphComparator(){;}
	public int compare(Vertex v, Vertex w){
		if (v.getCost() == w.getCost()){
			return 0;
		}
		else if (v.getCost()<w.getCost()){
			return -1;
		}
		else{
			return 1;
		}
	}
}
