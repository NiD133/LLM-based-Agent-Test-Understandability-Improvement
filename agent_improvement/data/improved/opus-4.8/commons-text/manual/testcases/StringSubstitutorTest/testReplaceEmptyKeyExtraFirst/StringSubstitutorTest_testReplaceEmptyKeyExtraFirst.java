package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor} behaviour for a template that contains an
 * empty variable expression, i.e. a placeholder with no variable name ("${}").
 */
public class StringSubstitutorTest_testReplaceEmptyKeyExtraFirst {

    /** A placeholder with no variable name between the delimiters. */
    private static final String EMPTY_EXPRESSION = "${}";

    /** Variable-name to value mappings made available to the substitutor. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys/values.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * An empty expression has no variable name, so the substitutor has nothing to
     * look up and leaves it in place. The template is therefore returned unchanged,
     * even when the empty expression is preceded by other characters (here a ".").
     */
    @Test
    void testReplaceEmptyKeyExtraFirst() {
        final String template = "." + EMPTY_EXPRESSION;

        final String result = new StringSubstitutor(values).replace(template);

        assertEquals(template, result);
    }
}
