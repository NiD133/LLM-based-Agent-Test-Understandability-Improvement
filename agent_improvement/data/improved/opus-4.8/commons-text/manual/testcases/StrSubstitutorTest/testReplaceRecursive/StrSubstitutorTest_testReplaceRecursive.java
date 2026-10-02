package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceRecursive {

    /** Variable name -> value map shared by the substitutor under test. */
    private Map<String, String> values;

    /**
     * Runs {@code expectedResult == substitutor.replace(template)} against every input flavour the
     * {@link StrSubstitutor} API offers: the in-place {@code replaceIn} variants and the
     * {@code replace} variants that take a {@code String}, {@code char[]}, {@code StringBuffer},
     * {@code StringBuilder}, {@code StrBuilder} or arbitrary {@code Object}. This guarantees every
     * overload performs the same recursive substitution.
     *
     * @param expectedResult  the fully-resolved text expected after substitution
     * @param replaceTemplate the template containing {@code ${...}} variables
     * @param checkSubstring  when {@code true}, also verifies the offset/length overloads by
     *                        replacing only the template without its first and last character
     */
    private void assertReplacedEverywhere(final String expectedResult, final String replaceTemplate,
            final boolean checkSubstring) {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // The expected result of replacing the template minus its first and last character.
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);

        // --- replace(...) overloads: read template, return resolved copy ---

        // String
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (checkSubstring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // char[]
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (checkSubstring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // StringBuffer
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (checkSubstring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // StringBuilder
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (checkSubstring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // StrBuilder
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (checkSubstring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // Object whose toString() yields the template
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn(...) overloads: mutate the buffer in place, return true if it changed ---

        // StringBuffer
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (checkSubstring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // The untouched first/last characters mean the buffer ends up fully resolved.
            assertEquals(expectedResult, buf.toString());
        }

        // StringBuilder
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (checkSubstring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        // StrBuilder
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (checkSubstring) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString());
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests that substitution recurses through chains of variables that themselves reference other
     * variables, e.g. {@code ${animal}} -> {@code ${critter}} -> {@code ${critterSpeed} ...}.
     */
    @Test
    void testReplaceRecursive() {
        // Build a multi-level variable chain so that resolving "animal" and "target" requires
        // following several layers of indirection:
        //   animal -> critter -> "${critterSpeed} ${critterColor} ${critterType}" -> "quick brown fox"
        //   target -> pet     -> "${petCharacteristic} dog"                        -> "lazy dog"
        values.put("animal", "${critter}");
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");

        final String template = "The ${animal} jumps over the ${target}.";
        final String expected = "The quick brown fox jumps over the lazy dog.";
        assertReplacedEverywhere(expected, template, true);

        // Same expectation, but now "pet" resolves "lazy" through a default value (${name:-default})
        // because "petCharacteristicUnknown" is not defined.
        values.put("pet", "${petCharacteristicUnknown:-lazy} dog");
        assertReplacedEverywhere(expected, template, true);
    }
}
