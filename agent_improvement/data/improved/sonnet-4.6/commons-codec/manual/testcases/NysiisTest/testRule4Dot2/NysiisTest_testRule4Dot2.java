package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testRule4Dot2 extends AbstractStringEncoderTest<Nysiis> {

    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Rule 4b: Q is transcoded to G in the middle of a name.
     */
    @Test
    void testRule4Dot2_Q_transcodesToG() {
        assertEquals("XG", fullNysiis.encode("XQ"), "Rule 4b: Q should be transcoded to G");
    }

    /**
     * Rule 4c: Z is transcoded to S in the middle of a name.
     * Note: the trailing S is subsequently removed by rule 5, so "XZ" encodes to "X".
     */
    @Test
    void testRule4Dot2_Z_transcodesToS_thenTrailingSRemoved() {
        assertEquals("X", fullNysiis.encode("XZ"), "Rule 4c + rule 5: Z transcodes to S, then trailing S is dropped");
    }

    /**
     * Rule 4d: M is transcoded to N in the middle of a name.
     */
    @Test
    void testRule4Dot2_M_transcodesToN() {
        assertEquals("XN", fullNysiis.encode("XM"), "Rule 4d: M should be transcoded to N");
    }
}
