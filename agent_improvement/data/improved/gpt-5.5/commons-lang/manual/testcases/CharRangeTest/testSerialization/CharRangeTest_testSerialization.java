package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testSerialization extends AbstractLangTest {

    @Test
    void testSerialization() {
        final CharRange singleCharacterRange = CharRange.is('a');
        assertEquals(singleCharacterRange, SerializationUtils.clone(singleCharacterRange));

        final CharRange inclusiveRange = CharRange.isIn('a', 'e');
        assertEquals(inclusiveRange, SerializationUtils.clone(inclusiveRange));

        final CharRange negatedRange = CharRange.isNotIn('a', 'e');
        assertEquals(negatedRange, SerializationUtils.clone(negatedRange));
    }
}
