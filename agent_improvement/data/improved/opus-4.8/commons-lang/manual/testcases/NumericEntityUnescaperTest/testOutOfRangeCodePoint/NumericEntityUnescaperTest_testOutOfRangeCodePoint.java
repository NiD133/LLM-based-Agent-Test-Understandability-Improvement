package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link NumericEntityUnescaper} leaves numeric entities untouched
 * when their value falls outside the valid Unicode code point range.
 *
 * <p>Valid Unicode code points span {@code 0x0} to {@code 0x10FFFF}. Any numeric
 * entity that decodes to a larger value is not a legal code point, so the
 * unescaper must ignore it and return the input unchanged.</p>
 */
@Deprecated
public class NumericEntityUnescaperTest_testOutOfRangeCodePoint extends AbstractLangTest {

    /** The highest valid Unicode code point; anything above this is out of range. */
    private static final int MAX_VALID_CODE_POINT = 0x10FFFF;

    @Test
    void testOutOfRangeCodePoint() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

        // Each entity below decodes to a value greater than MAX_VALID_CODE_POINT,
        // so translate() must leave the original text unchanged.

        // 0x110000 = MAX_VALID_CODE_POINT + 1 (hexadecimal entity).
        assertEntityIsIgnored(unescaper, "&#x110000;");

        // 1114112 = 0x110000 in decimal (decimal entity).
        assertEntityIsIgnored(unescaper, "&#1114112;");

        // 0x7FFFFFFF = Integer.MAX_VALUE, far above the valid range (hexadecimal entity).
        assertEntityIsIgnored(unescaper, "&#x7FFFFFFF;");
    }

    /**
     * Asserts that the unescaper returns the given entity verbatim, confirming the
     * out-of-range code point was ignored rather than decoded.
     */
    private static void assertEntityIsIgnored(final NumericEntityUnescaper unescaper, final String entity) {
        assertEquals(entity, unescaper.translate(entity),
                "Failed to ignore code point above 0x" + Integer.toHexString(MAX_VALID_CODE_POINT).toUpperCase());
    }
}
