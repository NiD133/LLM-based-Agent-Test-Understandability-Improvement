package org.apache.commons.lang3.text.translate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class NumericEntityUnescaperTest_testOutOfBounds extends AbstractLangTest {

    @Test
    void testOutOfBounds() {
        final NumericEntityUnescaper unescaper = new NumericEntityUnescaper();
        final String trailingAmpersand = "Test &";
        final String decimalEntityPrefix = "Test &#";
        final String lowercaseHexEntityPrefix = "Test &#x";
        final String uppercaseHexEntityPrefix = "Test &#X";

        assertEquals(trailingAmpersand, unescaper.translate(trailingAmpersand), "Failed to ignore trailing ampersand");
        assertEquals(decimalEntityPrefix, unescaper.translate(decimalEntityPrefix), "Failed to ignore trailing decimal entity prefix");
        assertEquals(lowercaseHexEntityPrefix, unescaper.translate(lowercaseHexEntityPrefix),
                "Failed to ignore trailing lowercase hexadecimal entity prefix");
        assertEquals(uppercaseHexEntityPrefix, unescaper.translate(uppercaseHexEntityPrefix),
                "Failed to ignore trailing uppercase hexadecimal entity prefix");
    }
}
