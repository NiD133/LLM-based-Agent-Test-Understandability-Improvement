package org.jsoup.nodes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link Entities#getByName(String)}, which resolves a named HTML entity
 * to the character string it represents.
 */
public class EntitiesTest_getByName {

    /**
     * Some named entities map to two codepoints (stored in the multipoints table).
     * These must be returned as a two-character string.
     */
    @Test
    @DisplayName("getByName returns correct string for multi-codepoint entities")
    public void getByName_multiCodepointEntities() {
        // "nGt" → U+226B MUCH GREATER-THAN + U+20D2 COMBINING LONG VERTICAL LINE OVERLAY
        assertEquals("≫⃒", Entities.getByName("nGt"));
        // "fjlig" → 'f' + 'j' (typographic ligature stored as two characters)
        assertEquals("fj", Entities.getByName("fjlig"));
    }

    /**
     * Most named entities map to a single codepoint and should be returned as a
     * one-character string.
     */
    @Test
    @DisplayName("getByName returns correct string for single-codepoint entities")
    public void getByName_singleCodepointEntities() {
        // "gg" → U+226B MUCH GREATER-THAN (≫)
        assertEquals("≫", Entities.getByName("gg"));
        // "copy" → U+00A9 COPYRIGHT SIGN (©)
        assertEquals("©", Entities.getByName("copy"));
    }
}
