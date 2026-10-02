package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_strictUnescape {
    private static final String AMBIGUOUS_AMPERSAND = "Hello &amp= &amp;";
    private static final String STRICT_ATTRIBUTE_UNESCAPE = "Hello &amp= &";
    private static final String LENIENT_TEXT_UNESCAPE = "Hello &= &";

    @Test
    public void strictUnescape() {
        assertEquals(STRICT_ATTRIBUTE_UNESCAPE, Entities.unescape(AMBIGUOUS_AMPERSAND, true));
        assertEquals(LENIENT_TEXT_UNESCAPE, Entities.unescape(AMBIGUOUS_AMPERSAND));
        assertEquals(LENIENT_TEXT_UNESCAPE, Entities.unescape(AMBIGUOUS_AMPERSAND, false));
    }
}
