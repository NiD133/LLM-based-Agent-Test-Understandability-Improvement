package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceWeirdPattens {

    private Map<String, String> values;

    /**
     * Asserts that the given template is returned unchanged (no substitution occurs).
     * When {@code replaceTemplate} is null, verifies that all replace/replaceIn overloads
     * return null / false rather than throwing.
     */
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
     * Tests that templates containing malformed or unresolvable variable syntax are
     * returned unchanged.  The cases fall into four categories:
     *
     * <ul>
     *   <li>Empty / blank variable names — the substitutor skips "${}", "${ }", etc.
     *       because an empty key can never match a map entry.</li>
     *   <li>Incomplete delimiters — a lone "${" or lone "}" is not a complete
     *       variable reference, so nothing is substituted.</li>
     *   <li>Escape-only constructs — patterns such as "${${}}" consist entirely of the
     *       escape character or unresolvable nested prefixes and produce no output
     *       change.</li>
     *   <li>Nested-prefix constructs — patterns like "${${" or "${${ }}" open a
     *       second prefix before finding a suffix, leaving the whole expression
     *       unresolved.</li>
     * </ul>
     */
    @Test
    void testReplaceWeirdPattens() {
        // Empty string — nothing to substitute
        doTestNoReplace("");

        // Empty or whitespace-only variable names are not resolved
        doTestNoReplace("${}");
        doTestNoReplace("${ }");
        doTestNoReplace("${\t}");
        doTestNoReplace("${\n}");
        doTestNoReplace("${\b}");

        // Incomplete delimiter sequences — no closing or no opening bracket
        doTestNoReplace("${");
        doTestNoReplace("$}");
        doTestNoReplace("}");

        // Trailing escape character after an empty variable — still unresolvable
        doTestNoReplace("${}$");

        // Nested prefix constructs with no matching variable
        doTestNoReplace("${${");
        doTestNoReplace("${${}}");
        doTestNoReplace("${$${}}");
        doTestNoReplace("${$$${}}");
        doTestNoReplace("${$$${$}}");
        doTestNoReplace("${${}}");
        doTestNoReplace("${${ }}");
    }
}
