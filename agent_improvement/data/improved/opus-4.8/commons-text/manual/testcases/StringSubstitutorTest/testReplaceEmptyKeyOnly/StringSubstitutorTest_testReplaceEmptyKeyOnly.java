package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor#replace(String)} for a template that contains
 * only an empty variable expression ({@code ${}}, i.e. a placeholder with no
 * variable name).
 */
public class StringSubstitutorTest_testReplaceEmptyKeyOnly {

    /** A variable placeholder whose key is empty. */
    private static final String EMPTY_KEY_PLACEHOLDER = "${}";

    /**
     * Builds the lookup map used to construct the substitutor. The concrete
     * entries are irrelevant to this test (the template has no resolvable key),
     * but the map mirrors the original fixture so the substitutor is created
     * from the same input.
     */
    private static Map<String, String> newValues() {
        final Map<String, String> values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
        return values;
    }

    /**
     * An empty key has no matching variable, so the placeholder is left
     * unchanged in the output.
     */
    @Test
    void testReplaceEmptyKeyOnly() {
        final StringSubstitutor substitutor = new StringSubstitutor(newValues());

        assertEquals(EMPTY_KEY_PLACEHOLDER, substitutor.replace(EMPTY_KEY_PLACEHOLDER));
    }
}
