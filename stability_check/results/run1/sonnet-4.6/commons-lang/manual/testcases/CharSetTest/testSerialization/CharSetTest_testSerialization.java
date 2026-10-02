package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CharSetTest_testSerialization extends AbstractLangTest {

    @Test
    void testSerialization() {
        // Single character pattern
        assertSerializationRoundTrip("a");
        // Continuous character range pattern
        assertSerializationRoundTrip("a-e");
        // Complex pattern: individual char, range, and negated range combined
        assertSerializationRoundTrip("be-f^a-z");
    }

    /**
     * Verifies that a CharSet built from {@code pattern} is equal to its
     * serialized-then-deserialized clone, confirming that serialization
     * preserves the full set definition.
     */
    private void assertSerializationRoundTrip(final String pattern) {
        final CharSet original = CharSet.getInstance(pattern);
        assertEquals(original, SerializationUtils.clone(original),
                "CharSet for pattern \"" + pattern + "\" must equal its serialized clone");
    }
}
