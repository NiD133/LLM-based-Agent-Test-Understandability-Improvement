package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that the JDK regex engine (re2j disabled) supports backreferences,
 * which the linear-time re2j engine does not.
 */
public class RegexTest_jdkSupportsBackreferenceMatches {

    /** The re2j-engine preference in effect before this test, restored afterwards. */
    private boolean originalUseRe2j;

    @BeforeEach
    void rememberRe2jSetting() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreRe2jSetting() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @Test
    void jdkRegexMatchesBackreference() {
        // Force the JDK backtracking engine, which (unlike re2j) supports backreferences.
        Regex.wantsRe2j(false);

        // "(\w+)\s+\1" matches a word, whitespace, then the same word repeated.
        String backreferencePattern = "(\\w+)\\s+\\1";
        String repeatedWordInput = "hello hello";

        Regex regex = Regex.compile(backreferencePattern);
        Regex.Matcher matcher = regex.matcher(repeatedWordInput);

        assertTrue(matcher.find(), "JDK regex should match the repeated word via backreference");
    }
}
