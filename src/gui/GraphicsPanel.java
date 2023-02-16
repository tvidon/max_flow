package gui;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.ArrayList;

/**
 * Class for the panel containing the graphical representation of the graph
 */
class GraphicsPanel extends JPanel implements MouseListener{

    /**
     * Main frame
     */
    private MainFrame mainFrame;
    /**
     * Placement mode indicates whether the user should be able to click to place nodes
     */
    private boolean placementMode;
    /**
     * Counter for the number of placed nodes during placement mode
     */
    private int nodesPlaced;

    /**
     * Constructor for the graphics panel
     */
    GraphicsPanel(){
        this.setPreferredSize(new Dimension(1920, 1080));
        this.addMouseListener(this);
    }

    /**
     * Activate placement mode
     */
    void activatePlacementMode(){
        placementMode = true;
    }

    /**
     * Find the parent of this panel (the main frame) and store it in mainFrame, called by the main frame once the panel is loaded
     */
    void findParent(){
        mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
    }

    /**
     * Place node on mouse click
     * Enabled by the newGraph() method of MainFrame
     */
    public void mouseClicked(MouseEvent e){
        if (placementMode){
            mainFrame.getGraph().addNode();
            mainFrame.getGraph().getNodes().get(nodesPlaced).setX(e.getX());
            mainFrame.getGraph().getNodes().get(nodesPlaced).setY(e.getY());
            nodesPlaced++;
            this.repaint();
            if (nodesPlaced == mainFrame.getGraph().getSize()){
                placementMode = false;
                nodesPlaced = 0;
                mainFrame.startEdgeAdder();
            }
        }
    }
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}

    /**
     * Paint the graphics
     */
    public void paint(Graphics g){
        // reset the background
        super.paint(g);
        // get the graph
        Graph graph = mainFrame.getGraph();
        // draw the graph
        if (graph != null){
            for (Node n : graph.getNodes()){
                // nodes
                g.drawString(String.valueOf(n.getIndex()), n.getX() - 3, n.getY() + 5);
                g.drawOval(n.getX() - 10, n.getY() - 10, 20, 20);
                // edges
                ArrayList<Edge> edges = graph.getMat().get(n.getIndex());
                for (int i = 0; i < edges.size(); i++){
                    Edge e = edges.get(i);
                    if (e != null){
                        Node n2 = graph.getNodes().get(i);
                        // shorten the line so as not to be in the circles
                        float startX = (float) n.getX();
                        float startY = (float) n.getY();
                        float endX = (float) n2.getX();
                        float endY = (float) n2.getY();
                        float[] shortened = Tools.shorten(startX, startY, endX, endY, 10);
                        endX = shortened[0];
                        endY = shortened[1];
                        shortened = Tools.shorten(endX, endY, startX, startY, 10);
                        startX = shortened[0];
                        startY = shortened[1];
                        // draw the line
                        g.drawLine((int) startX, (int) startY, (int) endX, (int) endY);
                        // draw the arrow
                        float[] corners = Tools.arrow(startX, startY, endX, endY, 7, 6);
                        g.drawLine((int) corners[0], (int) corners[1], (int) endX, (int) endY);
                        g.drawLine((int) corners[2], (int) corners[3], (int) endX, (int) endY);
                        // draw the capacity
                        float length = (float) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
                        float[] fivePercent = Tools.shorten(startX, startY, endX, endY, (float) 0.95 * length);
                        g.drawString(e.getUsed() + "/" + e.getMax(), (int) fivePercent[0], (int) fivePercent[1]);
                    }
                }
            }
        }
    }
}
