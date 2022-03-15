package DFSBFS;

public class DFSRecur {
    //아직 방문하지 않은 노드들의 방문 상태를 false로 설정
    public static boolean [] visited = {false, false, false, false, false, false, false, false};

    public static int[][] graph = {
            {}, //0번 노드와 연결된 노드
            {2, 3, 4}, //1번 노드와 연결된 노드
            {1, 5, 6}, //2번 노드와 연결된 노드
            {1}, //3번 노드와 연결된 노드
            {1}, //4번 노드와 연결된 노드
            {2}, //5번 노드와 연결된 노드
            {2, 7}, //6번 노드와 연결된 노드
            {6} //7번 노드와 연결된 노드
    };

    public static void main(String[] args) {
        int start = 1; //시작 노드
        dfs(start); //시작 노드를 입력하고 dfs 시작
    }

    public static void dfs(int v) {
        visited[v] = true; //이동한 노드는 방문 상태를 true로 바꾼다.
        System.out.print(v + "  ");

        for (int i : graph[v]) {
            if (visited[i] == false) { //방문 상태가 false인 노드들에서만 dfs를 다시 실행
                dfs(i);
            }
        }
    }
}
