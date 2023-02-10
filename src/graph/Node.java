package graph;

/**
 * Class representing a node in a graph for the Ford-Fulkerson algorithm
 */
public class Node{
    
    /**
     * Node that this was marked from during the Ford-Fulkerson algorithm
     */
    private Node from;
    /**
     * Direction that the node was marked from during the Ford-Fulkerson algorithm
     * true if it was marked as a successor
     * false if it was marked as a predecessor
     */
    private boolean successor;
    /**
     * Index in the adjacency matrix
     */
    private int index;
    /**
     * x in the gui
     */
    private int x;
    /**
     * y in the gui
     */
    private int y;


    /**
     * Constructor
     * @param index index of the node in the adjacency matrix
     */
    public Node(int index){
        this.index = index;
    };

    /**
     * Getter for x in the gui
     * @return the x
     */
    public int getX(){
        return x;
    }
    /**
     * Getter for y in the gui
     * @return the y
     */
    public int getY(){
        return y;
    }
    /**
     * Setter for x in the gui
     * @param x x in the gui
     */
    public void setX(int x){
        this.x = x;
    }
    /**
     * Setter for y in the gui
     * @param y y in the gui
     */
    public void setY(int y){
        this.y = y;
    }
    /**
     * Setter for source node for marking during the Ford-Fulkerson algorithm
     * @param node source node
     */
    public void setFrom(Node node){
        this.from = node;
    }
    /**
     * Getter for source node for marking during the Ford-Fulkerson algorithm
     * @return node from which this node was marked
     */
    public Node getFrom(){
        return from;
    }
    /**
     * Setter for the direction that the node was marked from
     * @param successor true if it was marked as a successor, false if it was marked as a predecessor
     */
    public void setSuccessor(boolean successor){
        this.successor = successor;
    }
    /**
     * Getter for the direction that the node was marked form
     * @return true if it was marked as a successor, false if it was marked as a predecessor
     */
    public boolean getSuccessor(){
        return successor;
    }
    /**
     * Getter for the index of this node in the adjacency matrix
     * @return index of this node in the adjacency matrix
     */
    public int getIndex(){
        return index;
    }
}
