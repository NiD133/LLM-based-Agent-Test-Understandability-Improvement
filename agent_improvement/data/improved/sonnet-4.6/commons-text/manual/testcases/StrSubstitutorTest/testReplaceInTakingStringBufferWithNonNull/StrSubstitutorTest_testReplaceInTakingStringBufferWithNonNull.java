package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInTakingStringBufferWithNonNull {

    /**
     * When the variable prefix and suffix are the same string (here "WV@i#y?N*["),
     * the substitution cannot find a matching pair of delimiters, so replaceIn()
     * returns false (no replacement made) and the buffer content is left unchanged.
     * This also verifies that the default preserveEscapes flag is false and that
     * the custom escape character ('*') is stored correctly.
     */
    @Test
    void testReplaceInTakingStringBufferWithNonNull() {
        final String delimiter = "WV@i#y?N*[";
        final char escapeChar = '*';

        // Both prefix and suffix are the same string, with an empty variable map.
        final StrSubstitutor sub = new StrSubstitutor(new HashMap<>(), delimiter, delimiter, escapeChar);

        // By default, escape sequences are not preserved after substitution.
        assertFalse(sub.isPreserveEscapes());

        // No variable delimiters can be matched (prefix == suffix), so no replacement occurs.
        assertFalse(sub.replaceIn(new StringBuffer(delimiter)));

        // The custom escape character is correctly stored.
        assertEquals(escapeChar, sub.getEscapeChar());
    }
}
