package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escapedSupplementary {

    // U+1D559: MATHEMATICAL DOUBLE-STRUCK SMALL H (𝕙), a supplementary character (surrogate pair) outside the BMP
    private static final String SUPPLEMENTARY_CHAR = "𝕙";

    @Test
    public void escapedSupplementary_asciiBaseMode_producesNumericHexEntity() {
        // ASCII charset cannot encode supplementary characters; base mode falls back to numeric hex escape &#x...;
        String escaped = Entities.escape(SUPPLEMENTARY_CHAR, new OutputSettings().charset("ascii").escapeMode(base));
        assertEquals("&#x1d559;", escaped);
    }

    @Test
    public void escapedSupplementary_asciiExtendedMode_producesNamedEntity() {
        // ASCII charset cannot encode it, but extended mode knows the named entity &hopf; for this codepoint
        String escaped = Entities.escape(SUPPLEMENTARY_CHAR, new OutputSettings().charset("ascii").escapeMode(extended));
        assertEquals("&hopf;", escaped);
    }

    @Test
    public void escapedSupplementary_utf8ExtendedMode_returnsOriginalCharacter() {
        // UTF-8 can represent supplementary characters directly, so no escaping is needed
        String escaped = Entities.escape(SUPPLEMENTARY_CHAR, new OutputSettings().charset("UTF-8").escapeMode(extended));
        assertEquals(SUPPLEMENTARY_CHAR, escaped);
    }
}
