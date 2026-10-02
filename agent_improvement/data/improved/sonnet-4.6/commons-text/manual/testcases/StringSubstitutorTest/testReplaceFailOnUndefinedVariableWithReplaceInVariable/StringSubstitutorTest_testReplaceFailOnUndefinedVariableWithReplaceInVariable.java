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

public class StringSubstitutorTest_testReplaceFailOnUndefinedVariableWithReplaceInVariable {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";

    protected Map<String, String> values;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

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
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests that nested variable substitution resolves correctly when
     * {@code enableUndefinedVariableException} and {@code enableSubstitutionInVariables} are both enabled.
     * Verifies that undefined variables cause an {@link IllegalArgumentException} with a descriptive message,
     * while fully resolvable nested expressions produce correct substitutions.
     */
    @Test
    void testReplaceFailOnUndefinedVariableWithReplaceInVariable() throws IOException {
        // Set up variables that support nested key construction (e.g. "animal.${species}" -> "animal.2" -> "mouse")
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        values.put("statement.1", "2");
        values.put("recursive", "1");
        values.put("word", "variable");
        values.put("testok.2", "statement");

        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableUndefinedVariableException(true);
        sub.setEnableSubstitutionInVariables(true);

        // species=2 -> resolves "animal.2" -> "mouse"
        assertEqualsCharSeq("The mouse jumps over the lazy dog.",
                replace(sub, "The ${animal.${species}} jumps over the ${target}."));

        // species=1 -> resolves "animal.1" -> "fox"
        values.put("species", "1");
        assertEqualsCharSeq("The fox jumps over the lazy dog.",
                replace(sub, "The ${animal.${species}} jumps over the ${target}."));

        // "statement" exists but "test.${statement}" resolves to "test.2", which is undefined -> exception
        assertEquals(
                "Cannot resolve variable 'statement' (enableSubstitutionInVariables=true).",
                assertThrows(IllegalArgumentException.class,
                        () -> replace(sub, "The ${test.${statement}} is a sample for missing ${word}."))
                        .getMessage());

        // recursive resolution: statement.${recursive}=statement.1=2, so test.${statement.${recursive}}=test.2, which is undefined -> exception
        assertEquals(
                "Cannot resolve variable 'test.2' (enableSubstitutionInVariables=true).",
                assertThrows(IllegalArgumentException.class,
                        () -> replace(sub, "The ${test.${statement.${recursive}}} is a sample for missing ${word}."))
                        .getMessage());

        // testok.${statement.${recursive}} = testok.${statement.1} = testok.2 = "statement" -> resolves successfully
        assertEqualsCharSeq("statement",
                replace(sub, "${testok.${statement.${recursive}}}"));

        // "$${...}" escapes the leading "$", so the outer variable is not substituted but the inner is still resolved
        assertEqualsCharSeq("${testok.2}",
                replace(sub, "$${testok.${statement.${recursive}}}"));

        // full sentence with fully resolvable nested variables
        assertEqualsCharSeq("The statement is a sample for missing variable.",
                replace(sub, "The ${testok.${statement.${recursive}}} is a sample for missing ${word}."));
    }
}
