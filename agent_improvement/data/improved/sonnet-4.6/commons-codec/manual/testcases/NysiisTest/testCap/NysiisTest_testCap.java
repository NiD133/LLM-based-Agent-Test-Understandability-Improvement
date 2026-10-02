package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testCap extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Verifies that names with the same "CAP" sound but different spellings (including
     * an initial 'K' transcoded to 'C') all produce the same NYSIIS code "CAP".
     */
    @Test
    void testCap() {
        assertEquals("CAP", getStringEncoder().encode("Capp"), "Problem with Capp");
        assertEquals("CAP", getStringEncoder().encode("Cope"), "Problem with Cope");
        assertEquals("CAP", getStringEncoder().encode("Copp"), "Problem with Copp");
        assertEquals("CAP", getStringEncoder().encode("Kipp"), "Problem with Kipp");
    }
}
