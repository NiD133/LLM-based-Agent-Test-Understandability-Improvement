package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testCapitalizeFully_Text88 {

    @Test
    void testCapitalizeFullyWithEmptyDelimiters() {
        assertEquals("I am fine now", WordUtils.capitalizeFully("i am fine now", new char[] {}));
    }
}
