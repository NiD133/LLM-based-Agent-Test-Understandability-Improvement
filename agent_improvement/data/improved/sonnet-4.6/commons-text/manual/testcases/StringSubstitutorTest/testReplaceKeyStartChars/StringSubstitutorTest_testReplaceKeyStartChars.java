package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a string beginning with the variable-start marker but lacking
 * a closing suffix is left untouched by StringSubstitutor.
 */
public class StringSubstitutorTest_testReplaceKeyStartChars {

    /** Lookup map that contains "a" as a resolvable key. */
    protected Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("a", "1");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Delegates to {@link StringSubstitutor#replace(String)} so subclasses can
     * override the replacement strategy without duplicating test logic.
     */
    protected String replace(final StringSubstitutor substitutor, final String template) throws IOException {
        return substitutor.replace(template);
    }

    /**
     * A string that starts with the variable-start marker ("${") followed by a
     * known key character but has no closing "}" should not be substituted —
     * the substitutor must return the input unchanged.
     */
    @Test
    void testReplaceKeyStartChars() throws IOException {
        // "${a" — opens a variable expression but never closes it
        final String incompleteExpression = StringSubstitutor.DEFAULT_VAR_START + "a";

        final String result = replace(new StringSubstitutor(values), incompleteExpression);

        assertEquals(
            incompleteExpression,
            result,
            () -> String.format(
                "Expected no substitution: expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(incompleteExpression),
                StringUtils.length(result)
            )
        );
    }
}
