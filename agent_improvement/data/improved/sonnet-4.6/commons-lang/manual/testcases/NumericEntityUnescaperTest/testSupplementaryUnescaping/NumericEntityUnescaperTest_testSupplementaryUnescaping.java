package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class NumericEntityUnescaperTest_testSupplementaryUnescaping extends AbstractLangTest {

    // U+10C22 (decimal 68642) is a supplementary-plane character (Old Turkic letter).
    // Supplementary code points (> U+FFFF) are stored in Java strings as a UTF-16
    // surrogate pair: a high surrogate followed by a low surrogate.
    private static final String SUPPLEMENTARY_ENTITY = "&#68642;";
    private static final String SUPPLEMENTARY_CHAR   = "𐰢"; // U+10C22

    @Test
    void testSupplementaryUnescaping() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

        final String result = unescaper.translate(SUPPLEMENTARY_ENTITY);

        assertEquals(SUPPLEMENTARY_CHAR, result,
                "Decimal numeric entity for a supplementary code point should unescape to its UTF-16 surrogate pair");
    }
}
