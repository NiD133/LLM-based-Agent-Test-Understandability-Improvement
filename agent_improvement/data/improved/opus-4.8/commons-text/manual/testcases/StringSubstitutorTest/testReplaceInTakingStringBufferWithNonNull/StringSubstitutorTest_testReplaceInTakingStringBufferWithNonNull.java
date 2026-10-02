package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor#replaceIn(StringBuffer)} when the buffer
 * contains no resolvable variables, so nothing should be replaced.
 */
public class StringSubstitutorTest_testReplaceInTakingStringBufferWithNonNull {

    /**
     * The variable prefix, suffix and escape character are all configured so that
     * the buffer's text "WV@i#y?N*[" does not contain a complete, resolvable
     * variable expression. The substitution should therefore leave the buffer
     * unchanged and report that no replacement occurred.
     */
    @Test
    void testReplaceInTakingStringBufferWithNonNull() {
        final String prefix = "WV@i#y?N*[";
        final String suffix = "WV@i#y?N*[";
        final char escapeChar = '*';

        final StringSubstitutor substitutor =
                new StringSubstitutor(new HashMap<>(), prefix, suffix, escapeChar);

        // Default configuration: escaped sequences are not preserved.
        assertFalse(substitutor.isPreserveEscapes());

        // No resolvable variable in the buffer, so nothing is replaced.
        assertFalse(substitutor.replaceIn(new StringBuffer("WV@i#y?N*[")));

        // The escape character is the one supplied to the constructor.
        assertEquals('*', substitutor.getEscapeChar());
    }
}
