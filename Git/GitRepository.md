# Repository

## Git repostitory
내가 직접 git repository를 설정해야 한다.

```git status```
- git 폴더의 상태를 확인한다.
- git init을 하기 전에 필수적으로 git status를 사용해서 이미 git repository가 아닌지 확인해야 한다.(막 너무 위험한건 아니지만, 혼란 방지)

```git init```
- 현 위치를 git repository로 변환시킨다.
- git status를 치고 git 폴더가 아니란 것을 확인 후에 사용한다.
- 성공적으로 git init을 하고나서 command line에 <br/>```ls -a```를 친다면 .git 파일이 생성된 것을 확인할 수 있다.