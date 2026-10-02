package org.jsoup.nodes;

import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_parseHtmlEncodedEmoji {

    @Test
    void unescapesNumericEntityForEmojiBeyondBmp() {
        // The "hundred points" emoji 💯 is Unicode code point U+1F4AF, which lies
        // outside the Basic Multilingual Plane and so is encoded in HTML as the
        // decimal numeric character reference "&#128175;" (128175 == 0x1F4AF).
        String decimalNumericEntity = "&#128175;";

        // In Java a code point above U+FFFF is represented by a UTF-16 surrogate
        // pair; for U+1F4AF that pair is the high surrogate U+D83D and the low
        // surrogate U+DCAF.
        String expectedEmoji = "💯";

        // inAttribute = false: decode the entity as ordinary text content rather
        // than as an attribute value.
        String decoded = Parser.unescapeEntities(decimalNumericEntity, false);

        assertEquals(expectedEmoji, decoded);
    }
}
