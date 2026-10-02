package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that StringSubstitutor leaves an unknown variable expression unchanged
 * when the expression is followed by extra text at the end of the string.
 *
 * Scenario: the lookup map does not contain "person", so "${person}." must be
 * returned as-is — the substitutor must not partially consume or corrupt the
 * trailing period.
 */
public class StringSubstitutorTest_testReplaceUnknownKeyOnlyExtraLast {

    /** Lookup map populated with known keys; "person" is intentionally absent. */
    private Map<String, String> values;

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

    /**
     * Verifies that a template whose only variable references an unknown key —
     * with trailing literal text after the closing brace — is returned unchanged.
     *
     * Input:  "${person}."   ("person" is not in the lookup map)
     * Expected: "${person}." (the entire string, including the period, is preserved)
     */
    @Test
    void testReplaceUnknownKeyOnlyExtraLast() {
        // The variable key "person" has no mapping, so the expression must survive intact.
        final String templateWithUnknownKeyAndTrailingText = "${person}.";

        StringSubstitutor substitutor = new StringSubstitutor(values);
        String result = substitutor.replace(templateWithUnknownKeyAndTrailingText);

        assertEquals(templateWithUnknownKeyAndTrailingText, result,
                "Unknown variable followed by extra text should be left unchanged");
    }
}
