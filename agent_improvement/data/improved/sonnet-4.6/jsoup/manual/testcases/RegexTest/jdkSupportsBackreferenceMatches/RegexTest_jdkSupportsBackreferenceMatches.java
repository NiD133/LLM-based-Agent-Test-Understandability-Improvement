package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that the JDK regex engine (not re2j) correctly handles backreferences.
 * Backreferences like \1 refer to a previously captured group; re2j does NOT support them,
 * so these tests explicitly disable re2j to exercise the JDK engine path.
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
        // Force JDK engine because re2j does not support backreferences
        Regex.wantsRe2j(false);

        // Pattern: capture a word (\w+), skip whitespace (\s+), then require the same word again (\1)
        String pattern = "(\\w+)\\s+\\1";
        String input = "hello hello"; // repeated word satisfies the backreference

        Regex regex = Regex.compile(pattern);
        Regex.Matcher matcher = regex.matcher(input);

        assertTrue(matcher.find(), "JDK regex should match a repeated word via backreference \\1");
    }
}
