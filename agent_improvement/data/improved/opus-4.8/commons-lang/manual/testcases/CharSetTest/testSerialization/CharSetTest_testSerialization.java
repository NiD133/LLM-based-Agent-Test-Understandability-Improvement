package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link CharSet} survives a serialize/deserialize round-trip
 * unchanged, i.e. a cloned copy is equal to the original.
 */
public class CharSetTest_testSerialization extends AbstractLangTest {

    /**
     * Asserts that serializing the {@link CharSet} built from {@code definition}
     * and deserializing it again yields an object equal to the original.
     */
    private void assertSurvivesSerializationRoundTrip(final String definition) {
        final CharSet original = CharSet.getInstance(definition);
        final CharSet clone = SerializationUtils.clone(original);
        assertEquals(original, clone);
    }

    @Test
    void testSerialization() {
        // Single character set.
        assertSurvivesSerializationRoundTrip("a");
        // Simple character range.
        assertSurvivesSerializationRoundTrip("a-e");
        // Mixed definition: literal, range and negated range.
        assertSurvivesSerializationRoundTrip("be-f^a-z");
    }
}
