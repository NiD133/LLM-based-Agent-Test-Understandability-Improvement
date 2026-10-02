package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escapeByClonedOutputSettings {

    @Test
    public void escapeByClonedOutputSettings() {
        OutputSettings originalSettings = new OutputSettings();
        String textWithCharactersRequiringEscaping = "Hello &<> Å å π 新 there ¾ © »";

        OutputSettings firstClone = originalSettings.clone();
        OutputSettings secondClone = originalSettings.clone();

        String escapedWithFirstClone = assertDoesNotThrow(
            () -> Entities.escape(textWithCharactersRequiringEscaping, firstClone)
        );
        String escapedWithSecondClone = assertDoesNotThrow(
            () -> Entities.escape(textWithCharactersRequiringEscaping, secondClone)
        );

        assertEquals(escapedWithFirstClone, escapedWithSecondClone);
    }
}
