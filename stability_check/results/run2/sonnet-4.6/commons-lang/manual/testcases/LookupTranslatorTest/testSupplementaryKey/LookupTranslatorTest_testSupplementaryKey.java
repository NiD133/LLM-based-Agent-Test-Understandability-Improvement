package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class LookupTranslatorTest_testSupplementaryKey extends AbstractLangTest {

    // U+1D54F MATHEMATICAL DOUBLE-STRUCK CAPITAL X — a supplementary (non-BMP)
    // code point that Java encodes as a surrogate pair (two char values).
    private static final int SUPPLEMENTARY_CODE_POINT = 0x1D54F;

    @Test
    void testSupplementaryKey() throws IOException {
        // Build a key that is a single supplementary code point (2 Java chars).
        final String supplementaryKey = new String(Character.toChars(SUPPLEMENTARY_CODE_POINT));

        // Map the supplementary key to a single ASCII replacement character.
        final LookupTranslator translator =
                new LookupTranslator(new CharSequence[][] { { supplementaryKey, "X" } });

        // --- Verify single-key translation via the low-level translate() call ---

        final StringWriter output = new StringWriter();
        // translate() returns the number of *code points* consumed, not char values.
        // A supplementary code point occupies 2 chars but counts as 1 code point,
        // so we expect the return value to be 1.
        final int codePointsConsumed = translator.translate(supplementaryKey, 0, output);
        assertEquals(1, codePointsConsumed, "Supplementary key should count as one code point");
        assertEquals("X", output.toString(), "Supplementary key should translate to its mapped value");

        // --- Verify that matching the supplementary key does not consume the character
        //     that follows it in the input string ---

        // If the translator incorrectly treats the surrogate pair as two separate
        // matches, or consumes one char too many, "Y" would be lost or duplicated.
        assertEquals("XY", translator.translate(supplementaryKey + "Y"),
                "Character following the supplementary key must be preserved in output");
    }
}
