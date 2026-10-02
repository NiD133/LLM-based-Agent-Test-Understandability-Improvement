package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testListArray {

    @Test
    void testListArray() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);

        final String[] tokenArray = tokenizer.getTokenArray();
        final List<String> tokenList = tokenizer.getTokenList();

        assertEquals(Arrays.asList(tokenArray), tokenList);
        assertEquals(3, tokenList.size());

        // Mutating the returned list must not mutate the tokenizer's cached tokens.
        tokenList.set(0, "z");
        tokenList.remove(1);
        tokenList.set(1, "y");
        tokenList.add("x");
        assertEquals(Arrays.asList("z", "y", "x"), tokenList);

        assertEquals(Arrays.asList(tokenArray), tokenizer.getTokenList());
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
    }
}
