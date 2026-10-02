package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies {@link Entities#escape(String)}, the convenience overload that escapes using jsoup's
 * default output settings (UTF-8 charset, base HTML entity set, maximal escaping).
 */
public class EntitiesTest_escapeDefaults {

    @Test
    public void escapesReservedCharactersWhilePassingThroughUnicode() {
        // Input mixes:
        //  - HTML-reserved characters that must always be escaped: & < >
        //  - characters that are escaped only under maximal (text + attribute) escaping: ' "
        //  - Unicode characters that UTF-8 can represent natively and so should pass through unchanged
        String input = "Hello &<> Å å π 新 there ¾ © » ' \"";

        String escaped = Entities.escape(input);

        String expected = "Hello &amp;&lt;&gt; Å å π 新 there ¾ © » &apos; &quot;";
        assertEquals(expected, escaped);
    }
}
