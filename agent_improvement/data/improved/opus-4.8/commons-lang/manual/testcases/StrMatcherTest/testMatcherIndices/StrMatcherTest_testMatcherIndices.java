package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests how {@link StrMatcher#isMatch(char[], int, int, int)} interprets the
 * {@code pos} and {@code bufferEnd} indices.
 * <p>
 * The matcher under test looks for the literal string {@code "bc"}. The text
 * being scanned is {@code "abcdef"}, where {@code "bc"} starts at index 1.
 * The contract of {@code isMatch} puts the burden of supplying valid indices on
 * the caller, so these scenarios only exercise valid (in-bounds) inputs.
 */
@Deprecated
public class StrMatcherTest_testMatcherIndices extends AbstractLangTest {

    /** Text to scan; the target string "bc" begins at index 1. */
    private static final char[] TEXT = "abcdef".toCharArray();

    /** Index in TEXT where the target string "bc" begins. */
    private static final int MATCH_START = 1;

    @Test
    void testMatcherIndices() {
        final StrMatcher bcMatcher = StrMatcher.stringMatcher("bc");

        // Scanning the full buffer finds "bc" at index 1 -> 2 chars matched.
        assertEquals(2, bcMatcher.isMatch(TEXT, MATCH_START, 0, TEXT.length));

        // A bufferEnd of 3 still leaves room for both 'b' and 'c' -> match of 2.
        assertEquals(2, bcMatcher.isMatch(TEXT, MATCH_START, 0, 3));

        // A bufferEnd of 2 cuts off the 'c', so "bc" no longer fits -> no match.
        assertEquals(0, bcMatcher.isMatch(TEXT, MATCH_START, 0, 2));
    }
}
