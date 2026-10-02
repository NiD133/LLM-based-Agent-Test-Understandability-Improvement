package org.jsoup.nodes;

import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_parseHtmlEncodedEmoji {

    // U+1F4AF "HUNDRED POINTS SYMBOL" (💯), encoded as a decimal HTML entity
    private static final String EMOJI_DECIMAL_ENTITY = "&#128175;";

    // U+1F4AF in UTF-16 requires a surrogate pair: \uD83D (high) + \uDCAF (low)
    private static final String EMOJI_UTF16 = "💯";

    @Test
    void parseHtmlEncodedEmoji() {
        // strict=false: trailing semicolon on the entity is optional
        String emoji = Parser.unescapeEntities(EMOJI_DECIMAL_ENTITY, false);
        assertEquals(EMOJI_UTF16, emoji);
    }
}
