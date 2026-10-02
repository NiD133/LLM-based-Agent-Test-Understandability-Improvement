package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_notMissingSupplementals {

    @Test
    public void notMissingSupplementals() {
        String text = "&npolint; &qfr;";
        // 𝔮
        String un = "⨔ \uD835\uDD2E";
        assertEquals(un, Entities.unescape(text));
    }
}
