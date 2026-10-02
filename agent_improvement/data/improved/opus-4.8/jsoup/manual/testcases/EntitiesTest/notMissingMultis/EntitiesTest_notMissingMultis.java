package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_notMissingMultis {

    /**
     * The named entity {@code &nparsl;} maps to two code points rather than one.
     * Verifies that unescaping keeps both characters of such multi-character entities.
     */
    @Test
    public void notMissingMultis() {
        String escapedEntity = "&nparsl;";
        // U+2AFD (DOUBLE SOLIDUS OPERATOR) followed by U+20E5 (COMBINING REVERSE SOLIDUS OVERLAY)
        String expectedTwoCharacters = "⫽⃥";

        assertEquals(expectedTwoCharacters, Entities.unescape(escapedEntity));
    }
}
