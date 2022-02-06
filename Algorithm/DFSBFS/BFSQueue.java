import java.util.LinkedList;
import java.util.Queue;

public class BFSQueue {
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

        int start = 1; //시작 노드

        //큐 구현
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);

        //현재 노드를 방문 처리
        visited[start] = true;

        //큐가 빌 때까지 반복
        while (!queue.isEmpty()) {
            //큐에서 요소 하나를 뽑아 출력
            int v = queue.poll();
            System.out.print(v + "  ");

            //인접한 노드 중 아직 방문하지 않은 원소들을 큐에 삽입
            for (int i : graph[v]) {
                if(visited[i] == false) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
        }
    }
}
