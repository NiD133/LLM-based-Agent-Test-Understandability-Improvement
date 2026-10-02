package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor} replacement of a template that contains a
 * single variable.
 */
public class StringSubstitutorTest_testReplaceVariablesCount1 {

    /** Value bound to the {@code animal} variable. */
    private static final String ANIMAL_VALUE = "quick brown fox";

    /** Template holding exactly one variable: {@code ${animal}}. */
    private static final String TEMPLATE_WITH_ONE_VARIABLE = "${animal}";

    /** Variable-name to value bindings used by the substitutor. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", ANIMAL_VALUE);
    }

    /**
     * Tests simple key replace: a template with a single variable is expanded to
     * that variable's value by every {@code replace} / {@code replaceIn} overload
     * of {@link StringSubstitutor}.
     */
    @Test
    void testReplaceVariablesCount1() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) returns the expanded text and leaves the input untouched.
        assertEquals(ANIMAL_VALUE, substitutor.replace(TEMPLATE_WITH_ONE_VARIABLE));
        assertEquals(ANIMAL_VALUE, substitutor.replace(TEMPLATE_WITH_ONE_VARIABLE.toCharArray()));
        assertEquals(ANIMAL_VALUE, substitutor.replace(new StringBuffer(TEMPLATE_WITH_ONE_VARIABLE)));
        assertEquals(ANIMAL_VALUE, substitutor.replace(new StringBuilder(TEMPLATE_WITH_ONE_VARIABLE)));
        assertEquals(ANIMAL_VALUE, substitutor.replace(new TextStringBuilder(TEMPLATE_WITH_ONE_VARIABLE)));
        // replace(Object) substitutes into the argument's toString() value.
        assertEquals(ANIMAL_VALUE, substitutor.replace(new MutableObject<>(TEMPLATE_WITH_ONE_VARIABLE)));

        // replaceIn(...) edits the builder in place and returns whether anything changed.
        final StringBuffer buffer = new StringBuffer(TEMPLATE_WITH_ONE_VARIABLE);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(ANIMAL_VALUE, buffer.toString());

        final StringBuilder builder = new StringBuilder(TEMPLATE_WITH_ONE_VARIABLE);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(ANIMAL_VALUE, builder.toString());

        final TextStringBuilder textBuilder = new TextStringBuilder(TEMPLATE_WITH_ONE_VARIABLE);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(ANIMAL_VALUE, textBuilder.toString());
    }
}
