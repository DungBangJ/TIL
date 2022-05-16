package Trie;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTrie {

     @Test
     public void trieTest() {
          Trie trie = new Trie();

          //insert 메서드
          assertTrue(trie.isRootEmpty());

          trie.insert("PI");
          trie.insert("PIE");
          trie.insert("POW");
          trie.insert("POP");

          assertFalse(trie.isRootEmpty());

          //contains 메서드
          assertTrue(trie.contains("POW"));
          assertFalse(trie.contains("PIES"));

          //Delete 메서드
          trie.delete("POP");
          assertFalse(trie.contains("POP"));
          assertTrue(trie.contains("POW"));

          //없는 단어를 지울 때 에러가 발생하는지 테스트
//          trie.delete("PO");
//          trie.delete("PIES");
//          trie.delete("PEN");
     }
}
