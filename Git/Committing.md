# Committing

## Adding a Checkpoint
- committing은 **checkpoint를** 남기는 것이다.
- 대략적인 순서
  1. Work on stuff (**Working Directory**)
    - 파일을 생성해 작업을 마치고 save를 한다면, ```git status```를 쳤을 때 untracked files에 추가된 것을 확인할 수 있다.
  2. Add Changes (**Staging Area**)
    - ```git add file1```를 통해 working directory에서 작업을 완료한 파일들을 staging area에 올린다.(```git add file1 file2```처럼 여러개를 넣는 것도 가능)
    - ```git add .```를 사용하면 한번에 모든 파일을 staging area에 올릴 수 있다.
  3. Commit (**Repository**)
    - ```git commit```을 사용해 commit 가능
    - ```git commit -m "message"```를 사용해서 committing message를 남길 수 있는데, 항상 message를 남기는 것을 추천한다.(message에 설명을 적어서 이해를 돕는다.)
    - commit을 한 파일 중에서 변경사항이 있다면, ```git status```를 사용했을 때, modified에 적힌 것을 확인할 수 있다.<br/>이 파일을 다시 ```git commit```하면 추가적인 내용에 대한 업데이트가 완료된다.
    - ```git log```를 사용해서 commit 정보를 시간 순서로 확인할 수 있다.
- **Keep commits atomic**
  - 최대한 세세한 내용을 commit하자
- Git message를 가급적이면 항상 사용하자
  - 현재형 동사를 사용하자
- ```git commit```을 했을 때 **vim**이 뜬다면?
  - ```i```를 누르면 INSERT mode가 된다.
  - INSERT mode가 됐으면 상단에 commit message를 입력할 수 있는데, ""이 필요없으니 그냥 message를 입력하자.
  - 입력했다면, ```esc```를 눌러 INSERT mode를 벗어나자.
  - 그 후에 ```:wq```를 입력하면 빠져나올 수 있다.

### git log
- ```git log --onelne```
  - ```git log```가 너무 보기 힘들고 복잡하다면, ```git log --oneline```을 사용해서 commit들의 짧은 hashcode와 message만 나열해서 볼 수 있다.

## Amending Commits
- 오직 직전의 commit만 수정 가능하다.
- ```git commit```을 완료한 파일을 다시 ```git add```하여 stage area로 올린 다음<br/> ```git commit --amend```를 사용해 직전의 commit 상태로 되돌린다.

## Ignoring Files
- 나의 민감한 정보들이 같이 commit되지 않게 해야한다.
- ```.gitignore```파일을 repository의 root에 생성하면, git이 commit을 할때 이 파일을 찾아서 어떤 형태의 파일을 무시할 것인지 확인한다.
- 이 파일 안에는 파일 혹은 폴더의 형식이나 이름을 기록한다.
- ```.gitignore```의 example: [김남윤님의 Github](https://github.com/cheese10yun/spring-jpa-best-practices/blob/master/.gitignore)
- vs code에서 단순하게 ```.gitignore```파일을 만들고 정보들을 나열하면 된다.
- 무엇을 무시해야 하지 모르겟으면 [.gitignore.io](https://www.toptal.com/developers/gitignore)에 접속해 자신이 사용하는 언어를 검색하면, 그에 해당하는 무시해야하는 type들이 나열된다. 이것을 그냥 복붙해도 문제는 없다.