package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceSolo {

    private static final String VARIABLE_TEMPLATE = "${animal}";
    private static final String RESOLVED_ANIMAL = "quick brown fox";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", RESOLVED_ANIMAL);
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests simple key replace.
     */
    @Test
    void testReplaceSolo() {
        final StrSubstitutor substitutor = new StrSubstitutor(values);

        assertReplaceCreatesResolvedCopies(substitutor);
        assertReplaceInMutatesToResolvedValue(substitutor);
    }

    private void assertReplaceCreatesResolvedCopies(final StrSubstitutor substitutor) {
        assertEquals(RESOLVED_ANIMAL, substitutor.replace(VARIABLE_TEMPLATE));

        final char[] templateChars = VARIABLE_TEMPLATE.toCharArray();
        assertEquals(RESOLVED_ANIMAL, substitutor.replace(templateChars));

        final StringBuffer templateBuffer = new StringBuffer(VARIABLE_TEMPLATE);
        assertEquals(RESOLVED_ANIMAL, substitutor.replace(templateBuffer));

        final StringBuilder templateBuilder = new StringBuilder(VARIABLE_TEMPLATE);
        assertEquals(RESOLVED_ANIMAL, substitutor.replace(templateBuilder));

        final StrBuilder templateStrBuilder = new StrBuilder(VARIABLE_TEMPLATE);
        assertEquals(RESOLVED_ANIMAL, substitutor.replace(templateStrBuilder));

        final MutableObject<String> templateObject = new MutableObject<>(VARIABLE_TEMPLATE);
        assertEquals(RESOLVED_ANIMAL, substitutor.replace(templateObject));
    }

    private void assertReplaceInMutatesToResolvedValue(final StrSubstitutor substitutor) {
        final StringBuffer templateBuffer = new StringBuffer(VARIABLE_TEMPLATE);
        assertTrue(substitutor.replaceIn(templateBuffer));
        assertEquals(RESOLVED_ANIMAL, templateBuffer.toString());

        final StringBuilder templateBuilder = new StringBuilder(VARIABLE_TEMPLATE);
        assertTrue(substitutor.replaceIn(templateBuilder));
        assertEquals(RESOLVED_ANIMAL, templateBuilder.toString());

        final StrBuilder templateStrBuilder = new StrBuilder(VARIABLE_TEMPLATE);
        assertTrue(substitutor.replaceIn(templateStrBuilder));
        assertEquals(RESOLVED_ANIMAL, templateStrBuilder.toString());
    }
}
