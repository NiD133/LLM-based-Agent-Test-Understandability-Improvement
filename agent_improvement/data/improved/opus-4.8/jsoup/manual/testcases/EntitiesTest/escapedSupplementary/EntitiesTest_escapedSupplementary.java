package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies how {@link Entities#escape(String, OutputSettings)} handles a supplementary
 * (astral-plane) character whose code point sits above the Basic Multilingual Plane.
 *
 * <p>The input is U+1D559 (MATHEMATICAL DOUBLE-STRUCK SMALL H), represented in Java as the
 * surrogate pair {@code 𝕙}. Each scenario below changes the output charset and
 * escape mode to confirm the character is escaped (or left intact) as expected.
 */
public class EntitiesTest_escapedSupplementary {

    /** U+1D559 (MATHEMATICAL DOUBLE-STRUCK SMALL H), encoded as a UTF-16 surrogate pair. */
    private static final String SUPPLEMENTARY_CHAR = "𝕙";

    @Test
    public void escapedSupplementary() {
        // ASCII cannot represent the character, and base mode has no named entity for it,
        // so it falls back to a numeric character reference.
        String asciiBase = Entities.escape(SUPPLEMENTARY_CHAR,
            new OutputSettings().charset("ascii").escapeMode(base));
        assertEquals("&#x1d559;", asciiBase);

        // ASCII still cannot represent the character, but extended mode knows a named
        // entity (&hopf;) for this code point, so it is preferred over the numeric form.
        String asciiExtended = Entities.escape(SUPPLEMENTARY_CHAR,
            new OutputSettings().charset("ascii").escapeMode(extended));
        assertEquals("&hopf;", asciiExtended);

        // UTF-8 can encode the character directly, so no escaping is needed.
        String utf8Extended = Entities.escape(SUPPLEMENTARY_CHAR,
            new OutputSettings().charset("UTF-8").escapeMode(extended));
        assertEquals(SUPPLEMENTARY_CHAR, utf8Extended);
    }
}
