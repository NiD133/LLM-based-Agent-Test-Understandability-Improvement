package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_quoteReplacements {

    /**
     * Numeric character references should be decoded to their literal characters,
     * including those that look like template/quote placeholders such as
     * {@code &#92;} (backslash) and {@code &#36;} (dollar sign).
     */
    @Test
    public void quoteReplacements() {
        // &#92; is the backslash character, &#36; is the dollar sign.
        String escaped = "&#92; &#36;";
        String expectedUnescaped = "\\ $";

        assertEquals(expectedUnescaped, Entities.unescape(escaped));
    }
}
