package org.jsoup.nodes;

import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_parseHtmlEncodedEmojiMultipoint {
    private static final String HTML_ENCODED_HUNDRED_POINTS_EMOJI = "&#55357;&#56495;";
    private static final String HUNDRED_POINTS_EMOJI = "\uD83D\uDCAF";

    @Test
    void parseHtmlEncodedEmojiMultipoint() {
        String decodedEmoji = Parser.unescapeEntities(HTML_ENCODED_HUNDRED_POINTS_EMOJI, false);

        assertEquals(HUNDRED_POINTS_EMOJI, decodedEmoji);
    }
}
