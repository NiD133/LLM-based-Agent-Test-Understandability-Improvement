package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that the JDK regex engine (not re2j) correctly handles backreferences,
 * which re2j does not support.
 */
public class RegexTest_jdkSupportsBackreferenceMatches {

    private boolean originalUseRe2j;

    @BeforeEach
    void saveRe2jSetting() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void restoreRe2jSetting() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @Test
    void jdkSupportsBackreferenceMatches() {
        // Force JDK engine so backreferences (unsupported by re2j) are exercised
        Regex.wantsRe2j(false);

        // Backreference \1 requires the same word to appear twice, e.g. "hello hello"
        String repeatedWordPattern = "(\\w+)\\s+\\1";
        String inputWithRepeatedWord = "hello hello";

        Regex regex = Regex.compile(repeatedWordPattern);
        Regex.Matcher matcher = regex.matcher(inputWithRepeatedWord);

        assertTrue(matcher.find(), "JDK engine should match repeated word via backreference");
    }
}
