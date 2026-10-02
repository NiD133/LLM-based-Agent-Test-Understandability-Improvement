package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that ASCII control characters (below U+0020, excluding tab/LF/CR) are handled correctly
 * when parsing HTML vs XML:
 *  - HTML keeps them escaped as hex entities (e.g. &#x1b; and &#x7;) for legibility.
 *  - XML drops them entirely, because they are not valid XML 1.0 characters.
 *
 * See https://github.com/jhy/jsoup/issues/1556
 */
public class EntitiesTest_controlCharactersAreEscaped {

    // ESC (U+001B) and BEL (U+0007) encoded as HTML hex entities
    private static final String ESC_ENTITY = "&#x1b;";
    private static final String BEL_ENTITY = "&#x7;";

    @Test
    public void controlCharactersAreEscaped() {
        // Input contains ESC and BEL in both an attribute value and in text content
        String input = "<a foo=\"" + ESC_ENTITY + "esc" + BEL_ENTITY + "bell\">Text " + ESC_ENTITY + " " + BEL_ENTITY + "</a>";

        // HTML parser: control characters are preserved as hex-escaped entities
        Document htmlDoc = Jsoup.parse(input);
        assertEquals(input, htmlDoc.body().html());

        // XML parser: control characters below U+0020 (except tab/LF/CR) are invalid in XML 1.0
        // and are silently dropped, leaving only the surrounding text
        Document xmlDoc = Jsoup.parse(input, "", Parser.xmlParser());
        assertEquals("<a foo=\"escbell\">Text  </a>", xmlDoc.html());
    }
}
