package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests {@link Entities#getByName(String)}, which resolves an HTML named
 * character reference (e.g. "copy") to the character string it represents
 * (e.g. "©").
 */
public class EntitiesTest_getByName {

    @Test
    public void getByName() {
        // "nGt" maps to two code points (U+226B GREATER-THAN + U+20D2 combining
        // vertical line): a multi-character reference resolved via the multipoints table.
        assertEquals("≫⃒", Entities.getByName("nGt"));

        // "fjlig" is the ligature expansion that produces the two letters "fj".
        assertEquals("fj", Entities.getByName("fjlig"));

        // "gg" maps to the single character U+226B (MUCH GREATER-THAN, "≫").
        assertEquals("≫", Entities.getByName("gg"));

        // "copy" is the familiar copyright sign U+00A9 ("©").
        assertEquals("©", Entities.getByName("copy"));
    }
}
