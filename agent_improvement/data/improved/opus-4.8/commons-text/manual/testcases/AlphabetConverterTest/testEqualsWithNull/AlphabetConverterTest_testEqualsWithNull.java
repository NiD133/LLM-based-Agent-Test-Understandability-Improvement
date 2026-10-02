package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testEqualsWithNull {

    /**
     * An AlphabetConverter must never report equality with {@code null}.
     */
    @Test
    void testEqualsWithNull() {
        final Character[] emptyAlphabet = ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
        final AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(emptyAlphabet, null, null);

        assertFalse(converter.equals(null));
    }
}
