package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testEqualsWithNull {

    @Test
    void testEqualsWithNull() {
        // A converter built from an empty alphabet should not equal null
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY, null, null);
        assertFalse(alphabetConverter.equals(null));
    }
}
