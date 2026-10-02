package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testUnCapitalize_Text88 {

    @Test
    void uncapitalizeWithEmptyDelimitersLowercasesTheFirstCharacterOnly() {
        assertEquals("i am fine now", WordUtils.uncapitalize("I am fine now", new char[] {}));
    }
}
