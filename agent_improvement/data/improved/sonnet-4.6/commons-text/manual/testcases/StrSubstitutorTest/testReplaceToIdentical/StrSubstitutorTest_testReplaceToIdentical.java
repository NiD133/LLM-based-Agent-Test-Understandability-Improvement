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

public class StrSubstitutorTest_testReplaceToIdentical {

    private Map<String, String> values;

    /**
     * Verifies that replace() returns null for every overload when the source is null,
     * and that replaceIn() returns false without mutating a null buffer.
     */
    private void doTestNoReplace(final String replaceTemplate) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        if (replaceTemplate == null) {
            // All replace() overloads must return null for a null source
            assertNull(sub.replace((String) null));
            assertNull(sub.replace((String) null, 0, 100));
            assertNull(sub.replace((char[]) null));
            assertNull(sub.replace((char[]) null, 0, 100));
            assertNull(sub.replace((StringBuffer) null));
            assertNull(sub.replace((StringBuffer) null, 0, 100));
            assertNull(sub.replace((StrBuilder) null));
            assertNull(sub.replace((StrBuilder) null, 0, 100));
            assertNull(sub.replace((Object) null));
            // All replaceIn() overloads must return false (no replacement done) for null
            assertFalse(sub.replaceIn((StringBuffer) null));
            assertFalse(sub.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(sub.replaceIn((StrBuilder) null));
            assertFalse(sub.replaceIn((StrBuilder) null, 0, 100));
        } else {
            // A template with no resolvable variables must come back unchanged
            assertEquals(replaceTemplate, sub.replace(replaceTemplate));
            final StrBuilder bld = new StrBuilder(replaceTemplate);
            assertFalse(sub.replaceIn(bld));
            assertEquals(replaceTemplate, bld.toString());
        }
    }

    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean substring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, substring);
    }

    /**
     * Exercises every replace() and replaceIn() overload of {@code sub} against
     * {@code replaceTemplate} and asserts that each produces {@code expectedResult}.
     *
     * When {@code substring} is true, the method also verifies that operating on the
     * interior slice [1, length-2] of the template yields the same interior slice of
     * {@code expectedResult}, confirming that the surrounding characters are left intact.
     */
    private void doTestReplace(final StrSubstitutor sub, final String expectedResult, final String replaceTemplate, final boolean substring) {
        // The interior of expectedResult, used when testing substring-range overloads
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);

        // --- replace() on a String source ---
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // --- replace() on a char[] source ---
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // --- replace() on a StringBuffer source ---
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // --- replace() on a StringBuilder source ---
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // --- replace() on a StrBuilder source ---
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // --- replace() on an Object (toString() returns the template string) ---
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn() mutating a StringBuffer in-place ---
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // Characters outside the specified range must remain untouched,
            // so the full buffer still equals expectedResult
            assertEquals(expectedResult, buf.toString());
        }

        // --- replaceIn() mutating a StringBuilder in-place ---
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            // Characters outside the specified range must remain untouched
            assertEquals(expectedResult, builder.toString());
        }

        // --- replaceIn() mutating a StrBuilder in-place ---
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            // Characters outside the specified range must remain untouched
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
     * Tests that substitution produces output identical to the input template when
     * the variable chain resolves back to an escaped variable reference.
     *
     * <p>Substitution chain:
     * <ol>
     *   <li>Template contains {@code ${animal}}.</li>
     *   <li>{@code animal} = {@code $${${thing}}}; the {@code $$} is an escape sequence for a
     *       literal {@code $}.</li>
     *   <li>{@code ${thing}} resolves to {@code "animal"}, so the value becomes
     *       {@code $${animal}} → {@code ${animal}} (after escape processing).</li>
     *   <li>The resolved value {@code ${animal}} is the same token that appeared in the
     *       original template, so the final output equals the original input.</li>
     * </ol>
     */
    @Test
    void testReplaceToIdentical() {
        // Override "animal" so its resolved value is the escaped placeholder "${animal}"
        values.put("animal", "$${${thing}}");
        values.put("thing", "animal");

        // The substitution chain resolves back to the original template string
        doTestReplace("The ${animal} jumps.", "The ${animal} jumps.", true);
    }
}
