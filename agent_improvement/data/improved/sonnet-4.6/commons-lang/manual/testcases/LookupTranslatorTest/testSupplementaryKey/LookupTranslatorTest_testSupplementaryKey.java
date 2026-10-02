package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link LookupTranslator} correctly handles supplementary (non-BMP)
 * Unicode code points as lookup keys. Supplementary characters are represented
 * as two Java {@code char} values (a surrogate pair) but count as a single
 * Unicode code point. The translator must consume exactly one code point per
 * match and must not accidentally consume any character that follows the key.
 */
@Deprecated
public class LookupTranslatorTest_testSupplementaryKey extends AbstractLangTest {

    /**
     * U+1D54F MATHEMATICAL DOUBLE-STRUCK CAPITAL X — a supplementary code point
     * (outside the Basic Multilingual Plane) that Java encodes as a two-{@code char}
     * surrogate pair. Chosen as the lookup key so the test exercises the boundary
     * between single-char BMP characters and two-char supplementary characters.
     */
    private static final int SUPPLEMENTARY_CODE_POINT = 0x1D54F;

    /**
     * A supplementary key must count as exactly one Unicode code point when consumed,
     * even though it occupies two {@code char} slots in the underlying Java string.
     */
    private static final int EXPECTED_CODE_POINTS_CONSUMED = 1;

    @Test
    void testSupplementaryKey() throws IOException {
        // Build the supplementary key as a Java string: two chars (surrogate pair), one code point.
        final String supplementaryKey = new String(Character.toChars(SUPPLEMENTARY_CODE_POINT));

        // Create a translator that maps the supplementary key to the ASCII letter "X".
        final LookupTranslator translator = new LookupTranslator(
                new CharSequence[][] { { supplementaryKey, "X" } });

        // --- Verify that the key translates correctly ---

        final StringWriter output = new StringWriter();
        final int codePointsConsumed = translator.translate(supplementaryKey, 0, output);

        // The translator must report consumption in code points, not in Java chars:
        // the two-char surrogate pair is a single code point, so the return value must be 1.
        assertEquals(EXPECTED_CODE_POINTS_CONSUMED, codePointsConsumed,
                "Incorrect code point consumption");

        // The translator must write the mapped replacement value.
        assertEquals("X", output.toString(), "Incorrect value");

        // --- Verify that the key does not over-consume the character that follows it ---
        // If the translator wrongly consumed the char after the surrogate pair,
        // the trailing 'Y' would be lost and the result would be just "X".
        assertEquals("XY", translator.translate(supplementaryKey + "Y"),
                "Trailing character must be preserved");
    }
}
