package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CharSetTest_testSerialization extends AbstractLangTest {

    /**
     * Verifies that a CharSet survives a serialization round-trip unchanged.
     * SerializationUtils.clone serializes then deserializes the object; the
     * resulting clone must be equal to the original according to CharSet.equals.
     */
    @Test
    @DisplayName("CharSet survives serialization round-trip for single char, range, and complex pattern")
    void testSerialization() {
        // Single character set: "a"
        CharSet singleChar = CharSet.getInstance("a");
        assertEquals(singleChar, SerializationUtils.clone(singleChar),
                "Single-character CharSet should be equal after serialization round-trip");

        // Contiguous range set: "a-e"
        CharSet range = CharSet.getInstance("a-e");
        assertEquals(range, SerializationUtils.clone(range),
                "Range CharSet should be equal after serialization round-trip");

        // Complex pattern combining a single char ("b"), a range ("e-f"), and a negated range ("^a-z")
        CharSet complex = CharSet.getInstance("be-f^a-z");
        assertEquals(complex, SerializationUtils.clone(complex),
                "Complex CharSet with negation should be equal after serialization round-trip");
    }
}
