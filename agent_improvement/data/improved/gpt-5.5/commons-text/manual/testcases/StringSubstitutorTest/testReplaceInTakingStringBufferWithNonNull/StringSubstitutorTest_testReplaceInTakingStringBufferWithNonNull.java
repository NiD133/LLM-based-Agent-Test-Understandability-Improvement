package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceInTakingStringBufferWithNonNull {

    private static final String VARIABLE_MARKER = "WV@i#y?N*[";
    private static final char ESCAPE_CHARACTER = '*';

    @Test
    void testReplaceInTakingStringBufferWithNonNull() {
        final StringSubstitutor substitutor = new StringSubstitutor(
                new HashMap<>(),
                VARIABLE_MARKER,
                VARIABLE_MARKER,
                ESCAPE_CHARACTER);

        assertFalse(substitutor.isPreserveEscapes());
        assertFalse(substitutor.replaceIn(new StringBuffer(VARIABLE_MARKER)));
        assertEquals(ESCAPE_CHARACTER, substitutor.getEscapeChar());
    }
}
