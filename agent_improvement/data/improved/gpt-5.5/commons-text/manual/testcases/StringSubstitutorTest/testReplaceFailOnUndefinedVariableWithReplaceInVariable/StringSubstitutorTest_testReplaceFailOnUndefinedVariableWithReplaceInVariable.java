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

    private static final String ANIMAL_BY_SPECIES_TEMPLATE =
            "The ${animal.${species}} jumps over the ${target}.";
    private static final String MISSING_STATEMENT_TEMPLATE =
            "The ${test.${statement}} is a sample for missing ${word}.";
    private static final String MISSING_RECURSIVE_TEST_TEMPLATE =
            "The ${test.${statement.${recursive}}} is a sample for missing ${word}.";
    private static final String RESOLVED_RECURSIVE_TEST_TEMPLATE =
            "The ${testok.${statement.${recursive}}} is a sample for missing ${word}.";

    protected Map<String, String> values;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    private void assertReplacementFails(final StringSubstitutor substitutor, final String template,
            final String expectedMessage) {
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> replace(substitutor, template));
        assertEquals(expectedMessage, exception.getMessage());
    }

    /**
     * For subclasses to override.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
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
     * Tests whether replace with fail on undefined variable with substitution in variable names enabled.
     */
    @Test
    void testReplaceFailOnUndefinedVariableWithReplaceInVariable() throws IOException {
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

        assertEqualsCharSeq("The mouse jumps over the lazy dog.", replace(sub, ANIMAL_BY_SPECIES_TEMPLATE));

        values.put("species", "1");
        assertEqualsCharSeq("The fox jumps over the lazy dog.", replace(sub, ANIMAL_BY_SPECIES_TEMPLATE));

        assertReplacementFails(sub, MISSING_STATEMENT_TEMPLATE,
                "Cannot resolve variable 'statement' (enableSubstitutionInVariables=true).");
        assertReplacementFails(sub, MISSING_RECURSIVE_TEST_TEMPLATE,
                "Cannot resolve variable 'test.2' (enableSubstitutionInVariables=true).");

        assertEqualsCharSeq("statement", replace(sub, "${testok.${statement.${recursive}}}"));
        assertEqualsCharSeq("${testok.2}", replace(sub, "$${testok.${statement.${recursive}}}"));
        assertEqualsCharSeq("The statement is a sample for missing variable.",
                replace(sub, RESOLVED_RECURSIVE_TEST_TEMPLATE));
    }
}
