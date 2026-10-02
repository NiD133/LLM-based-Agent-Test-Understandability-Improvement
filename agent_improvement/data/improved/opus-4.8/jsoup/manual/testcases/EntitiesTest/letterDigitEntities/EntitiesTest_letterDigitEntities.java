package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies how jsoup escapes "letter-digit" named entities (such as {@code &sup1;} and
 * {@code &frac12;}) when serializing a parsed element under different output charsets.
 */
public class EntitiesTest_letterDigitEntities {

    @Test
    public void letterDigitEntities() {
        // Markup containing the superscript (sup1-3) and vulgar-fraction (frac14, frac12, frac34) entities.
        String html = "<p>&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;</p>";
        Document doc = Jsoup.parse(html);
        Element paragraph = doc.select("p").first();

        // The characters these entities represent, as the parser decodes them.
        String decodedText = "¹²³¼½¾";
        // The same characters re-encoded back to their named entities.
        String encodedAsEntities = "&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;";

        // With an ASCII charset the characters can't be represented directly, so html() falls
        // back to the named entities, while text() still exposes the decoded characters.
        doc.outputSettings().charset("ascii");
        assertEquals(encodedAsEntities, paragraph.html());
        assertEquals(decodedText, paragraph.text());

        // With a UTF-8 charset the characters can be represented directly, so html() emits them as-is.
        doc.outputSettings().charset("UTF-8");
        assertEquals(decodedText, paragraph.html());
    }
}
