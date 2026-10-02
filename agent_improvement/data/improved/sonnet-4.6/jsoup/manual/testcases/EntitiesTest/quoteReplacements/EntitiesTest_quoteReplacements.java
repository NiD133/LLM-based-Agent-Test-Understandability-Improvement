package org.jsoup.nodes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_quoteReplacements {

    /**
     * Verifies that Entities.unescape() correctly converts HTML numeric character references
     * to their literal character equivalents.
     *
     * &#92; is the decimal numeric reference for backslash (\, U+005C)
     * &#36; is the decimal numeric reference for dollar sign ($, U+0024)
     */
    @Test
    public void quoteReplacements() {
        String htmlWithNumericRefs = "&#92; &#36;";  // backslash and dollar sign as decimal HTML entities
        String expectedLiteralChars = "\\ $";        // the corresponding literal characters

        assertEquals(expectedLiteralChars, Entities.unescape(htmlWithNumericRefs));
    }
}
