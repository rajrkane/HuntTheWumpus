import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Point;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;
import java.util.*;
import javax.swing.JOptionPane;
/**
 * Top level class. Handles the game logic.
 * @author Raj Kane
 * @version 07/28/18
 */

public class HuntTheWumpus extends JFrame{
	private int height=800;
	private int width=800;
	private int scale = 64;

	/**
	 * The 4 options for the play state machine.
	 */
	public enum PlayState{PLAY, SHOOT, RESET, STOP}
	private PlayState state;

	JButton reset;
	JLabel fieldX; JLabel fieldY;
	Point curPoint;
	Color curColor;
	Color prevColor;

	private Landscape scape;
	private LandscapeDisplay display;
	private Graph graph;
	private Hunter hunter;
	private Wumpus wumpus;

	/**
	 * The main function. Builds the graph. Inserts the vertices, hunter, and
	 * wumpus into the landscape. Incorporates user interface elements. Adds
	 * a key listener and an action listener.
	 */
	public HuntTheWumpus(){
		this.scape = new Landscape(height, width);
		if (display != null){display.dispose();}
		display = new LandscapeDisplay(this.scape, this.scale);
		graph = new Graph();
		graph.generateGraph(scape);
		ArrayList<Vertex> vertexList = graph.getGraph();
		Random rand = new Random();						// Wumpus is added randomly.
		Vertex wumpLocation = vertexList.get(rand.nextInt(vertexList.size()));
		this.wumpus = new Wumpus(wumpLocation);
		this.scape.add(this.wumpus);
		graph.shortestPath(wumpLocation);
		Vertex huntLocation = vertexList.get(rand.nextInt(vertexList.size()));
		this.hunter = new Hunter(huntLocation);
		scape.add(this.hunter);
		this.setupUI();
		state = PlayState.PLAY;
	}

	/**
	 * Sets up the user interface.
	 */
	private void setupUI(){
		JButton quit = new JButton("Quit");
		reset = new JButton("Reset");
		reset.setFocusable(false); quit.setFocusable(false);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(reset); panel.add(quit);
		this.display.add(panel,BorderLayout.SOUTH);
		this.display.pack();
		Control control = new Control();
		this.display.addKeyListener(control);
		reset.addActionListener(control);
		quit.addActionListener(control);
		this.display.setFocusable(true);
		this.display.requestFocus();
		this.curColor = Color.blue; this.prevColor = Color.blue;
		this.curPoint = new Point(this.width/2, this.height/2);
	}

	/*
	 * Handles keyboard and button input.
	 */
	private class Control extends KeyAdapter implements ActionListener{
		public void keyTyped(KeyEvent e){
			if(("" + e.getKeyChar()).equalsIgnoreCase("w")){
				if(state ==PlayState.PLAY){
					if(hunter.getLocation().getNeighbor(Direction.North)!=null){			// move up if possible
						hunter.setLocation(hunter.getLocation().getNeighbor(Direction.North));
					}
				}
				else if(state ==PlayState.SHOOT){				// shoot if possible
					if(hunter.getLocation().getNeighbor(Direction.North)==wumpus.getLocation()){
						wumpus.setAlive(false);				// wumpus killed
						wumpus.setVisible(true);
						int response = JOptionPane.showConfirmDialog(display,"You win! Play again?");
						if (response == 0 || response == 2){
							state=PlayState.RESET;
						}
						else{
							state=PlayState.STOP;
						}
					}
					else{
						wumpus.setAlive(true);				// hunter missed
						wumpus.setVisible(true);
						int response = JOptionPane.showConfirmDialog(display, "You lost. Play again?");
						if (response == 0 ||response == 2){
							state=PlayState.RESET;
						}
						else{
							state=PlayState.STOP;
						}
					}
				}
			}
			else if(("" + e.getKeyChar()).equalsIgnoreCase("a")){
				if(state==PlayState.PLAY){
					if(hunter.getLocation().getNeighbor(Direction.West)!=null){				// move left if possible
						hunter.setLocation(hunter.getLocation().getNeighbor(Direction.West));
					}
				}
				else if(state ==PlayState.SHOOT){				// shoot if possible
					if(hunter.getLocation().getNeighbor(Direction.West)==wumpus.getLocation()){
						wumpus.setAlive(false);				// wumpus killed
						wumpus.setVisible(true);
						int response = JOptionPane.showConfirmDialog(display,"You win! Play again?");
						if (response==0||response==2){state=PlayState.RESET;}
						else{state=PlayState.STOP;}
					}
					else{
						wumpus.setAlive(true);				// hunter missed
						wumpus.setVisible(true);
						int response = JOptionPane.showConfirmDialog(display, "You lost. Play again?");
						if (response==0||response==2){state=PlayState.RESET;}
						else{state=PlayState.STOP;}
					}
				}
			}
			else if(("" + e.getKeyChar()).equalsIgnoreCase("s")){
				if(state==PlayState.PLAY){
					if(hunter.getLocation().getNeighbor(Direction.South)!=null){				// move down if possible
						hunter.setLocation(hunter.getLocation().getNeighbor(Direction.South));
					}
				}
				else if(state ==PlayState.SHOOT){				// shoot if possible
					if(hunter.getLocation().getNeighbor(Direction.South)==wumpus.getLocation()){
						wumpus.setAlive(false);				// wumpus killed
						wumpus.setVisible(true);
						int response = JOptionPane.showConfirmDialog(display,"You win! Play again?");
						if (response==0||response==2){state=PlayState.RESET;}
						else{state =PlayState.STOP;}
					}
					else{
						wumpus.setAlive(true);				// hunter missed
						wumpus.setVisible(true);
						int response = JOptionPane.showConfirmDialog(display, "You lost. Play again?");
						if (response==0||response==2){state=PlayState.RESET;}
						else{state=PlayState.STOP;}
					}
				}
			}
			else if(("" + e.getKeyChar()).equalsIgnoreCase("d")){
				if(state==PlayState.PLAY){
					if(hunter.getLocation().getNeighbor(Direction.East)!=null){				// move right if possible
						hunter.setLocation(hunter.getLocation().getNeighbor(Direction.East));
					}
				}
				else if(state ==PlayState.SHOOT){				// shoot if possible
					if(hunter.getLocation().getNeighbor(Direction.East)==wumpus.getLocation()){
						wumpus.setAlive(false);				// wumpus killed
						wumpus.setVisible(true);
						int response = JOptionPane.showConfirmDialog(display,"You win! Play again?");
						if (response==0||response==2){state=PlayState.RESET;}
						else{state =PlayState.STOP;}
					}
					else{
						wumpus.setAlive(true);				// hunter missed
						wumpus.setVisible(true);
						int response = JOptionPane.showConfirmDialog(display, "You lost. Play again?");
						if (response==0||response==2){state=PlayState.RESET;}
						else{state=PlayState.STOP;}
					}
				}
			}

			else if(("" + e.getKeyChar()).equalsIgnoreCase("f")){
				if(state==PlayState.PLAY){
					state = PlayState.SHOOT;
					hunter.setArmed(true);
				}
				else if(state==PlayState.SHOOT){
					state = PlayState.PLAY;
					hunter.setArmed(false);
				}
			}
		}

		/**
		 * Handles quit and reset buttons
		 */
		public void actionPerformed(ActionEvent e){
			if(e.getActionCommand().equalsIgnoreCase("Quit")){
				state = PlayState.STOP;
			}
			else if (e.getActionCommand().equalsIgnoreCase("Reset")){
				state = PlayState.RESET;
			}
		}
	}

	/**
	 * The state machine.
	 */
	public void simulate(){
		if(state==PlayState.PLAY){
			if(hunter.getLocation()==wumpus.getLocation()){
				wumpus.setAlive(true); wumpus.setVisible(true);
				int response = JOptionPane.showConfirmDialog(display, "You lost. Play again?");
				if(response==0||response==2){
					state=PlayState.RESET;
				}
				else{
					state=PlayState.STOP;
				}

			}
		}
		else if(state == PlayState.RESET){
			scape = new Landscape(height,width);
			display.setScape(scape);
			graph = new Graph();
			graph.generateGraph(scape);
			ArrayList<Vertex> vertexList = graph.getGraph();
			Random rand = new Random();
			Vertex wumpLocation = vertexList.get(rand.nextInt(vertexList.size()));
			this.wumpus = new Wumpus(wumpLocation);
			scape.add(this.wumpus);
			graph.shortestPath(wumpLocation);
			Vertex huntLocation = vertexList.get(rand.nextInt(vertexList.size()));
			while (huntLocation.getCost() <= 2){
				huntLocation = vertexList.get(rand.nextInt(vertexList.size()));
			}
			this.hunter = new Hunter(huntLocation);
			scape.add(this.hunter);
			display.repaint();
			state = PlayState.PLAY;
		}
		display.update();
		try{Thread.sleep(300);}
		catch(InterruptedException e){e.printStackTrace();}
	}


	// ...Here goes nothing...
	public static void main(String[] args) {
		HuntTheWumpus game = new HuntTheWumpus();
		while (game.state != PlayState.STOP){
			game.simulate();
		}
		game.display.dispose();
	}
}
