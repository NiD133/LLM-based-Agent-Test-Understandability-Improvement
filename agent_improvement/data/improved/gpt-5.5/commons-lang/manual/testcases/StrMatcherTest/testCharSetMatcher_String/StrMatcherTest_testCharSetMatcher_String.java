package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class StrMatcherTest_testCharSetMatcher_String extends AbstractLangTest {

    private static final String MATCHED_CHARACTERS = "ace";
    private static final char[] TEST_BUFFER = "abcdef".toCharArray();
    private static final int SINGLE_CHARACTER_MATCH_LENGTH = 1;
    private static final int NO_MATCH_LENGTH = 0;

    @Test
    void testCharSetMatcher_String() {
        final StrMatcher matcher = StrMatcher.charSetMatcher(MATCHED_CHARACTERS);

        assertMatchesSelectedCharacter(matcher, 0);
        assertDoesNotMatchSelectedCharacter(matcher, 1);
        assertMatchesSelectedCharacter(matcher, 2);
        assertDoesNotMatchSelectedCharacter(matcher, 3);
        assertMatchesSelectedCharacter(matcher, 4);
        assertDoesNotMatchSelectedCharacter(matcher, 5);

        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher(""));
        assertSame(StrMatcher.noneMatcher(), StrMatcher.charSetMatcher((String) null));
        assertInstanceOf(StrMatcher.CharMatcher.class, StrMatcher.charSetMatcher("a"));
    }

    private static void assertMatchesSelectedCharacter(final StrMatcher matcher, final int position) {
        assertEquals(SINGLE_CHARACTER_MATCH_LENGTH, matcher.isMatch(TEST_BUFFER, position));
    }

    private static void assertDoesNotMatchSelectedCharacter(final StrMatcher matcher, final int position) {
        assertEquals(NO_MATCH_LENGTH, matcher.isMatch(TEST_BUFFER, position));
    }
}
