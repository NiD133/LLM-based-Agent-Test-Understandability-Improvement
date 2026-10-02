package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_notMissingMultis {

    /**
     * Verifies that "&nparsl;" -- a named entity that expands to two Unicode codepoints
     * (U+2AFD DOUBLE SOLIDUS OPERATOR followed by U+20E5 COMBINING REVERSE SOLIDUS OVERLAY)
     * -- is correctly unescaped to its two-character string representation.
     */
    @Test
    public void notMissingMultis() {
        String htmlEntity = "&nparsl;";
        String expectedUnescaped = "⫽⃥"; // U+2AFD + U+20E5

        assertEquals(expectedUnescaped, Entities.unescape(htmlEntity));
    }
}
