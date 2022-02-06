import java.util.Deque;
import java.util.LinkedList;

public class DFSStack {
    static void dfs(int[][] graph, int start, boolean[] visited) {
        visited[start] = true; //시작 노드의 방문 상태 true
        System.out.print(start + "  ");

        Deque<Integer> stack = new LinkedList<>(); //스택 생성
        stack.push(start); //시작 노드를 스택의 첫번째로 집어 넣음

        while (!stack.isEmpty()) { //스택이 비어있지 않다면 while문 수행
            int now = stack.peek(); //현재 스택 최상단에 있는 노드를 now

            boolean hasNearNode = false; //인접 노드가 없다고 초기화
            for (int i : graph[now]) {
                //인접한 노드를 방문하지 않았다면 스택에 넣고 방문처리
                if (!visited[i]) {
                    hasNearNode = true;
                    stack.push(i); //스택에 넣는다.
                    visited[i] = true; //방문 처리
                    System.out.print(i + "  ");
                    break;
                }//if
            }//for

            //인접한 노드를 모두 방문했다면 스택에서 해당 노드를 꺼낸다.
            if (hasNearNode == false) {
                stack.pop();
            }
        }//while
    }

    public static void main(String[] args) {
         int[][] graph = {
                {}, //0번 노드와 연결된 노드
                {2, 3, 4}, //1번 노드와 연결된 노드
                {1, 5, 6}, //2번 노드와 연결된 노드
                {1}, //3번 노드와 연결된 노드
                {1}, //4번 노드와 연결된 노드
                {2}, //5번 노드와 연결된 노드
                {2, 7}, //6번 노드와 연결된 노드
                {6} //7번 노드와 연결된 노드
        };
        boolean [] visited = {false, false, false, false, false, false, false, false};

        dfs(graph, 1, visited);
    }
}
