package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link StringUtil#padding(int)} and {@link StringUtil#padding(int, int)}, which return a
 * string consisting of {@code width} spaces, capped at a maximum padding width.
 *
 * <p>Key behaviours under test:
 * <ul>
 *   <li>{@code padding(width)} delegates to {@code padding(width, 30)}, so it caps at 30 spaces.</li>
 *   <li>Results for widths 0..20 are served from a memoised cache and returned before the cap is
 *       applied, so for those widths {@code maxPaddingWidth} is effectively ignored.</li>
 *   <li>Widths of 21 or more bypass the cache and honour {@code maxPaddingWidth}
 *       ({@code -1} means unlimited).</li>
 * </ul>
 */
public class StringUtilTest_padding {

    /** Builds the expected result: a string of exactly {@code count} spaces. */
    private static String spaces(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++)
            sb.append(' ');
        return sb.toString();
    }

    @Test
    public void padding() {
        // Single-argument padding: caps at the default maximum of 30 spaces.
        assertEquals(spaces(0), StringUtil.padding(0));
        assertEquals(spaces(1), StringUtil.padding(1));
        assertEquals(spaces(2), StringUtil.padding(2));
        assertEquals(spaces(15), StringUtil.padding(15));
        assertEquals(spaces(30), StringUtil.padding(45)); // requested 45, but the default cap is 30

        // Unlimited cap (-1): cached widths 0..20 return as requested (the cap is irrelevant here).
        assertEquals(spaces(0), StringUtil.padding(0, -1));
        assertEquals(spaces(20), StringUtil.padding(20, -1));

        // Unlimited cap (-1) for non-cached widths (21+): the full requested width is returned.
        assertEquals(spaces(21), StringUtil.padding(21, -1));
        assertEquals(spaces(30), StringUtil.padding(30, -1));
        assertEquals(spaces(45), StringUtil.padding(45, -1));

        // Zero cap: width 0 yields "" (also a cache hit); width 21 bypasses the cache and is capped to "".
        assertEquals(spaces(0), StringUtil.padding(0, 0));
        assertEquals(spaces(0), StringUtil.padding(21, 0));

        // Explicit cap of 30 mirrors the single-argument behaviour: widths above 30 are capped to 30.
        assertEquals(spaces(0), StringUtil.padding(0, 30));
        assertEquals(spaces(1), StringUtil.padding(1, 30));
        assertEquals(spaces(2), StringUtil.padding(2, 30));
        assertEquals(spaces(15), StringUtil.padding(15, 30));
        assertEquals(spaces(30), StringUtil.padding(45, 30));

        // The cap applies even though width 20 would otherwise be a cache hit: capped to length 5.
        assertEquals(5, StringUtil.padding(20, 5).length());
    }
}
