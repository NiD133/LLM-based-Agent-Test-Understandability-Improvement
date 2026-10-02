package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testDad {

    private static final String NAME_WITH_NT_SUFFIX = "Dent";
    private static final String EXPECTED_ENCODING = "DAD";

    @Test
    void testDad() {
        assertNysiisEncoding(NAME_WITH_NT_SUFFIX, EXPECTED_ENCODING);
    }

    private void assertNysiisEncoding(final String input, final String expectedEncoding) {
        assertEquals(expectedEncoding, new Nysiis().encode(input), "Problem with " + input);
    }
}
