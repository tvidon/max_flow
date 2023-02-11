package graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

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
    public Graph(){};
    /**
     * Contructor for when the number of nodes is already known
     * @param size number of nodes in the graph
     */
    public Graph(int size){
        mat = new ArrayList<>(size);
        nodes = new ArrayList<>(size);
        for (int i = 0; i < size; i++){
            mat.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++){
                mat.get(i).add(null);
            }
            nodes.add(new Node(i));
        }
    };
    /**
     * Contructor for when the graph is stored in a file
     * @param path path to the file
     */
    public Graph(String path) throws IOException, IllegalArgumentException{
        // reader
        BufferedReader r = new BufferedReader(new FileReader(new File(path)));
        try{
            // size
            r.readLine();
            int size = Integer.parseInt(r.readLine());
            mat = new ArrayList<>(size);
            nodes = new ArrayList<>(size);
            // adjacency matrix
            r.readLine();
            for (int i = 0; i < size; i++){
                mat.add(new ArrayList<>(size));
                StringTokenizer stElements = new StringTokenizer(r.readLine(), "\t");
                for (int j = 0; j < size; j++){
                    String element = stElements.nextToken();
                    if (element.equals("null")){
                        mat.get(i).add(null);
                    }
                    else{
                        StringTokenizer stElement = new StringTokenizer(element, "/");
                        int used = Integer.parseInt(stElement.nextToken());
                        mat.get(i).add(new Edge(Integer.parseInt(stElement.nextToken())));
                        mat.get(i).get(j).setUsed(used);
                    }
                }
            }
            // nodes
            r.readLine();
            for (int i = 0; i < size; i++){
                Node n = new Node(i);
                nodes.add(n);
                StringTokenizer stXY = new StringTokenizer(r.readLine(), ",");
                n.setX(Integer.parseInt(stXY.nextToken()));
                n.setY(Integer.parseInt(stXY.nextToken()));
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("Invalid file format");
        }
        // close
        r.close();
    };

    /**
     * Get the number of nodes in the graph
     * @return number of nodes in the graph
     */
    public int getSize(){
        return mat.size();
    }

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
     */
    public void addNode(){
        int size = this.getSize();
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
        nodes.add(new Node(size));
    }

    @Override
    public String toString(){
        int size = this.getSize();
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
     * Save the grah to a file
     * Deletes the content of the file if it already exists
     * @param path path to the file to save the graph in
     */
    public void save(String path) throws IOException{
        // create the file if it doesn't already exist
        File f = new File(path);
        f.createNewFile();
        // writer
        BufferedWriter w = new BufferedWriter(new FileWriter(f, false));
        // number of nodes
        w.write("// number of nodes\n");
        w.write(this.getSize() + "\n");
        // adjacency matrix
        w.write("// adjacency matrix\n");
        w.write(this.toString() + "\n");
        // nodes
        w.write("// nodes (x,y)\n");
        for (Node n : nodes){
            w.write(n.getX() + "," + n.getY() + "\n");
        }
        // close
        w.close();
    }

    /**
     * Run the Ford-Fulkerson algorithm
     * @param source source node index in the adjancency matrix
     * @param sink sink node index in the adjacency matrix
     */
    public void ff(int sourceIndex, int sinkIndex){
        Node source = nodes.get(sourceIndex);
        Node sink = nodes.get(sinkIndex);
        int size = this.getSize();
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
