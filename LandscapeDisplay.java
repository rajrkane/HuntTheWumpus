import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;
/**
 * Displays a Landscape graphically using Swing.  The Landscape
 * can be displayed at any scale factor.
 * @author bseastwo
 */
public class LandscapeDisplay extends JFrame
{
    protected Landscape scape;
    private LandscapePanel canvas;
    private int scale;

    /**
     * Initializes a display window for a Landscape.
     * @param scape the Landscape to display
     * @param scale controls the relative size of the display
     */
    public LandscapeDisplay(Landscape scape, int scale)
    {
        // setup the window
        super("Hunt The Wumpus!!!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.scape = scape; this.scale = scale;

        // create a panel in which to display the Landscape
        this.canvas = new LandscapePanel( (int) this.scape.getWidth(),
                                        (int) this.scape.getHeight());

        // add the panel to the window, layout, and display
        this.add(this.canvas, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Saves an image of the display contents to a file.  The supplied
     * filename should have an extension supported by javax.imageio, e.g.
     * "png" or "jpg".
     *
     * @param filename  the name of the file to save
     */
    public void saveImage(String filename)
    {
        // get the file extension from the filename
        String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());

        // create an image buffer to save this component
        Component tosave = this.getRootPane();
        BufferedImage image = new BufferedImage(tosave.getWidth(), tosave.getHeight(),
                                                BufferedImage.TYPE_INT_RGB);

        // paint the component to the image buffer
        Graphics g = image.createGraphics();
        tosave.paint(g);
        g.dispose();

        // save the image
        try
                {
                        ImageIO.write(image, ext, new File(filename));
                }
        catch (IOException ioe)
                {
                        System.out.println(ioe.getMessage());
                }
    }

    /**
     * This inner class provides the panel on which Landscape elements
     * are drawn.
     */
    private class LandscapePanel extends JPanel
    {
        /**
         * Creates the panel.
         * @param width     the width of the panel in pixels
         * @param height        the height of the panel in pixels
         */
        public LandscapePanel(int width, int height)
        {
                super();
                this.setPreferredSize(new Dimension(width, height));
                this.setBackground(Color.white);
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen.  The supplied Graphics
         * object is used to draw.
         *
         * @param g     the Graphics object used for drawing
         */
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            ArrayList<Agent> agents = scape.getAgents();
            for(Agent a:agents){
                a.draw(g,0,0,scale);
            }
        } 
    }

    public void setScape(Landscape scape){
      this.scape = scape;
    }

    public void update() {
        Graphics g = canvas.getGraphics();
        this.requestFocus();
        canvas.paintComponent( g );
    }


    public static void main(String[] args) throws InterruptedException {
        Landscape scape = new Landscape(400,400);
        Random rand = new Random();
        Vertex v1 = new Vertex(50,20);
        Vertex v2 = new Vertex(50,50);
        Vertex v3 = new Vertex(80,20);
        Vertex v4 = new Vertex(110,20);
        v1.setVisible(true); v2.setVisible(true);v3.setVisible(true);
        v4.setVisible(true);
        Graph graph = new Graph();
        graph.addEdge(v1, Direction.South, v2);
        graph.addEdge(v1, Direction.East, v3);
        graph.addEdge(v3,Direction.East,v4);
        Hunter hunter = new Hunter(v1);
        Wumpus wumpus = new Wumpus(v2);
        scape.add(v1);
        scape.add(v2); scape.add(v3); scape.add(v4);
        scape.add(hunter);
        scape.add(wumpus);
       LandscapeDisplay display = new LandscapeDisplay(scape, 2);
        for (int i=0;i<100;i++){
            scape.advance();
            display.update();
            Thread.sleep(200);
        }
    }
}
