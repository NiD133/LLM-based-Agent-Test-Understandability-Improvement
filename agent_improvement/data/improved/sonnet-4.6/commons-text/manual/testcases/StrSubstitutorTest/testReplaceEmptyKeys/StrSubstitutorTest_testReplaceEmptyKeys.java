package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests StrSubstitutor behaviour when variable keys are empty.
 *
 * <p>An empty key "${}" is left unresolved in the output, while a key with only
 * a default value "${:-animal}" (empty name, non-empty default) is replaced by
 * that default.  Named variables such as "${target}" are always resolved normally.
 */
public class StrSubstitutorTest_testReplaceEmptyKeys {

    /** Lookup map shared by every test in this class. */
    private Map<String, String> values;

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Tests
    // -------------------------------------------------------------------------

    /**
     * Verifies that an empty variable key "${}" is left as-is in the output,
     * and that a default-value-only expression "${:-animal}" is resolved to its
     * default ("animal") when the key is empty.
     */
    @Test
    void testReplaceEmptyKeys() {
        // "${}" has no key and no default → kept verbatim; "${target}" → "lazy dog"
        doTestReplace(
                "The ${} jumps over the lazy dog.",
                "The ${} jumps over the ${target}.",
                /* substring= */ true);

        // "${:-animal}" has an empty key but a default of "animal" → resolved to "animal"
        doTestReplace(
                "The animal jumps over the lazy dog.",
                "The ${:-animal} jumps over the ${target}.",
                /* substring= */ true);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Exercises {@link StrSubstitutor#replace} and {@link StrSubstitutor#replaceIn}
     * across every supported source type (String, char[], StringBuffer, StringBuilder,
     * StrBuilder, Object) and verifies that the result equals {@code expectedResult}.
     *
     * <p>When {@code substring} is {@code true}, also exercises the offset/length
     * overloads on the inner portion of the template (indices 1 to length-2) and
     * confirms that only that slice is substituted while the rest is left untouched.
     *
     * @param expectedResult   the fully-substituted string expected from a full replace
     * @param replaceTemplate  the template string containing variable references
     * @param substring        whether to also test the partial-range overloads
     */
    private void doTestReplace(final String expectedResult,
                                final String replaceTemplate,
                                final boolean substring) {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // Slice used when testing the offset/length overloads (strip first and last char).
        final String expectedShortResult =
                expectedResult.substring(1, expectedResult.length() - 1);

        // --- replace(String) ---
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (substring) {
            assertEquals(expectedShortResult,
                    sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // --- replace(char[]) ---
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // --- replace(StringBuffer) ---
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // --- replace(StringBuilder) ---
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult,
                    sub.replace(builder, 1, builder.length() - 2));
        }

        // --- replace(StrBuilder) ---
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // --- replace(Object) — Object.toString() returns the template ---
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn(StringBuffer) — mutates the buffer in place ---
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            assertEquals(expectedResult, buf.toString()); // remainder is untouched
        }

        // --- replaceIn(StringBuilder) — mutates the builder in place ---
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString()); // remainder is untouched
        }

        // --- replaceIn(StrBuilder) — mutates the StrBuilder in place ---
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString()); // remainder is untouched
        }
    }
}
