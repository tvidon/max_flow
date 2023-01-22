public class Main{
    public static void main(String[] args){
        // default 2x2
        Graph g = new Graph(2);
        System.out.println("default 2x2");
        System.out.println(g);
        // 2x2 with edge from 1 to 0 with a capacity of 10
        g.addEdge(1, 0, 10);
        System.out.println("2x2 with edge from 1 to 0 with a capacity of 10");
        System.out.println(g);
        // increase to 3x3
        g.addNode();
        System.out.println("increase to 3x3");
        System.out.println(g);
    }
}
