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
     * Constructor for the main frame of the app
     */
    public MainFrame(){
        
        // base
        this.setTitle("max_flow");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1920, 1080);
        this.setVisible(true);

        // menu bar
        MainMenuBar mainMenuBar = new MainMenuBar();
        this.setJMenuBar(mainMenuBar);
        mainMenuBar.findParent();

    }

    // File
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
                    source = input;
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "This node does not exist, please pick a number between 0 and " + maxIndex);
                }
            }
            int sink = -1;
            while (sink == -1){
                try{
                    int input = Integer.parseInt(JOptionPane.showInputDialog(this, "Pick the sink node", maxIndex));
                    if (input < 0 || input > maxIndex){
                        throw new NumberFormatException();
                    }
                    sink = input;
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "This node does not exist, please pick a number between 0 and " + maxIndex);
                }
            }
            graph.ff(source, sink);
        }
        else {
            JOptionPane.showMessageDialog(this, "No graph currently loaded, load one from the File menu");
        }
    }
}
