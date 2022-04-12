# magic file 경로

- /etc/magic
- /usr/share/misc/file/magic(/sur/share/file/magic)

magic file의 엔트리
- 4개의 필드로 구성
  - 처음 혹은 이전 레벨로부터의 오프셋 값
  - 데이터 종류
  - 값
  - 출력 문자열

magic file은 열 수 있는 파일이 아니다. 
- vi 로 magic file을 열어보면 '41 string 42 42 file'처럼 명령어 없이 무턱대고 숫자부터 나오므로 실행할 수 없는 것이다.
  - man file을 쳐보면 file -m 이 나오는데 이 명령어가 magic file을 실행시켜주는 명령어인 것이다.


# tr (translate characters)

맥의 터미널(unix) 환경에서 파일 안의 내용을 치환하는 방법
- 텍스트 파일 내의 문자들을 치환해주는 기능을 수행

```zsh
$ tr [Option] [Set1] [Set2]
```
- Option을 바탕으로 Set1을 Set2로 치환한다.
  - Option
    - Option이 없으면 그냥 그대로 치환해준다.
    - -c (complements)
      - [Set1]의 문자열을 제외한 나머지 문자를 모두 [Set2]의 문자열로 치환
    - -d (delete)
      - [Set1]에 지정된 문자열을 삭제
    - -t (truncates)
      - [Set1]의 문자열을 [Set2]의 문자열 길이로 자른다.

```
id -Gn $FT_USER | tr ' ' ',' | tr -d '\n' | cat -e

groups $FT_USER | sed 's/ /,/g' | tr -d '\n' | cat -e
```
- 두 개는 같은 코드이다
- tr의 역할을 sed로 대체해도 된다.

# find

-exec (utility) {};
- 마지막에 세미콜론을 꼭 붙여줘야 한다.
- exec 뒤의 옵션을 실행하는 것
- {}은 현재 경로를 의미한다.
- find가 실행되는 현 디렉토리에서 utility가 실행되게 만드는 구문이다.

```zsh
find . -type f -name "*.sh" -exec basename {} '.sh' \;
```
- 이름이 .sh로 끝나는 모든 파일을 해당 디렉토리에서 찾아서 그것의 basename을 실행시킨다
  - 이 basename을 사용하면 파일명이나 확장자를 추출할 수 있으며 파일 경로를 옵션없이 사용하면 확장자를 포함한 파일명을 추출한다.
  - basename 뒤에 '.sh'가 나왔으므로 확장자(.sh)를 삭제한다는 소리이다.
  - 즉, {}(현재 경로)에 있는 .sh 파일들의 확장자를 제거해서 출력한다.