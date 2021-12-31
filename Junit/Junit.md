# unit test
- method를 test하는 방법
- necessary!!
- lower cost of mistakes
- Junit
  - standard for Java
- can combine with Mockito for more complicated projects


수업에 필요한 파일을 다운 받은 후에 테스트하고 싶은 클래스파일에서 오른쪽 마우스 클릭을 하고, New에서 Junit Test Case를 클릭한다.
source folder명을 main에서 test로 바꿔준다.
- 우리는 test를 하고싶은 것이기에 main 폴더에서 test를 진행하지 않으므로, directory를 test로 변경한 것이다.
그리고 source folder 위에 있는 New Junit Jupiter test를 클릭해준다.

이렇게 path까지 추가해주면, 저절로 test폴더가 만들어지면서 test 파일에 생성된다.
- 초기 상태는 test 메소드 안에 fail이 있으므로 무조건 fail이 뜨지만, 이 구문을 지운다면 코드가 잘 돌아가는 것을 확인할 수 있다.

test 메소드 안에 assertTru()를 사용해서 확인하고 싶은 것을 ()안에 넣는다. 그것이 참이라면 test는 성황리에 끝날 것이다.
- test 메소드를 가독성을 높이기 위해 문장으로 메소드 이름을 표현하자.
  - should_ReturnTrue_When_DietRecommended()로 바꿔주자.

unit test는 남들이 보기에 한번에 어떤 test인지 파악할 수 있을만큼 가독성이 좋게 만들어야 한다.
- given
- when
- then
