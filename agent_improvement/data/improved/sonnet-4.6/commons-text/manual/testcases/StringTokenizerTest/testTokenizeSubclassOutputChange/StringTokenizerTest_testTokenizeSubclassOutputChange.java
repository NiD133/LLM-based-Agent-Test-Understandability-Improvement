package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testTokenizeSubclassOutputChange {

    /**
     * Verifies that subclasses can override {@code tokenize} to change the token order
     * returned by the iterator. The subclass reverses the token list so that "a b c"
     * is iterated as "c", "b", "a".
     */
    @Test
    void testTokenizeSubclassOutputChange() {
        final StringTokenizer tkn = new StringTokenizer("a b c") {

            @Override
            protected List<String> tokenize(final char[] chars, final int offset, final int count) {
                final List<String> list = super.tokenize(chars, offset, count);
                Collections.reverse(list);
                return list;
            }
        };
        assertEquals("c", tkn.next());
        assertEquals("b", tkn.next());
        assertEquals("a", tkn.next());
    }
}
