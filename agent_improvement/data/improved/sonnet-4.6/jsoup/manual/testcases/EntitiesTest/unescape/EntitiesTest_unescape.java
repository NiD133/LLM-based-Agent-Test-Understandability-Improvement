package org.jsoup.nodes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_unescape {

    /**
     * HTML entities that should be unescaped, covering:
     *  - named entities with and without trailing semicolons (e.g. &AElig; and &LT)
     *  - decimal and hex numeric character references (&#960; &#x65B0;)
     *  - case-insensitive named entities (&COPY; == &copy;)
     *  - invalid entity names that must be left as-is (&angst &!)
     */
    private static final String MIXED_ENTITIES_INPUT =
        "Hello &AElig; &amp;&LT&gt; &reg &angst; &angst &#960; &#960 &#x65B0; there &! &frac34; &copy; &COPY;";

    private static final String MIXED_ENTITIES_EXPECTED =
        "Hello Æ &<> ® Å &angst π π 新 there &! ¾ © ©";

    /**
     * Strings whose entity-like tokens cannot be resolved should be returned unchanged.
     * A leading digit (&0987654321;) and an unrecognised name (&unknown) are both left intact.
     */
    private static final String INVALID_ENTITIES_INPUT  = "&0987654321; &unknown";
    private static final String INVALID_ENTITIES_EXPECTED = "&0987654321; &unknown";

    @Test
    @DisplayName("unescape converts valid HTML entities and leaves invalid ones unchanged")
    public void unescape() {
        assertEquals(MIXED_ENTITIES_EXPECTED, Entities.unescape(MIXED_ENTITIES_INPUT));
        assertEquals(INVALID_ENTITIES_EXPECTED, Entities.unescape(INVALID_ENTITIES_INPUT));
    }
}
