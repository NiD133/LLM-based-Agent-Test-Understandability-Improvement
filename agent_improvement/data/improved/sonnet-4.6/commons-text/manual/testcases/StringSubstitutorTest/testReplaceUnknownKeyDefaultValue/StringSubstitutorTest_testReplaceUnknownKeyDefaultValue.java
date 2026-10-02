package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests StringSubstitutor behavior when a template contains:
 * - an unknown key with no default (left as-is)
 * - a known key (substituted normally)
 * - an unknown key with a default value via the ":-" delimiter (substituted with default)
 */
public class StringSubstitutorTest_testReplaceUnknownKeyDefaultValue {

    // Template: ${person} is unknown (no default), ${target} is known, ${undefined.number:-1234567890} uses a default
    private static final String TEMPLATE =
            "The ${person} jumps over the ${target}. ${undefined.number:-1234567890}.";

    // ${person} stays unreplaced; ${target} → "lazy dog"; ${undefined.number:-1234567890} → "1234567890"
    private static final String EXPECTED =
            "The ${person} jumps over the lazy dog. 1234567890.";

    private Map<String, String> values;

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
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that:
     * <ul>
     *   <li>A known variable ({@code ${target}}) is replaced with its mapped value.</li>
     *   <li>An unknown variable with no default ({@code ${person}}) is left unchanged.</li>
     *   <li>An unknown variable with a default value ({@code ${undefined.number:-1234567890}})
     *       is replaced by the default ("1234567890").</li>
     * </ul>
     * Replacement is verified across all supported input types: String, char[],
     * StringBuffer, StringBuilder, TextStringBuilder, Object, and in-place replaceIn variants.
     */
    @Test
    void testReplaceUnknownKeyDefaultValue() throws IOException {
        StringSubstitutor sub = new StringSubstitutor(values);

        // Substring result (strip first and last character of expected for range-based calls)
        String expectedSubstring = EXPECTED.substring(1, EXPECTED.length() - 1);

        // --- replace(String) ---
        assertEquals(EXPECTED, sub.replace(TEMPLATE));
        assertEquals(expectedSubstring, sub.replace(TEMPLATE, 1, TEMPLATE.length() - 2));

        // --- replace(char[]) ---
        char[] templateChars = TEMPLATE.toCharArray();
        assertEquals(EXPECTED, sub.replace(templateChars));
        assertEquals(expectedSubstring, sub.replace(templateChars, 1, templateChars.length - 2));

        // --- replace(StringBuffer) ---
        StringBuffer templateBuffer = new StringBuffer(TEMPLATE);
        assertEquals(EXPECTED, sub.replace(templateBuffer));
        assertEquals(expectedSubstring, sub.replace(templateBuffer, 1, templateBuffer.length() - 2));

        // --- replace(StringBuilder) ---
        StringBuilder templateBuilder = new StringBuilder(TEMPLATE);
        assertEquals(EXPECTED, sub.replace(templateBuilder));
        assertEquals(expectedSubstring, sub.replace(templateBuilder, 1, templateBuilder.length() - 2));

        // --- replace(TextStringBuilder) ---
        TextStringBuilder templateTsb = new TextStringBuilder(TEMPLATE);
        assertEquals(EXPECTED, sub.replace(templateTsb));
        assertEquals(expectedSubstring, sub.replace(templateTsb, 1, templateTsb.length() - 2));

        // --- replace(Object) — delegates to toString() ---
        MutableObject<String> templateObj = new MutableObject<>(TEMPLATE);
        assertEquals(EXPECTED, sub.replace(templateObj));

        // --- replaceIn(StringBuffer) ---
        StringBuffer bufferInPlace = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(bufferInPlace), TEMPLATE);
        assertEquals(EXPECTED, bufferInPlace.toString());

        bufferInPlace = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(bufferInPlace, 1, bufferInPlace.length() - 2));
        assertEquals(EXPECTED, bufferInPlace.toString()); // unmodified region stays, full string matches

        // --- replaceIn(StringBuilder) ---
        StringBuilder builderInPlace = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(builderInPlace));
        assertEquals(EXPECTED, builderInPlace.toString());

        builderInPlace = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(builderInPlace, 1, builderInPlace.length() - 2));
        assertEquals(EXPECTED, builderInPlace.toString());

        // --- replaceIn(TextStringBuilder) ---
        TextStringBuilder tsbInPlace = new TextStringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(tsbInPlace));
        assertEquals(EXPECTED, tsbInPlace.toString());

        tsbInPlace = new TextStringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(tsbInPlace, 1, tsbInPlace.length() - 2));
        assertEquals(EXPECTED, tsbInPlace.toString());

        // --- null handling: replace(null) returns null ---
        assertNull(sub.replace((String) null));
        assertFalse(sub.replaceIn((StringBuffer) null));
        assertFalse(sub.replaceIn((TextStringBuilder) null));
    }
}
