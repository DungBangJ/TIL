//실행 시 예외: 스택이 비어 있음
class EmptyBothStackException extends RuntimeException {
    public EmptyBothStackException() {
    }
}

//실행 시 예외: 스택이 가득 참
class OverflowBothStackException extends RuntimeException {
    public OverflowBothStackException() {
    }
}

public class BothStack {
    private int max; //스택 용량
    private int ptr; //스택 포인터
    private int ptrO; //스택 포인터
    private int[] stk; //스택 본체

    //생성자
    public BothStack(int capacity) {
        ptr = 0;
        max = capacity;
        ptrO = max - 1;
        try {
            stk = new int[max]; //스택 본체용 배열을 생성
        } catch (OutOfMemoryError e) { //생성할 수 없음
            max = 0;
        }
    }

    //스택에 x를 푸시
    public int pushx(int x) throws OverflowBothStackException {
        if (ptr >= ptrO) { //스택이 가득차면
            throw new OverflowBothStackException();
        }
        return stk[ptr++] = x; //값을 저장하고 스택 포인터를 증가시킴
    }

    //스택에 y를 푸시
    public int pushy(int y) throws OverflowBothStackException {
        if (ptrO <= ptr) { //스택이 가득차면
            throw new OverflowBothStackException();
        }
        return stk[ptrO--] = y; //값을 저장하고 스택 포인터를 증가시킴
    }

    //스택에서 데이터를 팝(정상에 있는 데이터를 꺼냄)
    public int popx() throws EmptyBothStackException {
        if (ptr <= 0) { //스택이 비어 있다면
            throw new EmptyBothStackException();
        }
        return stk[--ptr]; //값을 삭제하고 스택의 이전 값을 반환
    }

    //스택에서 데이터를 팝(정상에 있는 데이터를 꺼냄)
    public int popy() throws EmptyBothStackException {
        if (ptrO >= max) { //스택이 비어 있다면
            throw new EmptyBothStackException();
        }
        return stk[++ptrO]; //값을 삭제하고 스택의 이전 값을 반환
    }

    //스택에서 데이터를 피크(정상에 있는 데이터를 들여다봄)
    public int peekx() throws EmptyBothStackException {
        if (ptr <= 0) { //스택이 비어 있으면
            throw new EmptyBothStackException();
        }
        return stk[ptr - 1]; //데이터의 입출력이 없으므로 스택 포인터는 변화하지 않는다.
    }

    //스택에서 데이터를 피크(정상에 있는 데이터를 들여다봄)
    public int peeky() throws EmptyBothStackException {
        if (ptrO >= max) { //스택이 비어 있으면
            throw new EmptyBothStackException();
        }
        return stk[ptrO + 1]; //데이터의 입출력이 없으므로 스택 포인터는 변화하지 않는다.
    }


    //스택에서 x를  찾아 인덱스를 반환(없으면 -1반환)
    public int indexOfx(int x) {
        for (int i = ptr - 1; i >= 0; i--) {
            if (stk[i] == x) return i;
        }
        return -1;
    }

    //스택에서 y를  찾아 인덱스를 반환(없으면 -1반환)
    public int indexOfy(int y) {
        for (int i = ptrO + 1; i <= max; i++) {
            if (stk[i] == y) return i;
        }
        return -1;
    }

    //스택을 비움
    public void clear() {
        ptr = 0; //스택 포인터를 0으로 두면 다시 쌓아야 한다는 의미이므로 clear와 같은 의미
    }

    //스택의 용량을 반환
    public int capacity() {
        return max;
    }

    //스택에 쌓여 있는 데이터 수를 반환
    public int size() {
        return ptr;
    }

    //스택이 비어 있는가?
    public boolean isEmpty() {
        return ptr <= 0;
    }

    //스택이 가득 찼는가?
    public boolean isFull() {
        return ptr >= max;
    }

    //스택 안의 모든 데이터를 바닥 -> 꼭대기 순으로 출력
    public void dump() {
        if (ptr <= 0) {
            System.out.println("스택이 비어 있습니다.");
        } else {
            for (int i = 0; i < ptr; i++) {
                System.out.print(stk[i] + " ");
            }
            System.out.println();
        }
    }//dump
}//IntStack Class
