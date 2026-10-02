package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_unescapeMultiChars {

    @Test
    public void unescapeMultiChars() {
        String entityText = "&NestedGreaterGreater; &nGg; &nGt; &nGtv; &Gt; &gg;";
        String expectedUnescaped = "≫ ⋙̸ ≫⃒ ≫̸ ≫ ≫";

        String unescaped = Entities.unescape(entityText);
        assertEquals(expectedUnescaped, unescaped);

        String escaped = Entities.escape(
            expectedUnescaped,
            new OutputSettings().charset("ascii").escapeMode(extended)
        );
        assertEquals("&Gt; &Gg;&#x338; &Gt;&#x20d2; &Gt;&#x338; &Gt; &Gt;", escaped);
        assertEquals(expectedUnescaped, Entities.unescape(escaped));
    }
}
