package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NysiisTest_testRule5 {

    private final Nysiis fullNysiis = new Nysiis(false);

    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    private void assertFullEncoding(final String input, final String expectedEncoding) {
        assertEquals(expectedEncoding, this.fullNysiis.encode(input), "Problem with " + input);
    }

    /**
     * Tests rule 5: If the last character is S, remove it.
     */
    @Test
    void testRule5() {
        assertFullEncoding("XS", "X");
        assertFullEncoding("XSS", "X");
    }
}
