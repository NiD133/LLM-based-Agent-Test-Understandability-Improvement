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
 * Tests {@link StringSubstitutor} escaping behaviour: a run of escape characters ({@code $}) in front of a
 * variable expression is collapsed and the variable itself is left untouched.
 */
public class StringSubstitutorTest_testReplaceVariablesCount1Escaping6To4 {

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values that the (escaped) variable names below could otherwise resolve to.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal keys/values.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Asserts that substituting {@code template} yields {@code expected} for every input type accepted by
     * {@link StringSubstitutor}: {@code String}, {@code char[]}, {@code StringBuffer}, {@code StringBuilder},
     * {@code TextStringBuilder} and arbitrary {@code Object}, plus the in-place {@code replaceIn} variants.
     */
    private void assertSubstitutesForAllInputTypes(final String expected, final String template) {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // Read-only inputs: replace(...) returns the substituted text and leaves the source untouched.
        assertEquals(expected, substitutor.replace(template));
        assertEquals(expected, substitutor.replace(template.toCharArray()));
        assertEquals(expected, substitutor.replace(new StringBuffer(template)));
        assertEquals(expected, substitutor.replace(new StringBuilder(template)));
        assertEquals(expected, substitutor.replace(new TextStringBuilder(template)));
        // An arbitrary Object is substituted via its toString().
        assertEquals(expected, substitutor.replace(new MutableObject<>(template)));

        // In-place inputs: replaceIn(...) reports a change and mutates the buffer to the substituted text.
        final StringBuffer stringBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(stringBuffer), template);
        assertEquals(expected, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(expected, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(expected, textStringBuilder.toString());
    }

    @Test
    void testReplaceVariablesCount1Escaping6To4() throws IOException {
        // Six leading '$' collapse to five; the trailing "${a}" / "${animal}" stays escaped (not resolved).
        assertSubstitutesForAllInputTypes("$$$$${a}", "$$$$$${a}");
        assertSubstitutesForAllInputTypes("$$$$${animal}", "$$$$$${animal}");
    }
}
