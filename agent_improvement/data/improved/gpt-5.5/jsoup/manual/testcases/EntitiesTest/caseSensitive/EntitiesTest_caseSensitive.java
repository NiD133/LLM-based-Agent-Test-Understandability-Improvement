package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_caseSensitive {
    private static final String ASCII_CHARSET = "ascii";
    private static final OutputSettings ASCII_EXTENDED_OUTPUT =
        new OutputSettings().charset(ASCII_CHARSET).escapeMode(extended);

    @Test
    public void caseSensitive() {
        String mixedCaseUmlautsAndAmpersands = "Ü ü & &";
        String escapedMixedCaseEntities = "&Uuml; &uuml; &amp; &AMP";

        assertEquals(
            "&Uuml; &uuml; &amp; &amp;",
            Entities.escape(mixedCaseUmlautsAndAmpersands, ASCII_EXTENDED_OUTPUT)
        );
        assertEquals("Ü ü & &", Entities.unescape(escapedMixedCaseEntities));
    }
}
