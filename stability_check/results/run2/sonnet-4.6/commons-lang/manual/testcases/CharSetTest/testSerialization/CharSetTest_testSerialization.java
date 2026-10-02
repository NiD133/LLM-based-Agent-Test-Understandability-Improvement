package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Verifies that {@link CharSet} is serializable and that equality is preserved
 * after a full serialization/deserialization round-trip.
 */
public class CharSetTest_testSerialization extends AbstractLangTest {

    /**
     * A CharSet created from each pattern must be equal to its deserialized clone,
     * covering a single character, a character range, and a mixed/negated expression.
     */
    @ParameterizedTest(name = "pattern \"{0}\" survives serialization round-trip")
    @DisplayName("CharSet equality is preserved after serialization and deserialization")
    @ValueSource(strings = {
        "a",          // single character
        "a-e",        // character range
        "be-f^a-z"    // mixed: literal 'b', range 'e-f', negated range '^a-z'
    })
    void testSerialization(final String pattern) {
        final CharSet original = CharSet.getInstance(pattern);
        assertEquals(original, SerializationUtils.clone(original));
    }
}
