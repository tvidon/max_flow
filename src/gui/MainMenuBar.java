package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

/**
 * Class for the main menu bar of the app
 */
class MainMenuBar extends JMenuBar implements ActionListener{

    /**
     * main frame
     */
    private MainFrame mainFrame;

    /**
     * load option in the menu
     */
    private JMenuItem loadItem;
    /**
     * save option in the menu
     */
    private JMenuItem saveItem;
    /**
     * exit option in the menu
     */
    private JMenuItem exitItem;
    /**
     * find max flow option in the menu
     */
    private JMenuItem findMaxFlowItem;

    /**
     * Constructor for the main menu bar of the app
     */
    MainMenuBar(){

        // File menu
        
        JMenu fileMenu = new JMenu("File");

        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        this.add(fileMenu);

        // Graph menu
        
        JMenu graphMenu = new JMenu("Graph");

        findMaxFlowItem = new JMenuItem("Find max flow");

        findMaxFlowItem.addActionListener(this);

        graphMenu.add(findMaxFlowItem);

        this.add(graphMenu);
    }

    /**
     * Find the parent of this bar (the main frame) and store it in mainFrame, called by the main frame once the bar is loaded
     */
    void findParent(){
        mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
    }

    /**
     * Call the functions related to each option in the bar
     */
    @Override
    public void actionPerformed(ActionEvent e){
        // File menu
        if(e.getSource() == loadItem){
            mainFrame.load();
        }
        if(e.getSource() == saveItem){
            mainFrame.save();
        }
        if(e.getSource() == exitItem){
            System.exit(0);
        }
        // Graph menu
        if(e.getSource() == findMaxFlowItem){
            mainFrame.findMaxFlow();
        }
    }
}
