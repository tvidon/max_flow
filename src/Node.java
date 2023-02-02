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
     * Number of nodes
     */
    private static int count;
    /**
     * Index in the adjacency matrix
     */
    private int index;


    /**
     * Default constructor
     */
    public Node(){
        index = count++;
    };

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
     * Resets the node counter
     * Use when creating a new graph
     */
    public static void resetCount(){
        count = 0;
    }
    /**
     * Getter for the index of this node in the adjacency matrix
     * @return index of this node in the adjacency matrix (int)
     */
    public int getIndex(){
        return index;
    }
}
