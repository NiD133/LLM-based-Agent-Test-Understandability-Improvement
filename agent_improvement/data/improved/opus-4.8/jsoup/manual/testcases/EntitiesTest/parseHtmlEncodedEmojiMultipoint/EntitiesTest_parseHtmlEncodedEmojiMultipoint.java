package org.jsoup.nodes;

import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link Parser#unescapeEntities(String, boolean)} correctly decodes an emoji that is
 * written as two separate numeric character references — one for each half of a UTF-16 surrogate pair.
 */
public class EntitiesTest_parseHtmlEncodedEmojiMultipoint {

    @Test
    void parseHtmlEncodedEmojiMultipoint() {
        // The "hundred points" emoji 💯 (U+1F4AF) is encoded as two numeric character references:
        // &#55357; is the high surrogate (U+D83D) and &#56495; is the low surrogate (U+DCAF).
        String htmlEncodedEmoji = "&#55357;&#56495;";

        // The second argument (strict = false) makes the trailing ';' optional rather than required.
        String decoded = Parser.unescapeEntities(htmlEncodedEmoji, false);

        // Decoding rejoins the surrogate pair back into the single 💯 emoji (high + low surrogate).
        String expectedEmoji = "💯";
        assertEquals(expectedEmoji, decoded);
    }
}
