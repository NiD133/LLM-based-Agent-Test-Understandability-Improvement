package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceKeyStartChars1Only {

    protected Map<String, String> values;

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

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests that a string containing only the first character of the default variable start marker
     * (e.g. "$" from "${") is returned unchanged, because it cannot form a complete variable reference.
     */
    @Test
    void testReplaceKeyStartChars1Only() {
        // DEFAULT_VAR_START is "${"; taking only "$" should not trigger any substitution
        String incompleteVarStart = StringSubstitutor.DEFAULT_VAR_START.substring(0, 1);
        StringSubstitutor substitutor = new StringSubstitutor(values);

        String result = substitutor.replace(incompleteVarStart);

        assertEquals(incompleteVarStart, result,
            () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(incompleteVarStart), StringUtils.length(result)));
    }
}
