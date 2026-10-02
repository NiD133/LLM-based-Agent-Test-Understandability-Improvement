package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StrSubstitutor} resolves a single variable reference
 * (e.g. {@code ${animal}}) against a value map, exercising every flavour of
 * {@code replace} / {@code replaceIn} input type.
 */
public class StrSubstitutorTest_testReplaceSolo {

    /** The variable placeholder to resolve. */
    private static final String TEMPLATE = "${animal}";

    /** The value expected after {@link #TEMPLATE} is substituted. */
    private static final String EXPECTED = "quick brown fox";

    /** Variable values shared by every substitution below. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests simple key replace.
     *
     * <p>The same {@code ${animal}} template is fed to the substitutor through
     * each supported input type; every call must yield {@link #EXPECTED}.</p>
     */
    @Test
    void testReplaceSolo() {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // replace() returns a new String for each source type.
        assertEquals(EXPECTED, sub.replace(TEMPLATE));
        assertEquals(EXPECTED, sub.replace(TEMPLATE.toCharArray()));
        assertEquals(EXPECTED, sub.replace(new StringBuffer(TEMPLATE)));
        assertEquals(EXPECTED, sub.replace(new StringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, sub.replace(new StrBuilder(TEMPLATE)));
        // An arbitrary Object is resolved via its toString().
        assertEquals(EXPECTED, sub.replace(new MutableObject<>(TEMPLATE)));

        // replaceIn() mutates the given buffer in place and reports that a
        // substitution occurred.
        final StringBuffer stringBuffer = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(stringBuffer));
        assertEquals(EXPECTED, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(EXPECTED, stringBuilder.toString());

        final StrBuilder strBuilder = new StrBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(EXPECTED, strBuilder.toString());
    }
}
