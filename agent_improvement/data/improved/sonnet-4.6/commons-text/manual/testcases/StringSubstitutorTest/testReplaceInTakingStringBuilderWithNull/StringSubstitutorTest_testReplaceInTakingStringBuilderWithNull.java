package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor#replaceIn(StringBuilder)} returns {@code false}
 * when given a {@code null} StringBuilder rather than throwing a NullPointerException.
 */
public class StringSubstitutorTest_testReplaceInTakingStringBuilderWithNull {

    @Test
    void testReplaceInTakingStringBuilderWithNull() {
        // Build a substitutor with an empty-string prefix/suffix and non-default escape/delimiter
        // characters. The exact configuration does not matter for this null-safety check.
        final Map<String, Object> variableMap = new HashMap<>();
        final StringSubstitutor substitutor = new StringSubstitutor(
                variableMap,
                StringUtils.EMPTY,   // variable prefix
                StringUtils.EMPTY,   // variable suffix
                'T',                 // escape character
                "K+<'f"              // value delimiter
        );

        // Passing null must return false (no replacement possible), not throw.
        assertFalse(substitutor.replaceIn((StringBuilder) null));
    }
}
