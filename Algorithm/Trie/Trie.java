package Trie;

public class Trie {

     //Trie는 기본적으로 빈 문자열을 가지는 루트노드만 가지고 있다.
     private TrieNode rootNode;
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

     public void contains(String word) {

     }

}
