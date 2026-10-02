package org.apache.commons.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UnicodeEscaper#above(int)}.
 */
public class UnicodeEscaperTest_testAbove {

    /**
     * {@code UnicodeEscaper.above('F')} escapes every character whose code point is strictly
     * greater than {@code 'F'} (0x46) and leaves all others untouched.
     *
     * <p>For the input {@code "ADFGZ"}:</p>
     * <ul>
     *   <li>{@code 'A'} (0x41), {@code 'D'} (0x44), {@code 'F'} (0x46) are at or below the
     *       boundary, so they are kept verbatim.</li>
     *   <li>{@code 'G'} (0x47) and {@code 'Z'} (0x5A) are above the boundary, so they are
     *       replaced by their {@code \\uXXXX} escapes.</li>
     * </ul>
     */
    @Test
    void aboveEscapesOnlyCharactersGreaterThanBoundary() {
        final UnicodeEscaper escaperAboveF = UnicodeEscaper.above('F');

        final String escaped = escaperAboveF.translate("ADFGZ");

        assertEquals("ADF\\u0047\\u005A", escaped,
                "Characters above 'F' (G, Z) should be Unicode-escaped while A, D, F are kept");
    }
}
