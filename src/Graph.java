import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
     * List of nodes
     */
    private ArrayList<Node> nodes;

    /**
     * Default constructor
     */
    public Graph(){
        Node.resetCount();
    };
    /**
     * Contructor for when the number of nodes is already known
     * @param size number of nodes in the graph
     */
    public Graph(int size){
        mat = new ArrayList<>(size);
        nodes = new ArrayList<>(size);
        Node.resetCount();
        for (int i = 0; i < size; i++){
            mat.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++){
                mat.get(i).add(null);
            }
            nodes.add(new Node());
        }
    };

    /**
     * Add an edge to the graph
     * @param startNodeIndex index of the starting node
     * @param endNodeIndex index of the ending node
     * @param max maximum capacity of the edge
     */
    public void addEdge(int startNodeIndex, int endNodeIndex, int max){
        mat.get(startNodeIndex).set(endNodeIndex, new Edge(max));
    }

    /**
     * Add a node to the graph
     * @param node node to add
     */
    public void addNode(Node node){
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
        // add the node
        nodes.add(node);
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

    /**
     * Run the Ford-Fulkerson algorithm
     * @param source source node index in the adjancency matrix
     * @param sink sink node index in the adjacency matrix
     */
    public void ff(int sourceIndex, int sinkIndex){
        Node source = nodes.get(sourceIndex);
        Node sink = nodes.get(sinkIndex);
        int size = mat.size();
        boolean optimal = false;
        while (!optimal){
            // reset marks
            for (Node node : nodes){
                node.setFrom(null);
            }
            // mark
            Queue<Node> q = new LinkedList<>();
            q.add(source);
            boolean foundSink = false;
            do{
                Edge edge;
                Node currentNode = q.poll();
                Node nextNode;
                int residualCapacity;
                int usedCapacity;
                // go through successorcs and predecessors
                for (int i=0; i<size; i++){
                    // successors
                    edge = mat.get(currentNode.getIndex()).get(i);
                    // check if there is an edge
                    if (edge != null){
                        // check if the node is already marked
                        nextNode = nodes.get(i);
                        if (nextNode.getFrom() == null){
                            // check if there is some residual capacity
                            residualCapacity = edge.findResidual();
                            if (residualCapacity > 0){
                                q.add(nextNode);
                                nextNode.setFrom(currentNode);
                                nextNode.setSuccessor(true);
                                if (nextNode == sink){
                                    foundSink = true;
                                }
                            }
                        }
                    }
                    // predecessors
                    edge = mat.get(i).get(currentNode.getIndex());
                    // check if there is an edge
                    if (edge != null){
                        // check if the node is already marked
                        nextNode = nodes.get(i);
                        if (nextNode.getFrom() == null){
                            // check if there is some used capacity
                            usedCapacity = edge.getUsed();
                            if (usedCapacity > 0){
                                q.add(nextNode);
                                nextNode.setFrom(currentNode);
                                nextNode.setSuccessor(false);
                            }
                        }
                    }
                }
            } while (q.peek() != null & foundSink == false);
            // increase if not optimal
            if (foundSink){
                Node previousNode;
                Node currentNode;
                // find the value to change by
                currentNode = sink;
                previousNode = currentNode.getFrom();
                int min = mat.get(previousNode.getIndex()).get(currentNode.getIndex()).findResidual();
                int res;
                currentNode = previousNode;
                previousNode = currentNode.getFrom();
                while (currentNode != source){
                    if (currentNode.getSuccessor()){
                        res = mat.get(previousNode.getIndex()).get(currentNode.getIndex()).findResidual();
                    }
                    else{
                        res = mat.get(currentNode.getIndex()).get(previousNode.getIndex()).getUsed();
                    }
                    if (res < min){
                        min = res;
                    }
                    currentNode = previousNode;
                    previousNode = currentNode.getFrom();
                }
                // improve flow
                currentNode = sink;
                previousNode = currentNode.getFrom();
                Edge edge = mat.get(previousNode.getIndex()).get(currentNode.getIndex());
                edge.setUsed(edge.getUsed() + min);
                currentNode = previousNode;
                previousNode = currentNode.getFrom();
                while (currentNode != source){
                    if (currentNode.getSuccessor()){
                        edge = mat.get(previousNode.getIndex()).get(currentNode.getIndex());
                        edge.setUsed(edge.getUsed() + min);
                    }
                    else{
                        edge = mat.get(currentNode.getIndex()).get(previousNode.getIndex());
                        edge.setUsed(edge.getUsed() - min);
                    }
                    currentNode = previousNode;
                    previousNode = currentNode.getFrom();
                }
            }
            else{
                optimal = true;
            }
        }
    }
}
