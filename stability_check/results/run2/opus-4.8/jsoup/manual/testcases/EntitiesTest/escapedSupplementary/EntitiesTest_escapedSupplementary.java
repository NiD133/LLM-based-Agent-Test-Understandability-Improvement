package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies how {@link Entities#escape} handles a supplementary (astral-plane) Unicode character,
 * which is stored in Java as a surrogate pair rather than a single {@code char}.
 */
public class EntitiesTest_escapedSupplementary {

    // U+1D559 MATHEMATICAL DOUBLE-STRUCK SMALL H, encoded as the surrogate pair D835 DD59.
    private static final String DOUBLE_STRUCK_SMALL_H = "𝕙";

    @Test
    public void escapedSupplementary() {
        // ASCII output can't represent the character, so with the base entity set it falls back
        // to a numeric character reference.
        String asciiWithBaseEntities = Entities.escape(
            DOUBLE_STRUCK_SMALL_H, new OutputSettings().charset("ascii").escapeMode(base));
        assertEquals("&#x1d559;", asciiWithBaseEntities);

        // The extended entity set knows a named reference for this character: &hopf;.
        String asciiWithExtendedEntities = Entities.escape(
            DOUBLE_STRUCK_SMALL_H, new OutputSettings().charset("ascii").escapeMode(extended));
        assertEquals("&hopf;", asciiWithExtendedEntities);

        // UTF-8 can encode the character directly, so it is left unescaped.
        String utf8WithExtendedEntities = Entities.escape(
            DOUBLE_STRUCK_SMALL_H, new OutputSettings().charset("UTF-8").escapeMode(extended));
        assertEquals(DOUBLE_STRUCK_SMALL_H, utf8WithExtendedEntities);
    }
}
