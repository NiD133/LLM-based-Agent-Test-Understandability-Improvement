package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor#replace(String)} leaves a placeholder
 * untouched when its key is not present in the lookup values.
 */
public class StringSubstitutorTest_testReplaceUnknownKeyOnly {

    /** Lookup values supplied to the substitutor; none of them match the unknown key. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * A placeholder whose key ("person") is absent from the values map must be
     * returned verbatim, since there is nothing to substitute.
     */
    @Test
    void testReplaceUnknownKeyOnly() throws IOException {
        final String templateWithUnknownKey = "${person}";

        final String result = new StringSubstitutor(values).replace(templateWithUnknownKey);

        assertEquals(templateWithUnknownKey, result);
    }
}
