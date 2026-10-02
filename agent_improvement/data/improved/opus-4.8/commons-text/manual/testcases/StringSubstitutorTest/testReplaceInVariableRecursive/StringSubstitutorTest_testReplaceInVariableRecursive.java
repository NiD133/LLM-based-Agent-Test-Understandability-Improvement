package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor} resolving a variable whose name is itself built by
 * (recursively) substituting nested variables, e.g. {@code ${animal.${species.${color}}}}.
 *
 * <p>Recursive substitution inside variable names is opt-in and enabled via
 * {@link StringSubstitutor#setEnableSubstitutionInVariables(boolean)}.</p>
 */
public class StringSubstitutorTest_testReplaceInVariableRecursive {

    /**
     * Builds the lookup map used by the test. The names exercise the recursive resolution:
     * {@code color} -> {@code species.<color>} -> {@code animal.<index>} -> the animal text.
     */
    private static Map<String, String> newRecursiveValues() {
        final Map<String, String> values = new HashMap<>();
        values.put("target", "lazy dog");
        // ${color} resolves to "white".
        values.put("color", "white");
        // ${species.white} resolves to "1", ${species.brown} resolves to "2".
        values.put("species.white", "1");
        values.put("species.brown", "2");
        // ${animal.1} resolves to "white mouse", ${animal.2} resolves to "brown fox".
        values.put("animal.1", "white mouse");
        values.put("animal.2", "brown fox");
        return values;
    }

    /**
     * Tests complex and recursive substitution in variable names.
     */
    @Test
    void testReplaceInVariableRecursive() throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(newRecursiveValues());
        sub.setEnableSubstitutionInVariables(true);

        // ${color} -> white, ${species.white} -> 1, ${animal.1} -> white mouse.
        assertEquals("white mouse",
                sub.replace("${animal.${species.${color}}}"));

        // The same recursive name embedded in a larger template.
        assertEquals("The white mouse jumps over the lazy dog.",
                sub.replace("The ${animal.${species.${color}}} jumps over the ${target}."));

        // The unknown inner variable falls back to its default "brown":
        // ${species.brown} -> 2, ${animal.2} -> brown fox.
        assertEquals("The brown fox jumps over the lazy dog.",
                sub.replace("The ${animal.${species.${unknownColor:-brown}}} jumps over the ${target}."));
    }
}
