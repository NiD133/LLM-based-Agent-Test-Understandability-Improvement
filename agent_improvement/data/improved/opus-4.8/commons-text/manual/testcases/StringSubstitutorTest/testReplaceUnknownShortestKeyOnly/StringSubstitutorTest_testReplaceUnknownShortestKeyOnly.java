package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringSubstitutor} leaves a placeholder untouched when its
 * key is unknown.
 *
 * <p>The fixture deliberately contains only short keys ("a", "b", ...). The key
 * "U" referenced by the template is absent, so the substitutor must return the
 * template unchanged rather than resolving or dropping the placeholder.</p>
 */
public class StringSubstitutorTest_testReplaceUnknownShortestKeyOnly {

    /** Known substitution variables; intentionally excludes the key "U". */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys and values.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys and values.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @Test
    void testReplaceUnknownShortestKeyOnly() throws IOException {
        final String templateWithUnknownKey = "${U}";

        final String result = new StringSubstitutor(values).replace(templateWithUnknownKey);

        // The unknown placeholder is preserved verbatim.
        assertEquals(templateWithUnknownKey, result);
    }
}
