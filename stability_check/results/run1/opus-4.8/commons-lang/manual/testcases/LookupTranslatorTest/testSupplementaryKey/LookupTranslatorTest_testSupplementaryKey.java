package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link LookupTranslator} correctly handles a lookup key that is a
 * single Unicode supplementary character (one that requires two Java {@code char}s,
 * i.e. a surrogate pair, to represent).
 */
@Deprecated
public class LookupTranslatorTest_testSupplementaryKey extends AbstractLangTest {

    /** Mathematical double-struck capital X (U+1D54F) — a supplementary code point. */
    private static final int SUPPLEMENTARY_CODE_POINT = 0x1D54F;

    @Test
    void testSupplementaryKey() throws IOException {
        // The lookup key is a single supplementary code point encoded as a surrogate pair.
        final String key = new String(Character.toChars(SUPPLEMENTARY_CODE_POINT));
        final LookupTranslator translator =
                new LookupTranslator(new CharSequence[][] { { key, "X" } });

        // Translate the key at index 0 and capture the replacement in a StringWriter.
        final StringWriter out = new StringWriter();
        final int consumedCodePoints = translator.translate(key, 0, out);

        // The match spans a single code point, even though it occupies two chars.
        assertEquals(1, consumedCodePoints, "Incorrect code point consumption");
        assertEquals("X", out.toString(), "Incorrect value");

        // A trailing character after the key must be left untouched by the greedy match.
        assertEquals("XY", translator.translate(key + "Y"), "Trailing character must be preserved");
    }
}
