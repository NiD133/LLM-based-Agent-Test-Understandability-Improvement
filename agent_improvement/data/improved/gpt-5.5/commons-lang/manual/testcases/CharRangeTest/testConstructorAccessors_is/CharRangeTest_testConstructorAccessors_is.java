package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testConstructorAccessors_is extends AbstractLangTest {

    @Test
    void testConstructorAccessors_is() {
        final char expectedCharacter = 'a';
        final CharRange singleCharacterRange = CharRange.is(expectedCharacter);

        assertEquals(expectedCharacter, singleCharacterRange.getStart());
        assertEquals(expectedCharacter, singleCharacterRange.getEnd());
        assertFalse(singleCharacterRange.isNegated());
        assertEquals(String.valueOf(expectedCharacter), singleCharacterRange.toString());
    }
}
