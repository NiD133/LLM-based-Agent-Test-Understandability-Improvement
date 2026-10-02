package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor#replaceIn(StringBuilder)} handles a
 * {@code null} target gracefully: there is nothing to substitute into, so the
 * method must report that no replacement was made by returning {@code false}.
 */
public class StringSubstitutorTest_testReplaceInTakingStringBuilderWithNull {

    @Test
    void replaceInReturnsFalseWhenStringBuilderIsNull() {
        // The substitutor configuration is irrelevant here; the null target is
        // what drives the behaviour under test.
        final Map<String, Object> emptyValues = new HashMap<>();
        final StringSubstitutor substitutor = new StringSubstitutor(
            emptyValues, StringUtils.EMPTY, StringUtils.EMPTY, 'T', "K+<'f");

        assertFalse(substitutor.replaceIn((StringBuilder) null));
    }
}
