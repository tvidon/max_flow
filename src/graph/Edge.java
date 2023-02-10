package graph;

/**
 * Class representing an edge in a directional graph for the Ford-Fulkerson algorithm
 * Does not store the start and end nodes as this info is already in the adjacency matrix
 */
public class Edge{
    
    /**
     * Maximum capacity of the edge
     * Should be bigger than 0
     */
    private int max;
    /**
     * Currently used capacity
     * Should be between 0 and max
     */
    private int used;

    /**
     * Default constructor
     */
    public Edge(){
        max = 1;
    };
    /**
     * Contructor for when the max capacity is already known
     * @param max maximum capacity of the edge
     */
    public Edge(int max){
        if (max > 0){
            this.max = max;
        }
        else {
            throw new IllegalArgumentException("max must be >0");
        }
    };

    /**
     * Setter for max capacity
     * @param max max capacity
     */
    public void setMax(int max){
        if (max > 0){
            this.max = max;
        }
        else {
            throw new IllegalArgumentException("max must be >0");
        }
    }
    /**
     * Getter for max capacity
     * @return max capacity
     */
    public int getMax(){
        return max;
    }
    /**
     * Setter for used capacity
     * @param used used capacity
     */
    public void setUsed(int used){
        if (0 <= used && used <= max){
            this.used = used;
        }
        else {
            throw new IllegalArgumentException("used must be >=0 and <=max");
        }
    }
    /**
     * Getter for used capacity
     * @return used capacity
     */
    public int getUsed(){
        return used;
    }

    /**
     * Finds and returns residual capacity (max-used)
     * @return residual capacity
     */
    public int findResidual(){
        return max-used;
    }

    @Override
    public String toString(){
        return used + "/" + max;
    }
}
