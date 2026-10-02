package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharSetTest_testSerialization extends AbstractLangTest {

    @Test
    void testSerialization() {
        final CharSet singleCharacterSet = CharSet.getInstance("a");
        assertEquals(singleCharacterSet, SerializationUtils.clone(singleCharacterSet));

        final CharSet characterRangeSet = CharSet.getInstance("a-e");
        assertEquals(characterRangeSet, SerializationUtils.clone(characterRangeSet));

        final CharSet mixedRangeAndNegatedRangeSet = CharSet.getInstance("be-f^a-z");
        assertEquals(mixedRangeAndNegatedRangeSet, SerializationUtils.clone(mixedRangeAndNegatedRangeSet));
    }
}
