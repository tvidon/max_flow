package gui;

import graph.Graph;

import java.awt.FileDialog;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class for the main frame of the app
 */
public class MainFrame extends JFrame{

    /**
     * Loaded graph
     */
    private Graph graph;
    /**
     * Panel containing the graphical representation of the graph
     */
    private GraphicsPanel graphicsPanel;

    /**
     * Constructor for the main frame of the app
     */
    public MainFrame(){
        
        // base
        this.setTitle("max_flow");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // menu bar
        MainMenuBar mainMenuBar = new MainMenuBar();
        this.setJMenuBar(mainMenuBar);
        mainMenuBar.findParent();

        // graphics panel
        graphicsPanel = new GraphicsPanel();
        this.add(graphicsPanel);
        graphicsPanel.findParent();

        // display
        this.pack();
        this.setVisible(true);

    }

    /**
     * Getter for the graph
     */
    Graph getGraph(){
        return graph;
    }

    /**
     * Start the edge adder dialogue
     * Called during the process of creating a new graph (newGraph() method of this class)
     */
    void startEdgeAdder(){
        int numberOfEdges = -1;
        while (numberOfEdges < 0){
            try{
                int input = Integer.parseInt(JOptionPane.showInputDialog(this, "Number of edges"));
                if (input < 0){
                    throw new IllegalArgumentException();
                }
                numberOfEdges = input;
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Pick a number");
            }
            catch (IllegalArgumentException e){
                JOptionPane.showMessageDialog(this, "Pick a number greater than 0");
            }
        }
        int maxIndex = graph.getSize() - 1;
        for (int i = 0; i < numberOfEdges; i++){
            boolean alreadyExists = true;
            int startNodeIndex = -1;
            int endNodeIndex = -1;
            while (alreadyExists){
                while (startNodeIndex == -1){
                    try{
                        int input = Integer.parseInt(JOptionPane.showInputDialog(this, "Edge " + (i + 1) + " out of " + numberOfEdges + "\nPick the start node"));
                        if (input < 0 || input > maxIndex){
                            throw new NumberFormatException();
                        }
                        startNodeIndex = input;
                    }
                    catch (NumberFormatException e){
                        JOptionPane.showMessageDialog(this, "This node does not exist, please pick a number between 0 and " + maxIndex);
                    }
                }
                while (endNodeIndex == -1){
                    try{
                        int input = Integer.parseInt(JOptionPane.showInputDialog(this, "Edge " + (i + 1) + " out of " + numberOfEdges + "\nPick the end node"));
                        if (input < 0 || input > maxIndex){
                            throw new NumberFormatException();
                        }
                        endNodeIndex = input;
                    }
                    catch (NumberFormatException e){
                        JOptionPane.showMessageDialog(this, "This node does not exist, please pick a number between 0 and " + maxIndex);
                    }
                }
                if (graph.getMat().get(startNodeIndex).get(endNodeIndex) == null){
                    alreadyExists = false;
                }
                else{
                    JOptionPane.showMessageDialog(this, "This edge already exists");
                    startNodeIndex = -1;
                    endNodeIndex = -1;
                }
            }
            int capacity = -1;
            while (capacity == -1){
                try{
                    int input = Integer.parseInt(JOptionPane.showInputDialog(this, "Edge " + (i + 1) + " out of " + numberOfEdges + "\nChoose a capacity"));
                    if (input < 1){
                        throw new NumberFormatException();
                    }
                    capacity = input;
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "Capacity must be a number greater than or equal to 1");
                }
            }
            graph.addEdge(startNodeIndex, endNodeIndex, capacity);
            graphicsPanel.repaint();
        }
    }

    // File
    /**
     * Create a new graph
     */
    void newGraph(){
        // asks how many nodes the graph will have
        // ends and lets graphics panel handle the placement with mouse events
        // the graphics panel will call the method to handle edge creation
        int numberOfNodes = 0;
        while(numberOfNodes < 2){
            try{
                int input = Integer.parseInt(JOptionPane.showInputDialog(this, "Number of nodes"));
                if (input < 2){
                    throw new IllegalArgumentException();
                }
                numberOfNodes = input;
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Pick a number");
            }
            catch (IllegalArgumentException e){
                JOptionPane.showMessageDialog(this, "Pick a number greater than or equal to 2");
            }
        }
        graph = new Graph(numberOfNodes);
        JOptionPane.showMessageDialog(this, "Click to place nodes");
        graphicsPanel.repaint();
        graphicsPanel.activatePlacementMode();
    }
    /**
     * Save the loaded graph to a file using a FileDialog
     */
    void save(){
        if (graph != null){
            FileDialog fileDialog = new FileDialog(this, null, FileDialog.SAVE);
            fileDialog.setVisible(true);
            String file = fileDialog.getFile();
            if (file != null){
                file = fileDialog.getDirectory() + file;
                try{
                    graph.save(file);
                }
                catch (IOException e){
                    JOptionPane.showMessageDialog(this, "Error, could not save to " + file);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "No graph currently loaded, load one from the File menu");
        }
    }
    /**
     * Load a graph from a file using a FileDialog
     */
    void load(){
        FileDialog fileDialog = new FileDialog(this, null, FileDialog.LOAD);
        fileDialog.setVisible(true);
        String file = fileDialog.getFile();
        if (file != null){
            file = fileDialog.getDirectory() + file;
            try{
                graph = new Graph(file);
                graphicsPanel.repaint();
            }
            catch (IOException e){
                JOptionPane.showMessageDialog(this, "Error, could not load " + file);
            }
            catch (IllegalArgumentException e){
                JOptionPane.showMessageDialog(this, file + " is not a valid file");
            }
        }
    }
    // Graph
    /**
     * Pick the source and sink nodes with a GUI and find the max flow for the loaded graph
     */
    void findMaxFlow(){
        if (graph != null){
            int maxIndex = graph.getSize() - 1;
            int source = -1;
            while (source == -1){
                try{
                    int input = Integer.parseInt(JOptionPane.showInputDialog(this, "Pick the source node", 0));
                    if (input < 0 || input > maxIndex){
                        throw new NumberFormatException();
                    }
                    for (int i = 0; i < graph.getSize(); i++){
                        if (graph.getMat().get(i).get(input) != null) {
                            throw new IllegalArgumentException();
                        }
                    }
                    source = input;
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "This node does not exist, please pick a number between 0 and " + maxIndex);
                }
                catch (IllegalArgumentException e){
                    JOptionPane.showMessageDialog(this, "Source node must have no edges pointing towards it");
                }
            }
            int sink = -1;
            while (sink == -1){
                try{
                    int input = Integer.parseInt(JOptionPane.showInputDialog(this, "Pick the sink node", maxIndex));
                    if (input < 0 || input > maxIndex){
                        throw new NumberFormatException();
                    }
                    for (Object edge : graph.getMat().get(input)){
                        if (edge != null){
                            throw new IllegalArgumentException();
                        }
                    }
                    sink = input;
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "This node does not exist, please pick a number between 0 and " + maxIndex);
                }
                catch (IllegalArgumentException e){
                    JOptionPane.showMessageDialog(this, "Sink node must have no edges coming out of it");
                }
            }
            graph.ff(source, sink);
            graphicsPanel.repaint();
        }
        else {
            JOptionPane.showMessageDialog(this, "No graph currently loaded, load one from the File menu");
        }
    }
}
