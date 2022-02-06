public class GraphArray {
    private int[][] array; //인접 행렬 선언

    public GraphArray(int size) {
        array = new int[size][size]; //인접 행렬의 크기
    }

    public int[][] getArray(){
        return array;
    }
}
