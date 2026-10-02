package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testSerialization extends AbstractLangTest {

    /**
     * Serializing a {@link CharRange} and deserializing the result should yield
     * an object that is equal to the original. This is verified for each of the
     * factory methods that produce a range.
     */
    @Test
    void testSerialization() {
        assertRoundTripsToEqual(CharRange.is('a'));
        assertRoundTripsToEqual(CharRange.isIn('a', 'e'));
        assertRoundTripsToEqual(CharRange.isNotIn('a', 'e'));
    }

    /**
     * Asserts that cloning the given range via serialization produces an equal range.
     *
     * @param range the range to serialize and deserialize.
     */
    private static void assertRoundTripsToEqual(final CharRange range) {
        final CharRange clone = SerializationUtils.clone(range);
        assertEquals(range, clone);
    }
}
