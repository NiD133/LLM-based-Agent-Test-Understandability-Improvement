package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor#setEnableSubstitutionInVariables(boolean)}, i.e. resolving a
 * variable whose name is itself built from another variable (e.g. {@code ${animal.${species}}}).
 */
public class StringSubstitutorTest_testReplaceInVariable {

    private static final String ANIMAL = "quick brown fox";
    private static final String TARGET = "lazy dog";

    /** Variable name to value mappings shared by the test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values, kept for parity with the original fixture.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys/values referenced by the templates below.
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * Asserts the substitution result, reporting both lengths on failure to ease debugging.
     */
    private void assertSubstitution(final String expected, final String actual) {
        assertEquals(expected, actual,
            () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Tests whether a variable can be replaced inside a variable name.
     */
    @Test
    void testReplaceInVariable() throws IOException {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);

        // species=2 -> ${animal.2} resolves to "mouse".
        assertSubstitution("The mouse jumps over the lazy dog.",
            sub.replace("The ${animal.${species}} jumps over the ${target}."));

        // species=1 -> ${animal.1} resolves to "fox".
        values.put("species", "1");
        assertSubstitution("The fox jumps over the lazy dog.",
            sub.replace("The ${animal.${species}} jumps over the ${target}."));

        // Unknown variables fall back to their default values (after ":-").
        assertSubstitution("The fox jumps over the lazy dog.",
            sub.replace("The ${unknown.animal.${unknown.species:-1}:-fox} jumps over the ${unknow.target:-lazy dog}."));
    }
}
