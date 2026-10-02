package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSet#getInstance(String...)}.
 *
 * <p>The factory method recognizes a fixed set of "common" patterns and returns
 * the shared, cached {@link CharSet} constants for them. This test verifies that
 * each recognized pattern maps to its expected constant, using {@code assertSame}
 * to confirm the exact cached instance (not merely an equal one) is returned.</p>
 */
public class CharSetTest_testGetInstance extends AbstractLangTest {

    @Test
    void testGetInstance() {
        // A null or empty definition yields the shared EMPTY constant.
        assertSame(CharSet.EMPTY, CharSet.getInstance((String) null));
        assertSame(CharSet.EMPTY, CharSet.getInstance((String[]) null));
        assertSame(CharSet.EMPTY, CharSet.getInstance(null));
        assertSame(CharSet.EMPTY, CharSet.getInstance(""));

        // "a-zA-Z" and its reordered equivalent "A-Za-z" both map to ASCII_ALPHA.
        assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("a-zA-Z"));
        assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("A-Za-z"));

        // Single-case and numeric ranges map to their dedicated constants.
        assertSame(CharSet.ASCII_ALPHA_LOWER, CharSet.getInstance("a-z"));
        assertSame(CharSet.ASCII_ALPHA_UPPER, CharSet.getInstance("A-Z"));
        assertSame(CharSet.ASCII_NUMERIC, CharSet.getInstance("0-9"));
    }
}
