package org.apache.commons.lang3.text.translate;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class NumericEntityUnescaperTest_testOutOfRangeCodePoint extends AbstractLangTest {

    @Test
    void testOutOfRangeCodePoint() {
        final NumericEntityUnescaper neu = new NumericEntityUnescaper();
        assertEquals("&#x110000;", neu.translate("&#x110000;"), "Failed to ignore code point above 0x10FFFF");
        assertEquals("&#1114112;", neu.translate("&#1114112;"), "Failed to ignore code point above 0x10FFFF");
        assertEquals("&#x7FFFFFFF;", neu.translate("&#x7FFFFFFF;"), "Failed to ignore code point above 0x10FFFF");
    }
}
