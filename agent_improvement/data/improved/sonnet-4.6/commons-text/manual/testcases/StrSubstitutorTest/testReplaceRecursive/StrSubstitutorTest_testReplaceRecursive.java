package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.SystemProperties;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StrSubstitutor} resolves variables recursively —
 * i.e. when a variable's value itself contains variable references,
 * those nested references are also expanded until the result contains
 * no more substitution markers.
 */
public class StrSubstitutorTest_testReplaceRecursive {

    /**
     * Shared variable map populated before each test and cleared afterwards.
     * Tests may add extra entries without affecting one another.
     */
    private Map<String, String> values;

    // ---------------------------------------------------------------------------
    // Helper: verify that a template is returned unchanged (no substitution occurs)
    // ---------------------------------------------------------------------------

    private void doTestNoReplace(final String replaceTemplate) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        if (replaceTemplate == null) {
            assertNull(sub.replace((String) null));
            assertNull(sub.replace((String) null, 0, 100));
            assertNull(sub.replace((char[]) null));
            assertNull(sub.replace((char[]) null, 0, 100));
            assertNull(sub.replace((StringBuffer) null));
            assertNull(sub.replace((StringBuffer) null, 0, 100));
            assertNull(sub.replace((StrBuilder) null));
            assertNull(sub.replace((StrBuilder) null, 0, 100));
            assertNull(sub.replace((Object) null));
            assertFalse(sub.replaceIn((StringBuffer) null));
            assertFalse(sub.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(sub.replaceIn((StrBuilder) null));
            assertFalse(sub.replaceIn((StrBuilder) null, 0, 100));
        } else {
            assertEquals(replaceTemplate, sub.replace(replaceTemplate));
            final StrBuilder bld = new StrBuilder(replaceTemplate);
            assertFalse(sub.replaceIn(bld));
            assertEquals(replaceTemplate, bld.toString());
        }
    }

    // ---------------------------------------------------------------------------
    // Helper: verify that a template is fully replaced across all supported input
    // types (String, char[], StringBuffer, StringBuilder, StrBuilder, Object) and
    // via both the return-value and the mutating replaceIn() APIs.
    // ---------------------------------------------------------------------------

    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean substring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, substring);
    }

    private void doTestReplace(final StrSubstitutor sub, final String expectedResult,
                                final String replaceTemplate, final boolean substring) {

        // When testing a sub-range the outer characters are kept as-is, so the
        // "short" expected result simply trims the first and last character.
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);

        // --- replace() returning a new String ---

        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // --- replace() on a char[] ---

        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // --- replace() on a StringBuffer ---

        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // --- replace() on a StringBuilder ---

        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // --- replace() on a StrBuilder ---

        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // --- replace() on a generic Object (toString() yields the template) ---

        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn() mutating a StringBuffer in place ---

        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // Characters outside the range are untouched, so the full result still matches.
            assertEquals(expectedResult, buf.toString());
        }

        // --- replaceIn() mutating a StringBuilder in place ---

        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        // --- replaceIn() mutating a StrBuilder in place ---

        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString());
        }
    }

    // ---------------------------------------------------------------------------
    // Lifecycle
    // ---------------------------------------------------------------------------

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

    // ---------------------------------------------------------------------------
    // Test
    // ---------------------------------------------------------------------------

    /**
     * Verifies multi-level recursive substitution: variables whose values contain
     * further variable references are expanded depth-first until the final string
     * contains no remaining markers.
     *
     * <p>The variable graph used in this test:
     * <pre>
     *   animal            → ${critter}
     *   critter           → ${critterSpeed} ${critterColor} ${critterType}
     *   critterSpeed      → "quick"
     *   critterColor      → "brown"
     *   critterType       → "fox"
     *
     *   target            → ${pet}
     *   pet               → ${petCharacteristic} dog
     *   petCharacteristic → "lazy"
     * </pre>
     * Resolving "The ${animal} jumps over the ${target}." should therefore
     * produce "The quick brown fox jumps over the lazy dog."
     *
     * <p>A second pass uses a default-value expression
     * ({@code ${petCharacteristicUnknown:-lazy}}) for an absent variable, which
     * must still produce the same final sentence.
     */
    @Test
    void testReplaceRecursive() {
        // --- Set up a multi-level variable graph ---

        // "animal" resolves through three levels: animal → critter → leaf values
        values.put("animal", "${critter}");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");

        // "target" resolves through two levels: target → pet → petCharacteristic + " dog"
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");

        final String template       = "The ${animal} jumps over the ${target}.";
        final String expectedResult = "The quick brown fox jumps over the lazy dog.";

        // Verify full recursive expansion across all supported input types.
        doTestReplace(expectedResult, template, true);

        // Replace the known variable with a default-value expression for an
        // undefined variable.  The default ("lazy") should kick in, and the
        // final sentence must remain identical.
        values.put("pet", "${petCharacteristicUnknown:-lazy} dog");
        doTestReplace(expectedResult, template, true);
    }
}
