package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies how jsoup handles ASCII control characters that are written as numeric
 * character references in the input (e.g. {@code &#x1b;} = ESC, {@code &#x7;} = BELL).
 *
 * <p>Expected behaviour (see https://github.com/jhy/jsoup/issues/1556):
 * <ul>
 *   <li>HTML output: control characters are kept but re-escaped as references, for legibility.</li>
 *   <li>XML output: control characters are dropped entirely, because they are not valid XML chars.</li>
 * </ul>
 */
public class EntitiesTest_controlCharactersAreEscaped {

    // Input contains ESC (&#x1b;) and BELL (&#x7;) control chars in both an attribute and text.
    private static final String INPUT_WITH_CONTROL_CHARS =
        "<a foo=\"&#x1b;esc&#x7;bell\">Text &#x1b; &#x7;</a>";

    @Test
    public void htmlOutputReEscapesControlCharacters() {
        Document doc = Jsoup.parse(INPUT_WITH_CONTROL_CHARS);

        // HTML output preserves the control characters as escaped references, unchanged from the input.
        assertEquals(INPUT_WITH_CONTROL_CHARS, doc.body().html());
    }

    @Test
    public void xmlOutputDropsControlCharacters() {
        Document xml = Jsoup.parse(INPUT_WITH_CONTROL_CHARS, "", Parser.xmlParser());

        // XML output removes the control characters, leaving the surrounding text in place.
        String expectedXml = "<a foo=\"escbell\">Text  </a>";
        assertEquals(expectedXml, xml.html());
    }
}
