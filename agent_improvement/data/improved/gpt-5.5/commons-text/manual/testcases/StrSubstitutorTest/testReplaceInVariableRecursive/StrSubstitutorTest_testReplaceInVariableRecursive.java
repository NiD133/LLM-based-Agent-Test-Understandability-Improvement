package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInVariableRecursive {

    private static final String TARGET_VALUE = "lazy dog";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", TARGET_VALUE);
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests complex and recursive substitution in variable names.
     */
    @Test
    void testReplaceInVariableRecursive() {
        values.put("animal.2", "brown fox");
        values.put("animal.1", "white mouse");
        values.put("color", "white");
        values.put("species.white", "1");
        values.put("species.brown", "2");

        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);

        final String colorDrivenTemplate = "The ${animal.${species.${color}}} jumps over the ${target}.";
        assertEquals("The white mouse jumps over the lazy dog.", sub.replace(colorDrivenTemplate));

        final String defaultColorTemplate = "The ${animal.${species.${unknownColor:-brown}}} jumps over the ${target}.";
        assertEquals("The brown fox jumps over the lazy dog.", sub.replace(defaultColorTemplate));
    }
}
