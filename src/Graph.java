import java.util.ArrayList;

/**
 * Class representing a directional graph
 * Implemented using an adjacency matrix
 */
public class Graph{
    
    /**
     * Adjacency matrix
     */
    private ArrayList<ArrayList<Edge>> mat;

    /**
     * Default constructor
     */
    public Graph(){};
    /**
     * Contructor for when the number of nodes is already known
     * @param size number of nodes in the graph
     */
    public Graph(int size){
        mat = new ArrayList<>(size);
        for (int i = 0; i < size; i++){
            mat.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++){
                mat.get(i).add(null);
            }
        }
    };

    /**
     * Add an edge to the graph
     * @param startNodeIndex index of the starting node (int)
     * @param endNodeIndex index of the ending node (int)
     * @param max maximum capacity of the edge (int)
     */
    public void addEdge(int startNodeIndex, int endNodeIndex, int max){
        mat.get(startNodeIndex).set(endNodeIndex, new Edge(max));
    }

    /**
     * Add a node to the graph
     */
    public void addNode(){
        int size = mat.size();
        // resize the already existing columns
        for (int i = 0; i < size; i++){
            mat.get(i).add(null);
        }
        // add the new columns
        mat.add(new ArrayList<>(size+1));
        for (int j = 0; j < size+1; j++){
            mat.get(size).add(null);
        }
    }

    @Override
    public String toString(){
        int size = mat.size();
        String string = "";
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                string += mat.get(i).get(j) + "\t";
            }
            string += "\n";
        }
        return string.trim();
    }
}
