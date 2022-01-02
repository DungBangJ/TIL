```git diff```
- 두 개의 차이를 보여준다.
  - ex) commits, branches, files etc.
- ```git diff```를 친다면, 파일에서 바뀌기 전과 후의 코드가 모두 나온다.
- ***staged***(staging area) vs ***unstaged***(working dir.)
- 만약 ```git add .```을 사용해서 모두 staging area에 올렸다면 ```git diff```의 output은 공백일 것이다.
- output 형식
  - diff --git a/past b/present
  - index ~~~~
  - ---past
  - +++present
  - @@ -3,4(a/의 3번째 줄에서 시작되어 4줄이 바뀌었다.) +3,5(b/의 3번째 줄에서 시작되어 5줄이 바뀌었다.) @@ 
  - 코드의 변화 상황을 모두 보여준다.
  - -는 a/의 코드, +는 b/의 코드
- ```git diff HEAD```
  - HEAD vs working dir. 를 보여주는데 모든 차이를 다 보여준다. HEAD와 차이가 있는 모든 changes를...
- ```git diff --staged```
  - HEAD vs Stage
- 모든 **git diff들** 뒤에다가 파일의 이름을 쓰면 그 파일의 차이를 볼 수 있다.
  - 너무 많은 차이들이 나오면 원하는 정보를 찾기 어려울 수 있다.
- ```git diff branch1..branch2```
  - branch1 vs branch2
- ```git diff commit1..commit2```
  - commit1 vs commit2
  - ```git log --oneline```으로 commit hash를 복사해서 붙여넣기를 해야 편하게 찾을 수 있다.

***git diff는 GUI로 보는게 편하다.***