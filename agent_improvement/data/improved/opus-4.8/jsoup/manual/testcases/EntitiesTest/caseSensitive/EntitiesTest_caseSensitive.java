package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that HTML entity escaping and unescaping are case-sensitive:
 * the uppercase form (e.g. {@code &Uuml;}) and the lowercase form (e.g. {@code &uuml;})
 * map to different characters, and only entities with the correct casing are recognised.
 */
public class EntitiesTest_caseSensitive {

    @Test
    public void caseSensitive() {
        // The extended escape mode (plus an ASCII charset, which forces non-ASCII
        // characters to be escaped) preserves the distinction between the uppercase
        // and lowercase entity names for U-umlaut.
        OutputSettings asciiExtended = new OutputSettings()
                .charset("ascii")
                .escapeMode(extended);

        String textWithBothCasings = "Ü ü & &";
        String expectedEscaped = "&Uuml; &uuml; &amp; &amp;";
        assertEquals(expectedEscaped, Entities.escape(textWithBothCasings, asciiExtended));

        // Unescaping decodes each entity back to its character. "&Uuml;" and "&uuml;"
        // resolve to the distinct upper/lower U-umlaut characters, and both the
        // semicolon-terminated "&amp;" and the legacy bare "&AMP" forms resolve to "&".
        String escapedInput = "&Uuml; &uuml; &amp; &AMP";
        String expectedUnescaped = "Ü ü & &";
        assertEquals(expectedUnescaped, Entities.unescape(escapedInput));
    }
}
