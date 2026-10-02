package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInVariable {

    private static final String JUMP_TEMPLATE = "The ${animal.${species}} jumps over the ${target}.";
    private static final String DEFAULTED_TEMPLATE = "The ${unknown.animal.${unknown.species:-1}:-fox} "
            + "jumps over the ${unknow.target:-lazy dog}.";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests whether a variable can be replaced in a variable name.
     */
    @Test
    void testReplaceInVariable() {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StrSubstitutor substitutor = new StrSubstitutor(values);
        substitutor.setEnableSubstitutionInVariables(true);

        assertEquals("The mouse jumps over the lazy dog.", substitutor.replace(JUMP_TEMPLATE));

        values.put("species", "1");

        assertEquals("The fox jumps over the lazy dog.", substitutor.replace(JUMP_TEMPLATE));
        assertEquals("The fox jumps over the lazy dog.", substitutor.replace(DEFAULTED_TEMPLATE));
    }
}
