package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInTakingStringBuilderWithNull {

    /**
     * {@link StrSubstitutor#replaceIn(StringBuilder)} should report that nothing was
     * replaced (return {@code false}) when given a {@code null} target, regardless of
     * how the substitutor was configured.
     */
    @Test
    void testReplaceInTakingStringBuilderWithNull() {
        final Map<String, Object> emptyValues = new HashMap<>();
        final StrSubstitutor substitutor =
                new StrSubstitutor(emptyValues, "", "", 'T', "K+<'f");

        final boolean replaced = substitutor.replaceIn((StringBuilder) null);

        assertFalse(replaced);
    }
}
