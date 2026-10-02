package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LookupTranslator} handles a lookup key made of a single
 * Unicode supplementary character (a code point outside the Basic Multilingual
 * Plane, encoded as a surrogate pair in Java's UTF-16 strings).
 */
@Deprecated
public class LookupTranslatorTest_testSupplementaryKey extends AbstractLangTest {

    /** Mathematical double-struck capital X (U+1D54F), a supplementary code point. */
    private static final int SUPPLEMENTARY_CODE_POINT = 0x1D54F;

    /** The key: one supplementary character, stored as a two-char surrogate pair. */
    private static final String SUPPLEMENTARY_KEY =
            new String(Character.toChars(SUPPLEMENTARY_CODE_POINT));

    /** The replacement that the key maps to. */
    private static final String REPLACEMENT = "X";

    @Test
    void testSupplementaryKey() throws IOException {
        final LookupTranslator translator =
                new LookupTranslator(new CharSequence[][] { { SUPPLEMENTARY_KEY, REPLACEMENT } });

        // Translating the key alone should write the replacement and report that
        // exactly one code point (not two chars) was consumed.
        final StringWriter out = new StringWriter();
        final int codePointsConsumed = translator.translate(SUPPLEMENTARY_KEY, 0, out);

        assertEquals(1, codePointsConsumed, "Incorrect code point consumption");
        assertEquals(REPLACEMENT, out.toString(), "Incorrect value");

        // The greedy match must stop at the key and leave the trailing 'Y' untouched.
        assertEquals("XY", translator.translate(SUPPLEMENTARY_KEY + "Y"),
                "Trailing character must be preserved");
    }
}
