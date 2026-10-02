package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link StrSubstitutor#replace(Object, Map, String, String)} when the
 * variable prefix and suffix are the same delimiter (here {@code "@"}), e.g.
 * {@code @name@}.
 */
public class StrSubstitutorTest_testSamePrefixAndSuffix {

    /** Delimiter used both as the variable prefix and the variable suffix. */
    private static final String DELIMITER = "@";

    @Test
    void testSamePrefixAndSuffix() {
        final Map<String, String> substitutions = new HashMap<>();
        substitutions.put("greeting", "Hello");
        substitutions.put(" there ", "XXX");
        substitutions.put("name", "commons");

        // A single variable (@name@) is substituted; surrounding text is kept verbatim.
        assertEquals("Hi commons!",
                StrSubstitutor.replace("Hi @name@!", substitutions, DELIMITER, DELIMITER));

        // Two variables (@greeting@ and @name@) are substituted in one template.
        // Note: the literal " there " is NOT treated as a variable, so its mapping
        // to "XXX" is never applied.
        assertEquals("Hello there commons!",
                StrSubstitutor.replace("@greeting@ there @name@!", substitutions, DELIMITER, DELIMITER));
    }
}
