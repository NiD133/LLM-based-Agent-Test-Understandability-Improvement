package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies that constructing a {@link StringSubstitutor} with a {@code null} value map
 * yields a lookup that resolves every variable to {@code null} (rather than throwing).
 */
public class StringSubstitutorTest_testConstructorNullMap {

    @Test
    void testConstructorNullMap() {
        // A null value map is allowed; the resulting lookup should simply find nothing.
        final Map<String, Object> nullValueMap = null;
        final StringSubstitutor substitutor = new StringSubstitutor(nullValueMap, "prefix", "suffix");

        // Both the Function-style apply(...) and the StringLookup-style lookup(...) return null.
        assertNull(substitutor.getStringLookup().apply("X"));
        assertNull(substitutor.getStringLookup().lookup("X"));
    }
}
