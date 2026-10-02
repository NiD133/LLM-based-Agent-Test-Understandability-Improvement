package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_notMissingMultis {
    private static final String NPARSL_ENTITY = "&nparsl;";
    private static final String NPARSL_UNESCAPED = "\u2AFD\u20E5";

    @Test
    public void notMissingMultis() {
        assertEquals(NPARSL_UNESCAPED, Entities.unescape(NPARSL_ENTITY));
    }
}
