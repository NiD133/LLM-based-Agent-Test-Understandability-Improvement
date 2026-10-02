package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceWeirdPattens {

    /** Variables made available to the substitutor; none of them are referenced by the weird patterns below. */
    private Map<String, String> values;

    /**
     * Asserts that {@code template} is left completely untouched by interpolation.
     *
     * <p>The weird patterns exercised by this test contain malformed or empty variable
     * references (for example {@code "${}"} or an unterminated {@code "${"}), so the
     * substitutor must return the template verbatim and report that nothing was replaced.</p>
     *
     * @param template a template that should not trigger any substitution
     */
    private void assertTemplateUnchanged(final String template) {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // replace(String) returns a new String equal to the original template.
        assertEquals(template, sub.replace(template));

        // replaceIn(StrBuilder) edits in place; it must report "no change" and leave the text intact.
        final StrBuilder builder = new StrBuilder(template);
        assertFalse(sub.replaceIn(builder), "no variable should have been replaced");
        assertEquals(template, builder.toString());
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
     * Tests interpolation with weird boundary patterns: empty, blank, malformed, unterminated
     * and nested variable markers should all be passed through unchanged.
     */
    @Test
    void testReplaceWeirdPattens() {
        assertTemplateUnchanged("");          // empty template

        // Empty or blank variable names: nothing to look up, so nothing is replaced.
        assertTemplateUnchanged("${}");       // empty name
        assertTemplateUnchanged("${ }");      // space
        assertTemplateUnchanged("${\t}");     // tab
        assertTemplateUnchanged("${\n}");     // newline
        assertTemplateUnchanged("${\b}");     // backspace

        // Unterminated or stray markers: not a complete "${...}" reference.
        assertTemplateUnchanged("${");        // opening marker without a close
        assertTemplateUnchanged("$}");        // stray close, no opening "${"
        assertTemplateUnchanged("}");         // lone closing brace
        assertTemplateUnchanged("${}$");      // empty reference followed by a trailing "$"
        assertTemplateUnchanged("${${");      // two opening markers, neither closed

        // Nested and repeated markers around empty names: still no resolvable variable.
        assertTemplateUnchanged("${${}}");
        assertTemplateUnchanged("${$${}}");
        assertTemplateUnchanged("${$$${}}");
        assertTemplateUnchanged("${$$${$}}");
        assertTemplateUnchanged("${${}}");
        assertTemplateUnchanged("${${ }}");
    }
}
