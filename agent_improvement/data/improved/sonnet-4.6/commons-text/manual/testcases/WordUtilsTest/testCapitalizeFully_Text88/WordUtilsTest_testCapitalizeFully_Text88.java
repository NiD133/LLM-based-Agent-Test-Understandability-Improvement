package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testCapitalizeFully_Text88 {

    @Test
    @DisplayName("capitalizeFully with empty delimiter array only capitalizes the first character, treating the whole string as one word")
    void testCapitalizeFully_Text88() {
        // An empty char[] means no delimiters are recognized, so only the very first
        // character is title-cased; subsequent words are not capitalized.
        assertEquals("I am fine now", WordUtils.capitalizeFully("i am fine now", new char[] {}));
    }
}
