package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies how {@link Entities#escape} handles a supplementary (astral) character,
 * i.e. one whose code point is above U+FFFF and is therefore stored as a surrogate pair.
 *
 * The character under test is U+1D559 (MATHEMATICAL DOUBLE-STRUCK SMALL H, "𝕙"),
 * represented in Java as the surrogate pair {@code 𝕙}.
 */
public class EntitiesTest_escapedSupplementary {

    /** U+1D559 encoded as a Java surrogate pair. */
    private static final String DOUBLE_STRUCK_SMALL_H = "𝕙";

    @Test
    public void escapedSupplementary() {
        // ASCII output with the base entity set: no named entity available, so fall back
        // to a numeric character reference.
        String asciiBase = Entities.escape(DOUBLE_STRUCK_SMALL_H,
            new OutputSettings().charset("ascii").escapeMode(base));
        assertEquals("&#x1d559;", asciiBase);

        // ASCII output with the extended entity set: a named entity ("hopf") exists.
        String asciiExtended = Entities.escape(DOUBLE_STRUCK_SMALL_H,
            new OutputSettings().charset("ascii").escapeMode(extended));
        assertEquals("&hopf;", asciiExtended);

        // UTF-8 output: the character can be encoded directly, so it is left unchanged.
        String utf8Extended = Entities.escape(DOUBLE_STRUCK_SMALL_H,
            new OutputSettings().charset("UTF-8").escapeMode(extended));
        assertEquals(DOUBLE_STRUCK_SMALL_H, utf8Extended);
    }
}
