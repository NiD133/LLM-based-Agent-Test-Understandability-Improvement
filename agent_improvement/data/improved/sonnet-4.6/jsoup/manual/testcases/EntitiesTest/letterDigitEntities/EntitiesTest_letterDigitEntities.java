package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that HTML named entities whose names contain letters AND digits
 * (e.g. sup1, frac14) survive a parse-and-serialise round-trip correctly,
 * both when the output charset is ASCII (entities must be re-emitted) and
 * when it is UTF-8 (characters can be written verbatim).
 */
public class EntitiesTest_letterDigitEntities {

    // The six entities under test: superscript digits ¹²³ and vulgar fractions ¼½¾.
    // Their names all mix letters and digits, which is the edge-case this test exercises.
    private static final String LETTER_DIGIT_ENTITY_HTML =
        "<p>&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;</p>";

    // The plain Unicode text that the six entities represent.
    private static final String EXPECTED_TEXT = "¹²³¼½¾";

    @Test
    public void letterDigitEntities() {
        Document doc = Jsoup.parse(LETTER_DIGIT_ENTITY_HTML);
        Element p = doc.select("p").first();

        // ── ASCII output ─────────────────────────────────────────────────────────
        // Characters outside ASCII cannot be encoded directly, so jsoup must
        // re-emit them as named entity references in the serialised HTML.
        doc.outputSettings().charset("ascii");

        assertEquals(
            "&sup1;&sup2;&sup3;&frac14;&frac12;&frac34;",
            p.html(),
            "ASCII output: non-ASCII chars should be serialised back as named entities"
        );
        assertEquals(
            EXPECTED_TEXT,
            p.text(),
            "ASCII output: text() should return the decoded Unicode characters"
        );

        // ── UTF-8 output ─────────────────────────────────────────────────────────
        // UTF-8 can represent all these characters directly, so jsoup should
        // emit the literal Unicode string rather than entity references.
        doc.outputSettings().charset("UTF-8");

        assertEquals(
            EXPECTED_TEXT,
            p.html(),
            "UTF-8 output: characters encodable in UTF-8 should appear verbatim in HTML"
        );
    }
}
