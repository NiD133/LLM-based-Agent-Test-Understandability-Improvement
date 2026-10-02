package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class NumericEntityUnescaperTest_testOutOfRangeCodePoint extends AbstractLangTest {

    private static final String OUT_OF_RANGE_CODE_POINT_MESSAGE = "Failed to ignore code point above 0x10FFFF";

    @Test
    void testOutOfRangeCodePoint() {
        final NumericEntityUnescaper numericEntityUnescaper = new NumericEntityUnescaper();

        assertOutOfRangeEntityIsIgnored(numericEntityUnescaper, "&#x110000;");
        assertOutOfRangeEntityIsIgnored(numericEntityUnescaper, "&#1114112;");
        assertOutOfRangeEntityIsIgnored(numericEntityUnescaper, "&#x7FFFFFFF;");
    }

    private void assertOutOfRangeEntityIsIgnored(final NumericEntityUnescaper numericEntityUnescaper,
            final String numericEntity) {
        assertEquals(numericEntity, numericEntityUnescaper.translate(numericEntity), OUT_OF_RANGE_CODE_POINT_MESSAGE);
    }
}
