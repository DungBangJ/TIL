package Trie;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
     private Map<Character, TrieNode> childNodes = new HashMap<>(); //자식 노드 맵
     private boolean isLastChar; //마지막 글자인지 여부

     //자식노드는 Trie 차원에서 생성해 넣을 것이기 때문에 Getter만 생성
     public Map<Character, TrieNode> getChildNodes() {
          return childNodes;
     }

     public boolean isLastChar() {
          return isLastChar;
     }

     public void setLastChar(boolean lastChar) {
          this.isLastChar = lastChar;
     }
}
