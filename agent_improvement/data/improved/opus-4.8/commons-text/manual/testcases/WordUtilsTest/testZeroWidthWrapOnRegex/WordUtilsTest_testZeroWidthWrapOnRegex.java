package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link WordUtils#wrap(String, int, String, boolean, String)} does not hang when the
 * {@code wrapOn} pattern is a zero-width regex.
 *
 * <p>The lookahead {@code "(?=a)"} matches an empty string before each {@code 'a'}. A naive wrap
 * implementation could fail to advance past such a zero-width match and loop forever. This test
 * guards against that regression by requiring the call to finish quickly and return a non-null
 * result.</p>
 */
public class WordUtilsTest_testZeroWidthWrapOnRegex {

    /** Regex that matches the empty position immediately before each 'a' (a zero-width match). */
    private static final String ZERO_WIDTH_BREAK_PATTERN = "(?=a)";

    @Test
    void wrapWithZeroWidthRegexShouldCompleteWithoutLooping() {
        assertTimeout(Duration.ofSeconds(2), () -> {
            final String wrapped = WordUtils.wrap(
                    "abcdef",                  // text to wrap
                    3,                         // wrap length
                    "\n",                      // newline string
                    false,                     // do not wrap long words
                    ZERO_WIDTH_BREAK_PATTERN); // zero-width break pattern

            assertNotNull(wrapped);
        });
    }
}
