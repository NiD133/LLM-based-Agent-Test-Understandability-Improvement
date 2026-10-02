package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that CharRange instances survive a serialization round-trip
 * (serialize → deserialize) without losing any state.
 */
public class CharRangeTest_testSerialization extends AbstractLangTest {

    @Test
    void testSerialization() {
        // A single-character range: is('a') covers exactly 'a'
        CharRange singleChar = CharRange.is('a');
        assertEquals(singleChar, SerializationUtils.clone(singleChar),
                "Single-character range should be equal after serialization round-trip");

        // A positive inclusive range: isIn('a','e') covers 'a' through 'e'
        CharRange inclusiveRange = CharRange.isIn('a', 'e');
        assertEquals(inclusiveRange, SerializationUtils.clone(inclusiveRange),
                "Inclusive range should be equal after serialization round-trip");

        // A negated inclusive range: isNotIn('a','e') covers everything except 'a' through 'e'
        CharRange negatedRange = CharRange.isNotIn('a', 'e');
        assertEquals(negatedRange, SerializationUtils.clone(negatedRange),
                "Negated range should be equal after serialization round-trip");
    }
}
