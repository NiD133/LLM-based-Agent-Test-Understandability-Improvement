package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CharSet} instances survive a serialization round-trip.
 *
 * <p>A CharSet must be equal to a clone produced by serializing and then
 * deserializing it, regardless of how the underlying set of characters was
 * defined (single character, character range, or a combination of ranges).</p>
 */
public class CharSetTest_testSerialization extends AbstractLangTest {

    /**
     * Serializing a {@link CharSet} and deserializing it back must yield an
     * object that is equal to the original.
     */
    @Test
    void testSerialization() {
        assertSerializationPreservesEquality("a");            // single character
        assertSerializationPreservesEquality("a-e");          // single range
        assertSerializationPreservesEquality("be-f^a-z");     // combination of ranges
    }

    /**
     * Asserts that the {@link CharSet} built from {@code definition} is equal to
     * a serialized-then-deserialized clone of itself.
     *
     * @param definition the CharSet definition string to test.
     */
    private void assertSerializationPreservesEquality(final String definition) {
        final CharSet original = CharSet.getInstance(definition);
        final CharSet clone = SerializationUtils.clone(original);
        assertEquals(original, clone);
    }
}
