package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escapedSupplementary {
    private static final String SUPPLEMENTARY_CHARACTER = "\uD835\uDD59";
    private static final String SUPPLEMENTARY_CHARACTER_NUMERIC_ENTITY = "&#x1d559;";
    private static final String SUPPLEMENTARY_CHARACTER_NAMED_ENTITY = "&hopf;";

    @Test
    public void escapedSupplementary() {
        OutputSettings asciiWithBaseEntities = new OutputSettings().charset("ascii").escapeMode(base);
        String escapedWithBaseEntities = Entities.escape(SUPPLEMENTARY_CHARACTER, asciiWithBaseEntities);
        assertEquals(SUPPLEMENTARY_CHARACTER_NUMERIC_ENTITY, escapedWithBaseEntities);

        OutputSettings asciiWithExtendedEntities = new OutputSettings().charset("ascii").escapeMode(extended);
        String escapedWithExtendedEntities = Entities.escape(SUPPLEMENTARY_CHARACTER, asciiWithExtendedEntities);
        assertEquals(SUPPLEMENTARY_CHARACTER_NAMED_ENTITY, escapedWithExtendedEntities);

        OutputSettings utf8WithExtendedEntities = new OutputSettings().charset("UTF-8").escapeMode(extended);
        String escapedWithUtf8 = Entities.escape(SUPPLEMENTARY_CHARACTER, utf8WithExtendedEntities);
        assertEquals(SUPPLEMENTARY_CHARACTER, escapedWithUtf8);
    }
}
