package org.jsoup.nodes;

import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that Entities.escape() correctly handles Unicode supplementary characters
 * (codepoints above U+FFFF, which require two Java chars / a surrogate pair).
 */
public class EntitiesTest_escapeSupplementaryCharacter {

    // U+210C1 is a supplementary codepoint (> U+FFFF) that needs a surrogate pair in Java.
    // In decimal this is 135361, the value used by the original test.
    private static final int SUPPLEMENTARY_CODEPOINT = 135361; // 0x210C1
    private static final String EXPECTED_HEX_ENTITY   = "&#x210c1;";

    @Test
    public void escapeSupplementaryCharacter() {
        // Build the two-char Java String that represents the supplementary codepoint.
        String supplementaryChar = new String(Character.toChars(SUPPLEMENTARY_CODEPOINT));

        // When the output charset is ASCII it cannot encode the codepoint, so jsoup must
        // fall back to a numeric hex character reference.
        OutputSettings asciiSettings = new OutputSettings().charset("ascii").escapeMode(base);
        String escapedAsAscii = Entities.escape(supplementaryChar, asciiSettings);
        assertEquals(EXPECTED_HEX_ENTITY, escapedAsAscii,
            "ASCII output should encode the supplementary char as a hex entity reference");

        // When the output charset is UTF-8 it can encode every Unicode codepoint directly,
        // so the character should be left unchanged (no escaping required).
        OutputSettings utf8Settings = new OutputSettings().charset("UTF-8").escapeMode(base);
        String escapedAsUtf8 = Entities.escape(supplementaryChar, utf8Settings);
        assertEquals(supplementaryChar, escapedAsUtf8,
            "UTF-8 output should pass the supplementary char through unchanged");
    }
}
