package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_caseSensitive {

    @Test
    public void caseSensitive() {
        // Escape: uppercase Ü maps to &Uuml; and lowercase ü maps to &uuml;,
        // confirming that entity names are case-sensitive
        String input = "Ü ü & &";
        OutputSettings asciiExtended = new OutputSettings().charset("ascii").escapeMode(extended);
        String escaped = Entities.escape(input, asciiExtended);
        assertEquals("&Uuml; &uuml; &amp; &amp;", escaped);

        // Unescape: &Uuml; and &uuml; decode back to their respective characters;
        // &AMP without a trailing semicolon is still recognized and decoded to &
        String htmlWithMixedCaseEntities = "&Uuml; &uuml; &amp; &AMP";
        String unescaped = Entities.unescape(htmlWithMixedCaseEntities);
        assertEquals("Ü ü & &", unescaped);
    }
}
