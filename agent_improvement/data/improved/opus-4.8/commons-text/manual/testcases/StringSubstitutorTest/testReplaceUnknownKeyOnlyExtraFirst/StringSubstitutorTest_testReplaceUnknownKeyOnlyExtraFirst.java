package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor#replace(String)} leaves a variable
 * expression untouched when its key is not present in the lookup values.
 */
public class StringSubstitutorTest_testReplaceUnknownKeyOnlyExtraFirst {

    /** Lookup values available to the substitutor; intentionally excludes "person". */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * When the only variable in the template ("person") is unknown, the template
     * should be returned unchanged.
     */
    @Test
    void testReplaceUnknownKeyOnlyExtraFirst() throws IOException {
        final String templateWithUnknownKey = ".${person}";

        final String actual = new StringSubstitutor(values).replace(templateWithUnknownKey);

        assertEquals(templateWithUnknownKey, actual);
    }
}
