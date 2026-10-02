package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link StrSubstitutor} applies a "default value" to a variable that
 * cannot be resolved from the value map.
 * <p>
 * In each scenario the template references two known variables ({@code animal},
 * {@code target}) plus one unknown variable ({@code undefined.number}). The
 * unknown variable carries an inline default value that is separated from the
 * variable name by the configured <em>value delimiter</em>. When the delimiter
 * is recognized the default value is substituted; when it is not, the whole
 * variable reference is left untouched.
 */
public class StrSubstitutorTest_testDefaultValueDelimiters {

    /** Variable prefix used by every scenario, e.g. the {@code ${} in ${animal}. */
    private static final String PREFIX = "${";

    /** Variable suffix used by every scenario, e.g. the {@code } in ${animal}. */
    private static final String SUFFIX = "}";

    /** Escape character used by every scenario. */
    private static final char ESCAPE = '$';

    /** Result expected when the unknown variable's default value IS applied. */
    private static final String RESOLVED_WITH_DEFAULT =
            "The fox jumps over the lazy dog. 1234567890.";

    /** Builds the value map that resolves {@code animal} and {@code target}. */
    private static Map<String, String> newValues() {
        final Map<String, String> values = new HashMap<>();
        values.put("animal", "fox");
        values.put("target", "dog");
        return values;
    }

    /**
     * Builds a template whose unknown variable separates name and default value
     * with the given delimiter, e.g. {@code ${undefined.number:-1234567890}}.
     */
    private static String templateWithDelimiter(final String valueDelimiter) {
        return "The ${animal} jumps over the lazy ${target}. "
                + "${undefined.number" + valueDelimiter + "1234567890}.";
    }

    @Test
    void testDefaultValueDelimiters() {
        final Map<String, String> map = newValues();

        // The default ":-" delimiter (bash style) is recognized, so the default
        // value "1234567890" replaces the unresolved variable.
        StrSubstitutor sub = new StrSubstitutor(map, PREFIX, SUFFIX, ESCAPE);
        assertEquals(RESOLVED_WITH_DEFAULT, sub.replace(templateWithDelimiter(":-")));

        // A custom "?:" delimiter is recognized just like the default.
        sub = new StrSubstitutor(map, PREFIX, SUFFIX, ESCAPE, "?:");
        assertEquals(RESOLVED_WITH_DEFAULT, sub.replace(templateWithDelimiter("?:")));

        // A custom "||" delimiter is recognized.
        sub = new StrSubstitutor(map, PREFIX, SUFFIX, ESCAPE, "||");
        assertEquals(RESOLVED_WITH_DEFAULT, sub.replace(templateWithDelimiter("||")));

        // A single-character "!" delimiter is recognized.
        sub = new StrSubstitutor(map, PREFIX, SUFFIX, ESCAPE, "!");
        assertEquals(RESOLVED_WITH_DEFAULT, sub.replace(templateWithDelimiter("!")));

        // With no value-delimiter matcher, the "!" is treated as part of the
        // variable name, the variable stays unresolved, and the reference is
        // left verbatim in the output.
        final String unresolvedTemplate = templateWithDelimiter("!");
        final String unresolvedExpected =
                "The fox jumps over the lazy dog. ${undefined.number!1234567890}.";

        // Constructing with an empty delimiter, then clearing the matcher.
        sub = new StrSubstitutor(map, PREFIX, SUFFIX, ESCAPE, "");
        sub.setValueDelimiterMatcher(null);
        assertEquals(unresolvedExpected, sub.replace(unresolvedTemplate));

        // Constructing with the default delimiter, then clearing the matcher.
        sub = new StrSubstitutor(map, PREFIX, SUFFIX, ESCAPE);
        sub.setValueDelimiterMatcher(null);
        assertEquals(unresolvedExpected, sub.replace(unresolvedTemplate));
    }
}
