public class GraphArray{
    private int[][] array;

    public GraphArray(int size){
        array = new int[size + 1][size + 1];
    }

    public int[][] getArray(){
        return array;
    }

    public boolean isEmpty() {
        return array == null;
    }
    public void addDirectedEdge(int x, int y) {
        array[x][y] = 1;
    }

    public void addCompleteEdge(int x, int y) {
        array[x][y] = 1;
        array[y][x] = 1;
    }
    public void deleteDirectedEdge(int x, int y) {
        array[x][y] = 0;
    }

    public void deleteCompleteEdge(int x, int y) {
        array[x][y] = 0;
        array[y][x] = 0;
    }

}