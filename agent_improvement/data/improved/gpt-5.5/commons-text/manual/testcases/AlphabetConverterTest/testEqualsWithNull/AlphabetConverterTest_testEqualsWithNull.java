package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testEqualsWithNull {

    @Test
    void testEqualsWithNull() {
        final Character[] emptyOriginalAlphabet = ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;

        final AlphabetConverter alphabetConverter =
                AlphabetConverter.createConverterFromChars(emptyOriginalAlphabet, null, null);

        assertFalse(alphabetConverter.equals(null));
    }
}
