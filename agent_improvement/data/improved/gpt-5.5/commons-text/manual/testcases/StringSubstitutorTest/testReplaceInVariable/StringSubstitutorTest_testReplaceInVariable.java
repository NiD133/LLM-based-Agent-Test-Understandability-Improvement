package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceInVariable {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";
    private static final String VARIABLE_NAME_TEMPLATE = "The ${animal.${species}} jumps over the ${target}.";
    private static final String DEFAULTED_VARIABLE_NAME_TEMPLATE =
            "The ${unknown.animal.${unknown.species:-1}:-fox} jumps over the ${unknow.target:-lazy dog}.";

    protected Map<String, String> values;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d", StringUtils.length(expected),
                        StringUtils.length(actual)));
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
     * Tests whether a variable can be replaced in a variable name.
     */
    @Test
    void testReplaceInVariable() throws IOException {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);

        assertEqualsCharSeq("The mouse jumps over the lazy dog.", replace(sub, VARIABLE_NAME_TEMPLATE));

        values.put("species", "1");

        assertEqualsCharSeq("The fox jumps over the lazy dog.", replace(sub, VARIABLE_NAME_TEMPLATE));
        assertEqualsCharSeq("The fox jumps over the lazy dog.", replace(sub, DEFAULTED_VARIABLE_NAME_TEMPLATE));
    }
}
