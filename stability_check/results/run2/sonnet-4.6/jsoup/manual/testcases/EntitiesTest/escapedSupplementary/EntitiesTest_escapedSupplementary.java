package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that supplementary (non-BMP) Unicode characters are correctly escaped
 * depending on the output charset and escape mode.
 *
 * The character under test is U+1D559 (MATHEMATICAL DOUBLE-STRUCK SMALL H),
 * stored as the UTF-16 surrogate pair 𝕙.
 */
public class EntitiesTest_escapedSupplementary {

    // U+1D559 MATHEMATICAL DOUBLE-STRUCK SMALL H — a supplementary codepoint
    // that requires a surrogate pair in Java's UTF-16 String representation.
    private static final String DOUBLE_STRUCK_SMALL_H = "𝕙";

    @Test
    public void escapedSupplementary() {
        // ASCII + base mode: U+1D559 has no name in the base entity set,
        // so the encoder falls back to a hex numeric character reference.
        String escapedAsciiBase = Entities.escape(
            DOUBLE_STRUCK_SMALL_H,
            new OutputSettings().charset("ascii").escapeMode(base));
        assertEquals("&#x1d559;", escapedAsciiBase);

        // ASCII + extended mode: the full HTML5 entity table maps U+1D559
        // to the named entity &hopf;, so that name is used instead of a numeric ref.
        String escapedAsciiExtended = Entities.escape(
            DOUBLE_STRUCK_SMALL_H,
            new OutputSettings().charset("ascii").escapeMode(extended));
        assertEquals("&hopf;", escapedAsciiExtended);

        // UTF-8 + extended mode: UTF-8 can encode U+1D559 directly,
        // so no escaping is needed and the original character is returned unchanged.
        String escapedUtf8Extended = Entities.escape(
            DOUBLE_STRUCK_SMALL_H,
            new OutputSettings().charset("UTF-8").escapeMode(extended));
        assertEquals(DOUBLE_STRUCK_SMALL_H, escapedUtf8Extended);
    }
}
