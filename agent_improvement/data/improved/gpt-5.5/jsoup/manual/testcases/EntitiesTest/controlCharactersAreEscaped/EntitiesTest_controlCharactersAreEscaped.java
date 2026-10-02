package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_controlCharactersAreEscaped {
    private static final String INPUT_WITH_CONTROL_CHARACTER_REFERENCES =
        "<a foo=\"&#x1b;esc&#x7;bell\">Text &#x1b; &#x7;</a>";
    private static final String XML_WITH_CONTROL_CHARACTERS_REMOVED =
        "<a foo=\"escbell\">Text  </a>";

    @Test
    public void controlCharactersAreEscaped() {
        // https://github.com/jhy/jsoup/issues/1556
        // HTML output keeps control characters escaped for legibility.
        Document htmlDocument = Jsoup.parse(INPUT_WITH_CONTROL_CHARACTER_REFERENCES);
        assertEquals(INPUT_WITH_CONTROL_CHARACTER_REFERENCES, htmlDocument.body().html());

        // XML output removes control characters because they are not valid XML.
        Document xmlDocument = Jsoup.parse(INPUT_WITH_CONTROL_CHARACTER_REFERENCES, "", Parser.xmlParser());
        assertEquals(XML_WITH_CONTROL_CHARACTERS_REMOVED, xmlDocument.html());
    }
}
