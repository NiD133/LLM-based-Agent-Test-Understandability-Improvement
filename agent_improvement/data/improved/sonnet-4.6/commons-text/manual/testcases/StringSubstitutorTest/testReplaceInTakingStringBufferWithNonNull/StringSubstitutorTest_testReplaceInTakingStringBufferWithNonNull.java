package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceInTakingStringBufferWithNonNull {

    /**
     * Used as both the variable prefix and suffix so that the substitutor cannot
     * detect any variable expressions in the input — the same delimiter on both
     * sides means no valid open/close pair can be formed.
     */
    private static final String SAME_PREFIX_AND_SUFFIX = "WV@i#y?N*[";

    private static final char ESCAPE_CHAR = '*';

    @Test
    void testReplaceInTakingStringBufferWithNonNull() {
        // Build a substitutor with an empty variable map and identical prefix/suffix,
        // so it has nothing to substitute and cannot match any variable expressions.
        final StringSubstitutor substitutor = new StringSubstitutor(
                new HashMap<>(), SAME_PREFIX_AND_SUFFIX, SAME_PREFIX_AND_SUFFIX, ESCAPE_CHAR);

        assertFalse(substitutor.isPreserveEscapes(),
                "Escape preservation should be disabled by default");

        assertFalse(substitutor.replaceIn(new StringBuffer(SAME_PREFIX_AND_SUFFIX)),
                "replaceIn should return false when no substitutions are made");

        assertEquals(ESCAPE_CHAR, substitutor.getEscapeChar(),
                "Escape character should match the one supplied at construction time");
    }
}
