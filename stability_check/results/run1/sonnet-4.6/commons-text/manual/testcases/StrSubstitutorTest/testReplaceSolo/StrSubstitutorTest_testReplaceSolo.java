package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that StrSubstitutor correctly replaces a single variable placeholder
 * with its mapped value across all supported input types.
 */
public class StrSubstitutorTest_testReplaceSolo {

    private static final String ANIMAL_KEY = "animal";
    private static final String ANIMAL_VALUE = "quick brown fox";

    /** Template containing a single variable placeholder to be resolved. */
    private static final String TEMPLATE = "${animal}";

    /** The expected output after substitution of ${animal}. */
    private static final String EXPECTED = "quick brown fox";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put(ANIMAL_KEY, ANIMAL_VALUE);
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Verifies that a template containing a single variable placeholder is
     * correctly resolved to its mapped value when replace/replaceIn is called
     * on every supported input type (String, char[], StringBuffer, StringBuilder,
     * StrBuilder, and Object).
     */
    @Test
    void testReplaceSolo() {
        final StrSubstitutor substitutor = new StrSubstitutor(values);

        // Replace on String input
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE));

        // Replace on char[] input
        final char[] templateChars = TEMPLATE.toCharArray();
        assertEquals(EXPECTED, substitutor.replace(templateChars));

        // Replace on StringBuffer input
        final StringBuffer stringBuffer = new StringBuffer(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(stringBuffer));

        // Replace on StringBuilder input
        final StringBuilder stringBuilder = new StringBuilder(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(stringBuilder));

        // Replace on StrBuilder input
        final StrBuilder strBuilder = new StrBuilder(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(strBuilder));

        // Replace on Object input (toString() provides the template)
        final MutableObject<String> objectTemplate = new MutableObject<>(TEMPLATE);
        assertEquals(EXPECTED, substitutor.replace(objectTemplate));

        // In-place replacement in StringBuffer
        final StringBuffer mutableStringBuffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(mutableStringBuffer));
        assertEquals(EXPECTED, mutableStringBuffer.toString());

        // In-place replacement in StringBuilder
        final StringBuilder mutableStringBuilder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(mutableStringBuilder));
        assertEquals(EXPECTED, mutableStringBuilder.toString());

        // In-place replacement in StrBuilder
        final StrBuilder mutableStrBuilder = new StrBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(mutableStrBuilder));
        assertEquals(EXPECTED, mutableStrBuilder.toString());
    }
}
