package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInVariable {

    private Map<String, String> values;

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

    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean substring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, substring);
    }

    private void doTestReplace(final StrSubstitutor sub, final String expectedResult, final String replaceTemplate, final boolean substring) {
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);
        // replace using String
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }
        // replace using char[]
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }
        // replace using StringBuffer
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }
        // replace using StringBuilder
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }
        // replace using StrBuilder
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }
        // replace using object
        // toString returns template
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));
        // replace in StringBuffer
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // expect full result as remainder is untouched
            assertEquals(expectedResult, buf.toString());
        }
        // replace in StringBuilder
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            // expect full result as remainder is untouched
            assertEquals(expectedResult, builder.toString());
        }
        // replace in StrBuilder
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            // expect full result as remainder is untouched
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
     * Tests whether a variable can be replaced in a variable name.
     *
     * <p>When {@code enableSubstitutionInVariables} is set to {@code true}, the substitutor
     * resolves inner variables before looking up the outer variable name. For example,
     * {@code ${animal.${species}}} first resolves {@code ${species}} to get a suffix
     * (e.g. "2"), then looks up {@code animal.2} in the value map.
     *
     * <p>Also verifies that nested default values ({@code :-}) work correctly when the
     * outer variable is unknown: the default is used as the final result.
     */
    @Test
    void testReplaceInVariable() {
        // Set up species-specific animal entries so that ${animal.${species}}
        // resolves to a different animal depending on the value of "species".
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);

        // species=2  →  ${animal.2}  →  "mouse"
        final String templateWithNestedVar = "The ${animal.${species}} jumps over the ${target}.";
        assertEquals("The mouse jumps over the lazy dog.", sub.replace(templateWithNestedVar));

        // Change species to 1 so the same template now resolves to "fox"
        values.put("species", "1");
        assertEquals("The fox jumps over the lazy dog.", sub.replace(templateWithNestedVar));

        // Verify nested substitution combined with default values (:-):
        // unknown.animal.${unknown.species:-1} → unknown.animal.1 (unknown → use default "fox")
        // unknow.target → unknown → use default "lazy dog"
        final String templateWithDefaults =
                "The ${unknown.animal.${unknown.species:-1}:-fox} jumps over the ${unknow.target:-lazy dog}.";
        assertEquals("The fox jumps over the lazy dog.", sub.replace(templateWithDefaults));
    }
}
