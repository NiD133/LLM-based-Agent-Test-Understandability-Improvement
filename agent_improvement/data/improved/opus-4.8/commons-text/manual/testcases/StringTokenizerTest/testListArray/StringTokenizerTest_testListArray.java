package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringTokenizer#getTokenArray()} and {@link StringTokenizer#getTokenList()}.
 *
 * <p>The tokenizer splits on runs of whitespace, so {@code "a  b c"} yields the three
 * tokens {@code "a"}, {@code "b"}, {@code "c"}. The key contract under test is that the
 * list returned by {@link StringTokenizer#getTokenList()} is a detached copy: mutating it
 * must not affect the tokenizer's own state.</p>
 */
public class StringTokenizerTest_testListArray {

    @Test
    void testListArray() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);

        // The array and list views expose the same three tokens.
        final String[] tokenArray = tokenizer.getTokenArray();
        final List<String> tokenList = tokenizer.getTokenList();
        assertEquals(Arrays.asList(tokenArray), tokenList);
        assertEquals(3, tokenList.size());

        // Mutating the returned list is allowed and ends up as ["z", "y", "x"]:
        //   ["a", "b", "c"]            initial tokens
        //   set(0, "z") -> ["z", "b", "c"]
        //   remove(1)   -> ["z", "c"]
        //   set(1, "y") -> ["z", "y"]
        //   add("x")    -> ["z", "y", "x"]
        tokenList.set(0, "z");
        tokenList.remove(1);
        tokenList.set(1, "y");
        tokenList.add("x");
        assertEquals(Arrays.asList("z", "y", "x"), tokenList);

        // Those mutations did not leak back into the tokenizer: a fresh list still
        // matches the original tokens, and iteration yields them in order.
        assertEquals(Arrays.asList(tokenArray), tokenizer.getTokenList());
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
    }
}
