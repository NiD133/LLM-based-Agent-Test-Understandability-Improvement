package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LookupTranslator} handles a lookup key made of a single
 * Unicode supplementary code point (one that lives outside the Basic Multilingual
 * Plane and is therefore encoded as a surrogate pair in a Java {@code String}).
 */
@Deprecated
public class LookupTranslatorTest_testSupplementaryKey extends AbstractLangTest {

    /** U+1D54F MATHEMATICAL DOUBLE-STRUCK CAPITAL X, a supplementary code point. */
    private static final int SUPPLEMENTARY_CODE_POINT = 0x1D54F;

    /** The supplementary code point as a String (a surrogate pair, i.e. two chars). */
    private static final String KEY = new String(Character.toChars(SUPPLEMENTARY_CODE_POINT));

    /** Value the translator should emit whenever it matches {@link #KEY}. */
    private static final String REPLACEMENT = "X";

    @Test
    void testSupplementaryKey() throws IOException {
        final LookupTranslator translator =
                new LookupTranslator(new CharSequence[][] { { KEY, REPLACEMENT } });

        // Translating the key on its own should consume exactly one code point
        // (even though it spans two chars) and write the replacement.
        final StringWriter out = new StringWriter();
        final int codePointsConsumed = translator.translate(KEY, 0, out);

        assertEquals(1, codePointsConsumed, "Incorrect code point consumption");
        assertEquals(REPLACEMENT, out.toString(), "Incorrect value");

        // A trailing character after the matched key must be left untouched.
        assertEquals("XY", translator.translate(KEY + "Y"),
                "Trailing character must be preserved");
    }
}
