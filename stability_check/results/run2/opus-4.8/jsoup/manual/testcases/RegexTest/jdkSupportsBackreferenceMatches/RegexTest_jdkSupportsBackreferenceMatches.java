package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that when the JDK regex engine is selected (re2j disabled), backreferences
 * such as {@code \1} are supported. Backreferences are a JDK-specific feature that the
 * linear-time re2j engine does not provide, so the engine choice matters here.
 */
public class RegexTest_jdkSupportsBackreferenceMatches {

    /** Saved re2j preference, restored after each test so the global setting is not leaked. */
    private boolean originalUseRe2j;

    @BeforeEach
    void rememberOriginalEngineSetting() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreOriginalEngineSetting() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @Test
    void jdkSupportsBackreferenceMatches() {
        // Force the JDK regex engine, which (unlike re2j) supports backreferences.
        Regex.wantsRe2j(false);

        // "(\w+)\s+\1" matches a word followed by whitespace and the same word repeated,
        // where "\1" is a backreference to the first captured group.
        String patternWithBackreference = "(\\w+)\\s+\\1";
        String repeatedWordInput = "hello hello";

        Regex regex = Regex.compile(patternWithBackreference);
        Regex.Matcher matcher = regex.matcher(repeatedWordInput);

        assertTrue(matcher.find(), "JDK regex engine should match a backreference against a repeated word");
    }
}
