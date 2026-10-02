package org.jsoup.nodes;

import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_parseHtmlEncodedEmoji {

    @Test
    void parseHtmlEncodedEmoji() {
        String encodedEmoji = "&#128175;";
        String expectedEmoji = "\uD83D\uDCAF";

        String decodedEmoji = Parser.unescapeEntities(encodedEmoji, false);

        assertEquals(expectedEmoji, decodedEmoji);
    }
}
