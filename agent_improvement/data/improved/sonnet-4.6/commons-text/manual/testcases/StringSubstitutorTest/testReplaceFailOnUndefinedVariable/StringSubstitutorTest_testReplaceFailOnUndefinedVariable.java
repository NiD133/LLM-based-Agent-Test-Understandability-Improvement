package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceFailOnUndefinedVariable {

    protected Map<String, String> values;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // shortest key and value.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // normal key and value.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests replace with fail on undefined variable.
     */
    @Test
    void testReplaceFailOnUndefinedVariable() throws IOException {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableUndefinedVariableException(true);

        // Nested variable whose inner part is unresolved — the raw unexpanded inner text
        // becomes part of the key name, which cannot be found, so an exception is thrown.
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
                () -> replace(sub, "The ${animal.${species}} jumps over the ${target}."));
        assertEquals("Cannot resolve variable 'animal.${species' (enableSubstitutionInVariables=false).",
                ex1.getMessage());

        // Same scenario but the inner variable also has a default value — the default text
        // is still embedded in the key, making it unresolvable.
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,
                () -> replace(sub, "The ${animal.${species:-1}} jumps over the ${target}."));
        assertEquals("Cannot resolve variable 'animal.${species:-1' (enableSubstitutionInVariables=false).",
                ex2.getMessage());

        // A completely absent variable (no default) triggers the exception even when
        // another variable in the same template does have a default.
        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class,
                () -> replace(sub, "The ${test:-statement} is a sample for missing ${unknown}."));
        assertEquals("Cannot resolve variable 'unknown' (enableSubstitutionInVariables=false).",
                ex3.getMessage());

        // If every unresolved variable has a default value, no exception is thrown.
        assertEqualsCharSeq(
                "The statement is a sample for missing variable.",
                replace(sub, "The ${test:-statement} is a sample for missing ${unknown:-variable}."));

        // A known variable (animal.1 = "fox") resolves normally.
        assertEqualsCharSeq(
                "The fox jumps over the lazy dog.",
                replace(sub, "The ${animal.1} jumps over the ${target}."));
    }
}
