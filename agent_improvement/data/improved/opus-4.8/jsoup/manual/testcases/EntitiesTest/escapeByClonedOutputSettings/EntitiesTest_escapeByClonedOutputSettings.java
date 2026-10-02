package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escapeByClonedOutputSettings {

    /**
     * Two independent clones of the same OutputSettings should escape the same text
     * identically, confirming that cloning preserves the escape configuration.
     */
    @Test
    public void escapeByClonedOutputSettings() {
        OutputSettings baseSettings = new OutputSettings();
        String textWithSpecialChars = "Hello &<> Å å π 新 there ¾ © »";

        OutputSettings firstClone = baseSettings.clone();
        OutputSettings secondClone = baseSettings.clone();

        String escapedWithFirstClone =
            assertDoesNotThrow(() -> Entities.escape(textWithSpecialChars, firstClone));
        String escapedWithSecondClone =
            assertDoesNotThrow(() -> Entities.escape(textWithSpecialChars, secondClone));

        assertEquals(escapedWithFirstClone, escapedWithSecondClone);
    }
}
