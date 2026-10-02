package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testCharMatcher_char extends AbstractLangTest {

    private static final char TARGET_CHARACTER = 'c';
    private static final char[] BUFFER2 = "abcdef".toCharArray();

    @Test
    void testCharMatcher_char() {
        final StrMatcher matcher = StrMatcher.charMatcher(TARGET_CHARACTER);
        final int[] expectedMatchLengthsByPosition = {0, 0, 1, 0, 0, 0};

        for (int position = 0; position < expectedMatchLengthsByPosition.length; position++) {
            assertEquals(expectedMatchLengthsByPosition[position], matcher.isMatch(BUFFER2, position));
        }
    }
}
