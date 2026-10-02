package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link LookupTranslator} correctly handles lookup keys composed of
 * supplementary Unicode code points (characters outside the Basic Multilingual
 * Plane that are represented by two {@code char} values in Java's UTF-16 encoding).
 *
 * <p>A supplementary code point occupies two {@code char} slots in a Java
 * {@code String}, but it still counts as a single logical code point.  The
 * translator must return 1 (one code point consumed) rather than 2 (two chars
 * consumed), and it must not accidentally swallow a character that follows the
 * matched key.
 */
@Deprecated
public class LookupTranslatorTest_testSupplementaryKey extends AbstractLangTest {

    /**
     * U+1D54F – a supplementary code point that lies outside the Basic
     * Multilingual Plane and therefore requires a surrogate pair (two {@code char}
     * values) in Java's internal UTF-16 representation.
     */
    private static final String SUPPLEMENTARY_KEY = new String(Character.toChars(0x1D54F));

    /** Replacement string mapped to {@link #SUPPLEMENTARY_KEY} in the lookup table. */
    private static final String REPLACEMENT = "X";

    /** A trailing ASCII character appended after the key to verify it is not consumed. */
    private static final String TRAILING_CHAR = "Y";

    @Test
    void testSupplementaryKey() throws IOException {
        // Build a translator that maps the supplementary key to REPLACEMENT.
        final LookupTranslator translator =
                new LookupTranslator(new CharSequence[][] { { SUPPLEMENTARY_KEY, REPLACEMENT } });

        // --- Verify single-key translation ---
        // translate(CharSequence, int, Writer) must report exactly 1 code point consumed,
        // even though the supplementary character occupies 2 char slots in the string.
        final StringWriter out = new StringWriter();
        final int codePointsConsumed = translator.translate(SUPPLEMENTARY_KEY, 0, out);

        assertEquals(1, codePointsConsumed,
                "Supplementary key must be counted as 1 code point, not 2 chars");
        assertEquals(REPLACEMENT, out.toString(),
                "Supplementary key must be translated to its mapped replacement");

        // --- Verify the translator does not over-consume the input ---
        // When a character follows the key, it must appear unchanged in the output.
        final String inputWithTrailing = SUPPLEMENTARY_KEY + TRAILING_CHAR;
        assertEquals(REPLACEMENT + TRAILING_CHAR,
                translator.translate(inputWithTrailing),
                "Character immediately after the matched supplementary key must be preserved");
    }
}
