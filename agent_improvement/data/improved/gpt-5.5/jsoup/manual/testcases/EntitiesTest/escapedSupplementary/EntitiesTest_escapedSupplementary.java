package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.base;
import static org.jsoup.nodes.Entities.EscapeMode.extended;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_escapedSupplementary {
    private static final String SUPPLEMENTARY_HOPF_CHARACTER = "\uD835\uDD59";

    @Test
    public void escapedSupplementary() {
        OutputSettings asciiWithBaseEntities = new OutputSettings().charset("ascii").escapeMode(base);
        String escapedAscii = Entities.escape(SUPPLEMENTARY_HOPF_CHARACTER, asciiWithBaseEntities);
        assertEquals("&#x1d559;", escapedAscii);

        OutputSettings asciiWithExtendedEntities = new OutputSettings().charset("ascii").escapeMode(extended);
        String escapedAsciiFull = Entities.escape(SUPPLEMENTARY_HOPF_CHARACTER, asciiWithExtendedEntities);
        assertEquals("&hopf;", escapedAsciiFull);

        OutputSettings utf8WithExtendedEntities = new OutputSettings().charset("UTF-8").escapeMode(extended);
        String escapedUtf = Entities.escape(SUPPLEMENTARY_HOPF_CHARACTER, utf8WithExtendedEntities);
        assertEquals(SUPPLEMENTARY_HOPF_CHARACTER, escapedUtf);
    }
}
