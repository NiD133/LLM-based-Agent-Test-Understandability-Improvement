package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_quoteReplacements {
    private static final String NUMERIC_ENTITIES_FOR_BACKSLASH_AND_DOLLAR = "&#92; &#36;";
    private static final String BACKSLASH_AND_DOLLAR = "\\ $";

    @Test
    public void quoteReplacements() {
        String escaped = NUMERIC_ENTITIES_FOR_BACKSLASH_AND_DOLLAR;
        String unescaped = BACKSLASH_AND_DOLLAR;

        assertEquals(unescaped, Entities.unescape(escaped));
    }
}
