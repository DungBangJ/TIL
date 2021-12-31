# Branches

모든 commit들은 고유의 hash를 갖고있다. commit은 최소 한개의 parent commit의 hash를 갖고있다.
commit들을 순서대로만 이어나가면 문제가 생긴다. 그래서 branch가 필요하다.

## master brach

- master branch is just the default branch.    
- master branch라고 특별한건 없다. 
- 사람들은 master branch를 업무의 중심 branch라고 생각한다.
- github에서는 master에서 main으로 변경
- 여러 branch를 만들고, 나중에 master branch와 merge할 수 있다.

## HEAD
- our current location
- branch pointer

## branch
- ```git branch``` 모든 branch를 확인할 수 있다. 현재 위치는 다른 색으로 표현된다.
- ```git branch```에서 나오려면 q를 누르자
- ```git branch branch-name```는 branch를 생성한다.
  - branch를 생성하고 ```git log```를 친다면 생성된 branch를 볼 수 있다.
  - branch를 생성하는 위치는 현재 HEAD가 위치해 있는 commit이다.
- ```git switch branch-name```branch-name으로 HEAD를 옮긴다.
  - ```git checkout branch-name```도 같은 기능을 하지만 옛날에 쓰던 것이다.
- ```git switch -c branch-name``` branch를 만들고 바로 switch 시킨다.
- 파일을 수정하고 ```git commit```을 하지 않은 상황에서 ```git switch```를 한다면, 오류가 난다.
- ```git branch -d branch-name``` branch를 삭제한다.
  - 삭제하려는 branch에 HEAD가 있다면 삭제할 수 없다.
  - 정말 branch를 진심으로 삭제하려면 ```git branch -D branch-name```을 사용하면 된다. -D는 강제로 삭제시키는 것을 뜻한다.
- ```git branch -m branch-name2``` branch의 이름을 바꿀 수 있다.
  - branch의 이름을 바꾸려면 HEAD가 바꾸려는 branch에 위치해야 한다.