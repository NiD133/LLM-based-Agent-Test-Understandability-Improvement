package org.jsoup.nodes;

import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_parseHtmlEncodedEmojiMultipoint {

    // U+1F4AF (💯) encoded as two HTML decimal numeric references for its UTF-16 surrogate pair:
    //   &#55357; = 0xD83D (high surrogate), &#56495; = 0xDCAF (low surrogate)
    private static final String ENCODED_EMOJI_SURROGATE_PAIR = "&#55357;&#56495;";

    // The decoded Java string for U+1F4AF (💯), represented as a UTF-16 surrogate pair
    private static final String HUNDRED_POINTS_EMOJI = "💯";

    @Test
    void parseHtmlEncodedEmojiMultipoint() {
        String decoded = Parser.unescapeEntities(ENCODED_EMOJI_SURROGATE_PAIR, false);
        assertEquals(HUNDRED_POINTS_EMOJI, decoded);
    }
}
