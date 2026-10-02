package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link StringSubstitutor} reflects changes made to its backing map
 * <em>after</em> the substitutor was constructed (a usage pattern that works but is
 * not recommended).
 */
public class StringSubstitutorTest_testReplaceChangedMap {

    private static final String TEMPLATE = "The ${animal} jumps over the ${target}.";

    /**
     * Builds the variable map shared by the substitutor under test. The "animal" and
     * "target" entries drive this test; the remaining short keys mirror the original
     * fixture and act as harmless noise.
     */
    private Map<String, String> newValues() {
        final Map<String, String> values = new HashMap<>();
        // Short keys and values.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Keys referenced by the template.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
        return values;
    }

    @Test
    void testReplaceChangedMap() throws IOException {
        final Map<String, String> values = newValues();
        final StringSubstitutor sub = new StringSubstitutor(values);

        // Before any change, the template resolves against the original map values.
        assertEquals("The quick brown fox jumps over the lazy dog.", sub.replace(TEMPLATE));

        // Mutating the map after construction is reflected on the next replace.
        values.put("target", "moon");
        assertEquals("The quick brown fox jumps over the moon.", sub.replace(TEMPLATE));
    }
}
