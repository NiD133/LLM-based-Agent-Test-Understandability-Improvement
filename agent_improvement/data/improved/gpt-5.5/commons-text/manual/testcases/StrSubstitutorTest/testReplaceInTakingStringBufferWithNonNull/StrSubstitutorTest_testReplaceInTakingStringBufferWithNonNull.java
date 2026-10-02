package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInTakingStringBufferWithNonNull {

    private static final String MATCHING_PREFIX_AND_SUFFIX = "WV@i#y?N*[";
    private static final char ESCAPE_CHARACTER = '*';

    @Test
    void testReplaceInTakingStringBufferWithNonNull() {
        final StrSubstitutor strSubstitutor = new StrSubstitutor(
                new HashMap<>(),
                MATCHING_PREFIX_AND_SUFFIX,
                MATCHING_PREFIX_AND_SUFFIX,
                ESCAPE_CHARACTER);

        assertFalse(strSubstitutor.isPreserveEscapes());
        assertFalse(strSubstitutor.replaceIn(new StringBuffer(MATCHING_PREFIX_AND_SUFFIX)));
        assertEquals(ESCAPE_CHARACTER, strSubstitutor.getEscapeChar());
    }
}
