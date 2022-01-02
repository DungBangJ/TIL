## BigInter가 필요한 이유

![](https://images.velog.io/images/disambur23/post/382de021-9236-4c8d-926b-b16ab0bae6f6/image.png)

 표에 해당하는 범위를 넘어서게 되면 모두 0으로 출력이 된다. 숫자의 범위가 저 범위를 넘을 경우는 잘 없겠지만 프로그램 개발 특히 돈과 관련된 개발이나 알고리즘 문제를 풀 때 항상 최악의 상황을 고려해야 하므로 무한의 정수가 들어갈 수 있는 가능성이 있다면 BigInteger이라는 클래스를 활용하는 것이 좋습니다. BigInteger은 문자열 형태로 이루어져 있어 **숫자의 범위가 무한**하기에 어떠한 숫자이든지 담을 수 있습니다.
 
 
##  BigInteger 사용법

우선 다음과 같이 import를 해준다.
```
import java.math.BigInteger;
```
```
BigInteger bigNumber = new BigInteger("10000");
```
BigInteger은 java.math안에 있으며 위와 같이 선언한다. 특이한 점은 BigInteger를 초기화하기 위해서는 **문자열을 인자 값으로 넘겨주어야 한다**는 점입니다. **BigInteger가 문자열**로 되어 있기 때문입니다.

### BigInteger 계산
```
BigInteger bigNumber1 = new BigInteger("100000");
BigInteger bigNumber2 = new BigInteger("10000");
		
System.out.println("덧셈(+) :" +bigNumber1.add(bigNumber2));
System.out.println("뺄셈(-) :" +bigNumber1.subtract(bigNumber2));
System.out.println("곱셈(*) :" +bigNumber1.multiply(bigNumber2));
System.out.println("나눗셈(/) :" +bigNumber1.divide(bigNumber2));
System.out.println("나머지(%) :" +bigNumber1.remainder(bigNumber2));
```
**BigInteger은 문자열이기에 사칙연산이 안된다.** 그렇기에 BigIntger 내부의 숫자를 계산하기 위해서는 BigIntger 클래스 내부에 있는 메서드를 사용해야 한다.


### BigInteger 형변환
```
BigInteger bigNumber = BigInteger.valueOf(100000); //int -> BigIntger

int int_bigNum = bigNumber.intValue(); //BigIntger -> int
long long_bigNum = bigNumber.longValue(); //BigIntger -> long
float float_bigNum = bigNumber.floatValue(); //BigIntger -> float
double double_bigNum = bigNumber.doubleValue(); //BigIntger -> double
String String_bigNum = bigNumber.toString(); //BigIntger -> String
```

### BigInteger 대소 비교
```
BigInteger bigNumber1 = new BigInteger("100000");
BigInteger bigNumber2 = new BigInteger("1000000");
		
//두 수 비교 compareTo 동일하면 0, bigNumber1가 크면 1, bigNumber1가 작으면 -1
int compare = bigNumber1.compareTo(bigNumber2);
System.out.println(compare);
```
BigInteger의 값을 비교할 때는 **compareTo**라는 메서드를 사용한다.
**두 수 비교 compareTo 동일하면 0, bigNumber1가 크면 1, bigNumber1가 작으면 -1**

### BigInteger와 Scanner 같이 쓰기
Scanner는 nextBigInteger를 지원하기 때문에, Scanner를 이용하면 두 수를 입력받아 합을 계산하는 프로그램을 작성할 수 있습니다.
```
import java.util.*;
import java.math.*;

public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        BigInteger a = sc.nextBigInteger();
        BigInteger b = sc.nextBigInteger();
        BigInteger sum = a.add(b);
        System.out.println(sum.toString());
    }
}
```

### BigInteger 배열
```
import java.util.*;
import java.math.*;

public class Main {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        BigInteger[] f = new BigInteger[100];
        f[0] = BigInteger.ZERO;
        f[1] = BigInteger.ONE;
        for (int i=2; i<f.length; i++) {
            f[i] = f[i-1].add(f[i-2]);
        }
        for (int i=0; i<f.length; i++) {
            System.out.println(String.format("f[%d] = %s",i, f[i].toString()));
        }
    }
}
```