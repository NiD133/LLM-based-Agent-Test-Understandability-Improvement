package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_letterDigitEntities {
    private static final String LETTER_DIGIT_ENTITY_HTML = "<p>&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;</p>";
    private static final String SERIALIZED_ASCII_ENTITIES = "&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;";
    private static final String DECODED_TEXT = "¹²³¼½¾";

    @Test
    public void letterDigitEntities() {
        Document doc = Jsoup.parse(LETTER_DIGIT_ENTITY_HTML);

        doc.outputSettings().charset("ascii");
        Element paragraph = doc.select("p").first();

        assertEquals(
            SERIALIZED_ASCII_ENTITIES,
            paragraph.html(),
            "ASCII output should preserve named entities whose names end in digits."
        );
        assertEquals(
            DECODED_TEXT,
            paragraph.text(),
            "Parsed paragraph text should contain the decoded entity characters."
        );

        doc.outputSettings().charset("UTF-8");
        assertEquals(
            DECODED_TEXT,
            paragraph.html(),
            "UTF-8 output should emit the decoded characters directly."
        );
    }
}
