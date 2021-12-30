# Committing

## Adding a Checkpoint
- committing은 **checkpoint를** 남기는 것이다.
- 대략적인 순서
  1. Work on stuff (**Working Directory**)
    - 파일을 생성해 작업을 마치고 save를 한다면, ```git status```를 쳤을 때 untracked files에 추가된 것을 확인할 수 있다.
  2. Add Changes (**Staging Area**)
    - ```git add file1```를 통해 working directory에서 작업을 완료한 파일들을 staging area에 올린다.(```git add file1 file2```처럼 여러개를 넣는 것도 가능)
  3. Commit (**Repository**)
    - ```git commit```을 사용해 commit 가능
    - ```git commit -m "message"```를 사용해서 committing message를 남길 수 있는데, 항상 message를 남기는 것을 추천한다.(message에 설명을 적어서 이해를 돕는다.)


