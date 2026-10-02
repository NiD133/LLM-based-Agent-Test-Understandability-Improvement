package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escapeSupplementaryCharacter {
    private static final int SUPPLEMENTARY_CODE_POINT = 135361;
    private static final String NUMERIC_ENTITY_FOR_ASCII = "&#x210c1;";

    @Test
    public void escapeSupplementaryCharacter() {
        String supplementaryCharacter = new String(Character.toChars(SUPPLEMENTARY_CODE_POINT));

        OutputSettings asciiSettings = new OutputSettings().charset("ascii").escapeMode(base);
        String escapedAscii = Entities.escape(supplementaryCharacter, asciiSettings);
        assertEquals(NUMERIC_ENTITY_FOR_ASCII, escapedAscii);

        OutputSettings utf8Settings = new OutputSettings().charset("UTF-8").escapeMode(base);
        String escapedUtf8 = Entities.escape(supplementaryCharacter, utf8Settings);
        assertEquals(supplementaryCharacter, escapedUtf8);
    }
}
