package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testRule5 extends AbstractStringEncoderTest<Nysiis> {

    /** Non-strict encoder so results are not truncated to 6 characters. */
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Tests rule 5: if the last character of the encoding is {@code S}, remove it.
     * <p>
     * Both "XS" and "XSS" should therefore encode to "X" once the trailing
     * S characters are dropped.
     * </p>
     */
    @Test
    void testRule5() {
        assertEquals("X", fullNysiis.encode("XS"), "Trailing S should be removed from XS");
        assertEquals("X", fullNysiis.encode("XSS"), "Trailing S should be removed from XSS");
    }
}
