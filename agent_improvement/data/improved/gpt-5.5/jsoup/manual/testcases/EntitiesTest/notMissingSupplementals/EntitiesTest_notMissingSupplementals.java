package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_notMissingSupplementals {

    @Test
    public void notMissingSupplementals() {
        String namedEntities = "&npolint; &qfr;";
        String expectedCharacters = "⨔ \uD835\uDD2E";

        assertEquals(expectedCharacters, Entities.unescape(namedEntities));
    }
}
