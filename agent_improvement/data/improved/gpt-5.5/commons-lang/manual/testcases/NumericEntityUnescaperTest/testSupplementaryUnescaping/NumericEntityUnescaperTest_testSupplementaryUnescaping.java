package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class NumericEntityUnescaperTest_testSupplementaryUnescaping extends AbstractLangTest {

    private static final int SUPPLEMENTARY_CODE_POINT = 68642; // U+10C22
    private static final String DECIMAL_NUMERIC_ENTITY = "&#68642;";
    private static final String SUPPLEMENTARY_CHARACTER = new String(Character.toChars(SUPPLEMENTARY_CODE_POINT));

    @Test
    void testSupplementaryUnescaping() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();

        final String result = unescaper.translate(DECIMAL_NUMERIC_ENTITY);

        assertEquals(SUPPLEMENTARY_CHARACTER, result, "Failed to unescape numeric entities supplementary characters");
    }
}
