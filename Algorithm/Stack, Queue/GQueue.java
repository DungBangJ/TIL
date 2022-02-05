//실행 시 예외: 큐가 비어 있음
class EmptyGQueueException extends RuntimeException {
    public EmptyGQueueException(){}
}

//실행 시 예외: 큐가 가득 참
class OverFlowGQueueException extends RuntimeException {
    public OverFlowGQueueException(){}
}

public class GQueue<E> {
    private int max; //큐의 용량
    private int front; //첫 번째 요소 커서
    private int rear; //마지막 요소 커서
    private int num; //현재 데이터 수
    private E[] que; //큐 본체
    //큐가 비었을 때나 가득 찼을 때 모두 front = rear이므로
    //구분을 하기 위해 max와 num이 필요하다.

    //생성자
    public GQueue(int capacity) {
        num = front = rear = 0;
        max = capacity;
        try {
            que = (E[])new Object[max]; //큐 본체용 배열을 생성성
        } catch (OutOfMemoryError e) { //생성할 수 없음
            max = 0;
        }
    }

    //큐에 데이터를 인큐
    public E equeue(E x) throws OverFlowGQueueException{
        if (num >= max) {
            throw new OverFlowGQueueException();
        }
        que[rear++] = x; //큐에 x를 대입하고 rear와 num을 증가시킨다.
        num++;
        if (rear == max) { //max-1값에서 1 증가시키면 max값이 되기 때문에, 이때 rear를 0으로 만들어준다.
            rear = 0;
        }
        return x; //인큐에 성공하면 인큐한 값을 그대로 반환
    }

    //큐에서 데이터를 디큐
    public int dequeue() throws EmptyGQueueException {
        if (num <= 0) {
            throw new EmptyGQueueException();
        }
        int x = (int)que[front++]; //dequeue는 front에서 빼는 것이므로 front를 1증가시킨다.
        num--; //데이터 수는 -1
        if (front == max) { //1만큼 증가한 front 값이 큐의 용량인 max와 같아지면 front의 값을 0으로 만들어준다.
            front = 0;
        }
        return x;
    }

    //큐에서 데이터를 피크(프런트 데이터를 들여다봄)
    public int peek() throws EmptyGQueueException {
        if (num <= 0) {
            throw new EmptyGQueueException();
        }
        return (int)que[front];
    }

    //큐에서 x를 검색하면 인덱스를 반환(없으면 -1반환)
    public int indexOf(int x) {
        for (int i = 0; i < num; i++) {
            int idx = (i + front) % max;
            if ((int)que[idx] == x) {
                return idx;
            }
        }
        return -1;
    }

    //큐를 비움
    public void clear() {
        num = front = rear = 0;
    }

    //큐의 용량을 반환
    public int capacity() {
        return max;
    }

    //큐에 쌓여 있는 데이터 수
    public int size() {
        return num;
    }

    //큐가 비어 있는가?
    public boolean isEmpty() {
        return num <= 0;
    }

    //큐가 가득 찼는가?
    public boolean isFull() {
        return num >= max;
    }

    //큐 안의 모든 데이터를 프런트 -> 리어 순으로 출력
    public void dump() {
        if (num <= 0) {
            System.out.println("큐가 비어 있습니다.");
        } else {
            for (int i = 0; i < num; i++) {
                System.out.println(que[(i + front) % max] + " ");
            }
            System.out.println();
        }
    }
}
