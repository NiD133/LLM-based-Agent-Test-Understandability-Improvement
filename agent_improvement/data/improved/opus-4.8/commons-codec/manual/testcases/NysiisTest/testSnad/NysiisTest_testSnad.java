package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testSnad extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    @Test
    void testSnad() {
        // "Data Quality and Record Linkage Techniques" (p.121) lists this as SNAT,
        // but the correct NYSIIS encoding for "Schmidt" is SNAD.
        final String actualEncoding = getStringEncoder().encode("Schmidt");

        assertEquals("SNAD", actualEncoding, "Problem with Schmidt");
    }
}
