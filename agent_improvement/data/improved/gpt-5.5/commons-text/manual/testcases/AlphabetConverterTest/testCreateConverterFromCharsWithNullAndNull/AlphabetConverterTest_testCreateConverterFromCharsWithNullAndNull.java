package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testCreateConverterFromCharsWithNullAndNull {

    @Test
    void testCreateConverterFromCharsWithNullAndNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            final Character[] characterArray = new Character[2];
            characterArray[0] = '$';
            characterArray[1] = characterArray[0];

            AlphabetConverter.createConverterFromChars(characterArray, null, null);
        });
    }
}
