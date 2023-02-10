import graph.Graph;

public class Main{
    public static void main(String[] args){
        // default 2x2
        Graph g = new Graph(2);
        System.out.println("default 2x2");
        System.out.println(g);
        // 2x2 with edge from 0 to 1 with a capacity of 10
        g.addEdge(0, 1, 10);
        System.out.println("2x2 with edge from 0 to 1 with a capacity of 10");
        System.out.println(g);
        // increase to 3x3
        g.addNode();
        System.out.println("increase to 3x3");
        System.out.println(g);
        // test ff
        System.out.println("\ntest ff");
        g = new Graph(9);
        g.addEdge(0, 1, 30);
        g.addEdge(0, 2, 30);
        g.addEdge(0, 3, 20);
        g.addEdge(1, 4, 20);
        g.addEdge(1, 5, 20);
        g.addEdge(2, 4, 20);
        g.addEdge(2, 5, 20);
        g.addEdge(3, 4, 20);
        g.addEdge(3, 5, 20);
        g.addEdge(4, 6, 10);
        g.addEdge(5, 7, 60);
        g.addEdge(6, 8, 30);
        g.addEdge(7, 6, 20);
        g.addEdge(7, 8, 50);
        System.out.println("before");
        System.out.println(g);
        g.ff(0, 8);
        System.out.println("after");
        System.out.println(g);
    }
}
