# Merge

- 간단하게 branch를 합치는 것

## Fast Forward Mearge

- HEAD의 commit에서 ```git merge branch-name```을 친다면 HEAD의 commit에서 branch-name으로 이동하며 합치게 된다. (fast-forward merge)
  - 각각의 branch는 아직 존재하고, HEAD의 commit의 내용에 branch-name의 내용을 추가해준 것이라고 생각해야 한다. (branch의 상태를 시각화하면 둘이 한 branch가 된 것을 확인할 수 있지만, 그래도 ```switch```를 사용해서 branch이동을 할 수 있다.)
    - 만약 HEAD에 내용을 추가하여 commit한다면 한 branch에서 HEAD가 앞서나가는 것을 확인할 수 있다.

## Merge Commit

- 한 branch에서 여러개의 commit 중 과거에 있는 commit에서 내용을 추가한다면 그 지점에 branch가 하나 더 생성되면서 분기된다.
  - 최근에 있던 commit에는 없던 내용이 새롭게 추가되는 것이므로 다른 branch가 생성되는 것이다.
  - 마치 나무에서 새로운 가지가 자라는 것처럼.
- HEAD에서 분기한 branch-name을 merge하려면 <br/>똑같이 ```git merge branch-name```을 치면 된다.
  - 그러면 branch-name의 branch가 쭉 땡겨져서 HEAD의 commit에 붙는다.

### Merge Conflicts

- merge를 하는데 오류가 나면 오류를 일으킨 파일로 가서 표시되어 있는 오류를 고친다.
- ***내용이 다르므로 어떤 내용을 살리고 죽일지를 결정하라고 나온다. 두 내용을 모두 살려서 합칠 수 있고, 한쪽을 죽여서 새로운 내용을 만들 수도 있다. 심지어 둘 다 죽이고 공백으로 merge를 할 수도 있다.***
