2진수, 8진수 16진수를 Scanner로 받으려면 String 데이터타입 또는 int데이터타입을 이용하면된다.

### String에서 n진수로 변환
2진수 : Integer.valueOf(String s, 2);, return : int
8진수 : Integer.valueOf(String s, 8);, return : int
16진수 : Integer.valueOf(String s, 16);, return : int

### 10진수에서 n진수로 변환

2진수 : Integer.toBinaryString(int i), return : String
8진수 : Integer.toOctalString(int i), return : String
16진수 : Integer.toHexString(int i), return : String

### n진수에서 10진수로 변환

2진수 : Integer.parseInt(String s, 2);, return : int
8진수 : Integer.parseInt(String s, 8);, return : int
16진수 : Integer.parseInt(String s, 16);, return : int

출처: https://sowon-dev.github.io/2020/10/06/201007al-c1034/