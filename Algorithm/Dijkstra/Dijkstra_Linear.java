public class Dijkstra_Linear {
        static int num = 7;
        static int INF = 1000000;

        //노드의 방문 상태
        static boolean[] visited = new boolean[7];

        //계속 갱신될 최소 가중치 배열
        static int[] finalArray = new int[7];

        //전체 그래프 초기화
        static int[][] arr = {
                {0, 1, 1, 3, INF, INF, INF},
                {1, 0, INF, 4, 3, 2, INF},
                {1, INF, 0, 1, INF, 5, INF},
                {3, 4, 1, 0, INF, INF, INF},
                {INF, 3, INF, INF, 0, 2, 2},
                {INF, 2, INF, INF, 2, 0, 1},
                {INF, INF, INF, INF, 2, 1, 0}
        };

        public static void main(String[] args) {
                Dijkstra_Linear ds = new Dijkstra_Linear();

                //배열은 0부터 시작하므로 start값에 1이 아닌 0 대입
                ds.dijkstra(0);

                for (int i = 0; i < num; i++) {
                        System.out.print(finalArray[i] + "  ");
                }

        }

        //최소 거리를 가지는 정점을 반환
        public int getSmallDis() {
                int min = INF;
                int idx = 0;

                for (int i = 0; i < num; i++) {
                        if (finalArray[i] < min && !visited[i]) {
                                min = finalArray[i];
                                idx = i;
                        }
                        //[0, 1, 1, 3, INF, INF, INF]

                }

                return idx;
        }

        public void dijkstra(int start) {

                //시작할 때 start의 배열을 갱신배열에 넣어준다.
                for (int i = 0; i < num; i++) {
                        finalArray[i] = arr[start][i];
                        //[0, 1, 1, 3, INF, INF, INF]
                }

                visited[start] = true;

                //num-2인 이유
                //자기 자신의 경우를 제외하고 반복문을 돌리면 마지막 남은 노드는 이미 가중치가 모두 갱신된 상태이므로
                //이동을 해도 바꿀 가중치가 없기 때문에 자기자신의 경우와 마지막 노드의 경우를 빼준 것이다.
                for (int i = 0; i < num - 2; i++) {

                        //현재 위치한 노드에서 가장 가중치가 작은 노드로 이동해야 한다.
                        int current = getSmallDis();

                        //가중치가 작은 노드로 이동했으므로 그 노드를 방문 처리리
                        visited[i] = true;
                        for (int j = 0; j < num; j++) {
                                //해당 노드의 인접 노드들을 확인한다.
                                if (!visited[j]) { //그 인접 노드가 방문하지 않은 상태라면
                                        //현재 위치한 그 노드까지의 최소 비용에서 그 해당 노드에서 인접 노드까지의 거리가
                                        //원래의 가중치 배열의 값보다 작다면 값을 갱신해준다.
                                        if (finalArray[current] + arr[current][j] < finalArray[j]) {
                                                finalArray[j] = finalArray[current] + arr[current][j];
                                        }
                                }
                        }
                }
        }
}