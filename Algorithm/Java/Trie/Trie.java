package Trie;

public class Trie {

     //Trie는 기본적으로 빈 문자열을 가지는 루트노드만 가지고 있다.
     private final TrieNode rootNode;
     //이후에 나올 insert 메서드를 통해 단어를 넣음으로써 그에 맞게 자식 노드가 생성된다.

     Trie() {
          rootNode = new TrieNode();
     }

     //메서드 구현
     public void insert(String word) {
          //입력받은 단어의 각 알파벳을 계층구조의 자식노드로 만들어 넣는다.
          TrieNode thisNode = this.rootNode;

          for (int i = 0; i < word.length(); i++) {
               //해당 계층 문자의 자식노드가 존재하지 않을 때에만 자식 노드를 생성한다.
               thisNode = thisNode.getChildNodes().computeIfAbsent(word.charAt(i), c -> new TrieNode());
          }

          thisNode.setLastChar(true);

     }

     public boolean contains(String word) {

          //특정 단어가 Trie에 존재하는지를 확인하기 위한 두가지 조건
          //1. 루트노드로부터 순서대로 알파벳이 일치하는 자식노드들이 존재할 것
          //2. 해당 단어의 마지막 글자에 해당하는 노드의 isLastChar 값이 true일 것

          TrieNode thisNode = this.rootNode;
          for (int i = 0; i < word.length(); i++) {
               char character = word.charAt(i);
               TrieNode node = thisNode.getChildNodes().get(character);

               if (node == null) {
                    return false;
               }

               thisNode = node;
          }

          return thisNode.isLastChar();
     }

     //특정 단어 지우기
     void delete(String word) {
          delete(this.rootNode, word, 0); //최초로 delete를 던지는 부분
     }

     //contains 메서드처럼 주어진 단어를 찾아 하위 노드로 단어 길이만큼 내려간다.
     //노드들이 부모노드의 정보를 가지고 있지 않기 때문에, 하위 노드로 내려가며 삭제 대상 단어를 탐색하고,
     //다시 올라오며 삭제하는 과정이 콜백(callback) 형식으로 구현되어야 한다.
     private void delete(TrieNode thisNode, String word, int index) {

          //삭제 조건
          //1. 자식 노드를 가지고 있지 않아야 한다.
          //2. 삭제를 시작하는 첫 노드는 isLastChar = true여야 한다.
          //3. 삭제를 진행하던 중에는 isLastChar = false여야 한다.

          char character = word.charAt(index);

          //아예 없는 단어인 경우 에러 발생
          if (!thisNode.getChildNodes().containsKey(character)) {
               throw new Error("There is no [" + word + "] in this Trie");
          }

          TrieNode childNode = thisNode.getChildNodes().get(character);
          index++;

          if (index == word.length()) {
               //삭제 조건 2번 항목
               if (!childNode.isLastChar()) {
                    throw new Error("There is no [" + word + "] in this Trie");
               }

               childNode.setLastChar(false);

               //삭제조건 1번 항목
               if (childNode.getChildNodes().isEmpty()) {
                    thisNode.getChildNodes().remove(character);
               }

          } else {
               delete(childNode, word, index); //콜백함수

               //삭제조건 1, 3번 항목
               //삭제 중, 자식 노드가 없고 현재 노드로 끝나는 다른 단어가 없는 경우 이 노드 삭제
               if (!childNode.isLastChar() && childNode.getChildNodes().isEmpty()) {
                    thisNode.getChildNodes().remove(character);
               }
          }
     }

     //테스트를 위한 메서드
     boolean isRootEmpty() {
          return this.rootNode.getChildNodes().isEmpty();
     }

}
