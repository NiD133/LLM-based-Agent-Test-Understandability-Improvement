package org.jsoup.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that the JDK regex engine correctly handles backreference patterns,
 * which re2j does not support. The suite temporarily disables re2j to force
 * JDK engine usage, and restores the original preference afterwards.
 */
public class RegexTest_jdkSupportsBackreferenceMatches {

    // Capture the current preference so the test can be safely isolated
    private boolean originalUseRe2j;

    @BeforeEach
    void setUp() {
        originalUseRe2j = Regex.wantsRe2j();
    }

    @AfterEach
    void tearDown() {
        Regex.wantsRe2j(originalUseRe2j);
    }

    @Test
    @DisplayName("JDK engine matches input that repeats a captured word (backreference \\1)")
    void jdkSupportsBackreferenceMatches() {
        // Force the JDK engine; re2j does not support backreferences
        Regex.wantsRe2j(false);

        // Pattern: capture a word, require whitespace, then the same word again via \1
        String backreferencePattern = "(\\w+)\\s+\\1";
        String repeatedWordInput    = "hello hello";

        Regex regex = Regex.compile(backreferencePattern);
        Regex.Matcher matcher = regex.matcher(repeatedWordInput);

        assertTrue(matcher.find(),
            "JDK regex should match repeated word using backreference \\1");
    }
}
