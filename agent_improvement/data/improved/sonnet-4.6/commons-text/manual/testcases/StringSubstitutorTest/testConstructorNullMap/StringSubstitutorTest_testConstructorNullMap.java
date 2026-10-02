package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testConstructorNullMap {

    /**
     * Verifies that constructing a StringSubstitutor with a null variable map causes all
     * lookups to return null, rather than throwing a NullPointerException or returning
     * a non-null default value.
     *
     * <p>The three-argument constructor accepting (Map, prefix, suffix) must tolerate a
     * null map and expose a StringLookup whose both {@code apply} and {@code lookup}
     * methods return null for any key.</p>
     */
    @Test
    void testConstructorNullMap() {
        final Map<String, Object> nullMap = null;
        final StringSubstitutor substitutor = new StringSubstitutor(nullMap, "prefix", "suffix");

        assertNull(substitutor.getStringLookup().apply("X"),
                "apply() on a substitutor built with a null map should return null");
        assertNull(substitutor.getStringLookup().lookup("X"),
                "lookup() on a substitutor built with a null map should return null");
    }
}
