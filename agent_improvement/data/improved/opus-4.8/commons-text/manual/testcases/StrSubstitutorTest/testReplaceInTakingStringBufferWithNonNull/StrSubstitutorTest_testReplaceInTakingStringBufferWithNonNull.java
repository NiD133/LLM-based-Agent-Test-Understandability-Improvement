package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInTakingStringBufferWithNonNull {

    /**
     * Verifies {@link StrSubstitutor#replaceIn(StringBuffer)} when the substitutor is
     * configured so that no substitution can occur.
     * <p>
     * The substitutor is built with an empty value map and with the variable prefix and
     * suffix both set to the literal text {@code "WV@i#y?N*["}, and an escape character of
     * {@code '*'}. Because there are no values to resolve, {@code replaceIn} must leave the
     * buffer unchanged and therefore report {@code false} (nothing altered).
     */
    @Test
    void replaceInLeavesBufferUnchangedWhenNoValuesToSubstitute() {
        final String variableMarker = "WV@i#y?N*[";
        final char escapeChar = '*';
        final StrSubstitutor substitutor = new StrSubstitutor(
                new HashMap<String, String>(), variableMarker, variableMarker, escapeChar);

        // The constructor stores the configuration as given.
        assertFalse(substitutor.isPreserveEscapes());
        assertEquals(escapeChar, substitutor.getEscapeChar());

        // With no values to resolve, the buffer is not altered, so replaceIn returns false.
        assertFalse(substitutor.replaceIn(new StringBuffer(variableMarker)));
    }
}
