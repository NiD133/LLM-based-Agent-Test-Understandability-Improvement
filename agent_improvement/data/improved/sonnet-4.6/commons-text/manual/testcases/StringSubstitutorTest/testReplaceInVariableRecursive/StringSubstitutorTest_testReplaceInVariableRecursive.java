package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests recursive variable substitution within variable names.
 *
 * When {@code setEnableSubstitutionInVariables(true)} is set, the substitutor
 * resolves variable names themselves before looking up their values — enabling
 * expressions like {@code ${animal.${species.${color}}}}, where the innermost
 * variable is resolved first and the result feeds into the outer lookup.
 */
public class StringSubstitutorTest_testReplaceInVariableRecursive {

    protected Map<String, String> values;

    /**
     * Asserts two CharSequences are equal, printing lengths on mismatch to
     * make large-string failures easier to diagnose.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Delegates to {@link StringSubstitutor#replace(String)} so subclasses can
     * override the replacement mechanism (e.g. stream-based variants).
     */
    protected String replace(final StringSubstitutor substitutor, final String template) throws IOException {
        return substitutor.replace(template);
    }

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
     * Tests three-level recursive substitution inside variable names.
     *
     * <p>Variable lookup chain:
     * <pre>
     *   color          → "white"
     *   species.white  → "1"
     *   species.brown  → "2"
     *   animal.1       → "white mouse"
     *   animal.2       → "brown fox"
     * </pre>
     *
     * <p>The expression {@code ${animal.${species.${color}}}} resolves as:
     * <ol>
     *   <li>{@code ${color}}         → "white"</li>
     *   <li>{@code ${species.white}} → "1"</li>
     *   <li>{@code ${animal.1}}      → "white mouse"</li>
     * </ol>
     *
     * <p>When an unknown variable is used with a default ({@code ${unknownColor:-brown}}),
     * the default value "brown" drives the chain to "brown fox" instead.
     */
    @Test
    void testReplaceInVariableRecursive() throws IOException {
        values.put("animal.2", "brown fox");
        values.put("animal.1", "white mouse");
        values.put("color", "white");
        values.put("species.white", "1");
        values.put("species.brown", "2");

        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);

        // Three-level nesting: color→"white" → species.white→"1" → animal.1→"white mouse"
        assertEqualsCharSeq("white mouse",
                replace(sub, "${animal.${species.${color}}}"));

        // Same chain embedded in a sentence
        assertEqualsCharSeq("The white mouse jumps over the lazy dog.",
                replace(sub, "The ${animal.${species.${color}}} jumps over the ${target}."));

        // Unknown variable falls back to default "brown": species.brown→"2" → animal.2→"brown fox"
        assertEqualsCharSeq("The brown fox jumps over the lazy dog.",
                replace(sub, "The ${animal.${species.${unknownColor:-brown}}} jumps over the ${target}."));
    }
}
