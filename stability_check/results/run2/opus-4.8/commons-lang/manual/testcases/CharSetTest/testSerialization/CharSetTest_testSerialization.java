package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link CharSet} instances survive a serialization round-trip:
 * a clone produced by deep-copying via serialization must be equal to the original.
 */
public class CharSetTest_testSerialization extends AbstractLangTest {

    /**
     * Serializes and deserializes each CharSet (via {@link SerializationUtils#clone})
     * and asserts the restored copy equals the original. Covers a single character,
     * a character range, and a combination of ranges with negation.
     */
    @Test
    void testSerialization() {
        assertSerializationRoundTripsToEqual("a");
        assertSerializationRoundTripsToEqual("a-e");
        assertSerializationRoundTripsToEqual("be-f^a-z");
    }

    /**
     * Builds a CharSet from the given definition, clones it through serialization,
     * and asserts the clone equals the original.
     *
     * @param setDefinition the CharSet syntax string to build the set from
     */
    private void assertSerializationRoundTripsToEqual(final String setDefinition) {
        final CharSet original = CharSet.getInstance(setDefinition);
        final CharSet deserializedCopy = SerializationUtils.clone(original);
        assertEquals(original, deserializedCopy);
    }
}
