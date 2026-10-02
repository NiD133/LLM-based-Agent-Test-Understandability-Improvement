package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escapedSupplementary {

    // U+1D559 MATHEMATICAL DOUBLE-STRUCK SMALL H ("𝕙"), encoded as a UTF-16 surrogate pair.
    // This is a supplementary character (above U+FFFF) used to verify correct surrogate-pair handling.
    private static final String SUPPLEMENTARY_CHAR = "𝕙";

    @Test
    public void escapedSupplementary() {
        // When the output charset is ASCII and escape mode is 'base', supplementary characters
        // cannot be represented directly, so they must be encoded as a hex numeric character reference.
        String escapedAsciiBase = Entities.escape(SUPPLEMENTARY_CHAR,
                new OutputSettings().charset("ascii").escapeMode(base));
        assertEquals("&#x1d559;", escapedAsciiBase,
                "Supplementary char should be encoded as hex numeric reference in ASCII/base mode");

        // When the output charset is ASCII and escape mode is 'extended', the character should be
        // represented using its named HTML entity (&hopf; = mathematical double-struck small h).
        String escapedAsciiExtended = Entities.escape(SUPPLEMENTARY_CHAR,
                new OutputSettings().charset("ascii").escapeMode(extended));
        assertEquals("&hopf;", escapedAsciiExtended,
                "Supplementary char should be encoded as named entity &hopf; in ASCII/extended mode");

        // When the output charset is UTF-8, the character can be encoded directly, so no escaping
        // should occur regardless of the escape mode setting.
        String escapedUtf8Extended = Entities.escape(SUPPLEMENTARY_CHAR,
                new OutputSettings().charset("UTF-8").escapeMode(extended));
        assertEquals(SUPPLEMENTARY_CHAR, escapedUtf8Extended,
                "Supplementary char should pass through unescaped when UTF-8 can represent it");
    }
}
