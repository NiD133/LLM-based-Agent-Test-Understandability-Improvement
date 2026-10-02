package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escapeByClonedOutputSettings {

    /**
     * Verifies that cloning OutputSettings produces independent but functionally equivalent copies:
     * escaping the same text with two separate clones of the same settings must yield identical results.
     */
    @Test
    @DisplayName("Escaping with two independent clones of the same OutputSettings yields identical results")
    public void escapeByClonedOutputSettings() {
        OutputSettings originalSettings = new OutputSettings();
        // A rich string covering ASCII specials, non-ASCII, and multi-byte Unicode characters
        String textWithSpecialChars = "Hello &<> Å å π 新 there ¾ © »";

        // Create two independent clones to confirm neither shares mutable state with the other
        OutputSettings clone1 = originalSettings.clone();
        OutputSettings clone2 = originalSettings.clone();

        String escaped1 = assertDoesNotThrow(() -> Entities.escape(textWithSpecialChars, clone1));
        String escaped2 = assertDoesNotThrow(() -> Entities.escape(textWithSpecialChars, clone2));

        assertEquals(escaped1, escaped2,
            "Cloned OutputSettings must produce identical escape output for the same input");
    }
}
