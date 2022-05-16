import java.util.ArrayList;

public class GraphList {
    private ArrayList<ArrayList<Integer>> listGraph;

    public GraphList() {
        listGraph = new ArrayList<ArrayList<Integer>>();
    }

    public void addVertex(int x){
        listGraph.add(new ArrayList<Integer>(x));
    }

    public void addDirectedEdge(int x, int y){
        listGraph.get(x).add(y);
    }

    public void addCompleteEdge(int x, int y){
        listGraph.get(x).add(y);
        listGraph.get(y).add(x);
    }

    public void printGraph() {
        for(int i=1; i<listGraph.size(); i++) {
            System.out.print("vertex " + i + " => ");
            for(int j=0; j<listGraph.get(i).size(); j++) {
                System.out.print(" "+listGraph.get(i).get(j));
            }
            System.out.println();
        }
    }
}
