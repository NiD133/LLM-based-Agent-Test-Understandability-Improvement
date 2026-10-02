package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testCharMatcher_char extends AbstractLangTest {

    // "abcdef": index 0='a', 1='b', 2='c', 3='d', 4='e', 5='f'
    private static final char[] BUFFER2 = "abcdef".toCharArray();

    @Test
    void testCharMatcher_char() {
        final StrMatcher matcher = StrMatcher.charMatcher('c');
        assertEquals(0, matcher.isMatch(BUFFER2, 0), "index 0 is 'a': no match expected");
        assertEquals(0, matcher.isMatch(BUFFER2, 1), "index 1 is 'b': no match expected");
        assertEquals(1, matcher.isMatch(BUFFER2, 2), "index 2 is 'c': match expected");
        assertEquals(0, matcher.isMatch(BUFFER2, 3), "index 3 is 'd': no match expected");
        assertEquals(0, matcher.isMatch(BUFFER2, 4), "index 4 is 'e': no match expected");
        assertEquals(0, matcher.isMatch(BUFFER2, 5), "index 5 is 'f': no match expected");
    }
}
