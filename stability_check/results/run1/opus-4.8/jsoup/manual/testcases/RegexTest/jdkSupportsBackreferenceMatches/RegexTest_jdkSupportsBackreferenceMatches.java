package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that when jsoup is configured to use the JDK's own regex engine
 * (rather than the linear-time re2j engine), features that are exclusive to
 * the JDK engine remain available.
 *
 * <p>The feature exercised here is a <em>backreference</em>: {@code \1} inside a
 * pattern refers back to whatever the first capturing group matched. The re2j
 * engine does not support backreferences, so this test explicitly disables re2j
 * to confirm the JDK engine is in use.</p>
 */
public class RegexTest_jdkSupportsBackreferenceMatches {

    /** The re2j-enabled flag as it was before the test, restored afterwards. */
    private boolean originalUseRe2jSetting;

    @BeforeEach
    void rememberOriginalEngineSetting() {
        originalUseRe2jSetting = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreOriginalEngineSetting() {
        Regex.wantsRe2j(originalUseRe2jSetting);
    }

    @Test
    void jdkEngineMatchesPatternWithBackreference() {
        // Force the JDK regex engine, since re2j cannot handle backreferences.
        Regex.wantsRe2j(false);

        // "(\w+)\s+\1": a word, some whitespace, then the same word repeated.
        String patternWithBackreference = "(\\w+)\\s+\\1";
        String repeatedWord = "hello hello";

        Regex regex = Regex.compile(patternWithBackreference);
        Regex.Matcher matcher = regex.matcher(repeatedWord);

        assertTrue(matcher.find(),
            "JDK regex engine should match a repeated word via backreference \\1");
    }
}
