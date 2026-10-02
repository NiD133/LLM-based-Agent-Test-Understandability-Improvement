package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testMatcherIndices extends AbstractLangTest {

    private static final char[] BUFFER1 = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final char[] BUFFER2 = "abcdef".toCharArray();

    @Test
    void testMatcherIndices() {
        // remember that the API contract is tight for the isMatch() method
        // all the onus is on the caller, so invalid inputs are not
        // the concern of StrMatcher, and are not bugs
        final StrMatcher matcher = StrMatcher.stringMatcher("bc");
        assertEquals(2, matcher.isMatch(BUFFER2, 1, 1, BUFFER2.length));
        assertEquals(2, matcher.isMatch(BUFFER2, 1, 0, 3));
        assertEquals(0, matcher.isMatch(BUFFER2, 1, 0, 2));
    }
}
