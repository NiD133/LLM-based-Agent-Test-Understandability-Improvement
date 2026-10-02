package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.apache.commons.text.lookup.StringLookup;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testConstructorNullMap {

    @Test
    void testConstructorNullMap() {
        final Map<String, Object> parameters = null;
        final StringSubstitutor substitutor = new StringSubstitutor(parameters, "prefix", "suffix");

        final StringLookup lookup = substitutor.getStringLookup();
        assertNull(lookup.apply("X"));
        assertNull(lookup.lookup("X"));
    }
}
