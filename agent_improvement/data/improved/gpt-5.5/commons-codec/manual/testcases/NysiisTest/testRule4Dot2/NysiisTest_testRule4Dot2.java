package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testRule4Dot2 {

    private final Nysiis fullNysiis = new Nysiis(false);

    private void assertFullNysiisEncoding(final String input, final String expectedEncoding) {
        assertEquals(expectedEncoding, fullNysiis.encode(input), "Problem with " + input);
    }

    /**
     * Tests rule 4.2: Q -> G, Z -> S, M -> N.
     */
    @Test
    void testRule4Dot2() {
        assertFullNysiisEncoding("XQ", "XG");
        assertFullNysiisEncoding("XZ", "X");
        assertFullNysiisEncoding("XM", "XN");
    }
}
