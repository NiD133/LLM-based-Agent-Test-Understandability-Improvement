package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testRule6 extends AbstractStringEncoderTest<Nysiis> {

    // Non-strict (full-length) encoder used to test suffix-trimming rules without the 6-char cap.
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Rule 6: if the encoded key ends with "AY", replace that pair with just "Y".
     *
     * Two cases are covered:
     *   "XAY"  – rule 6 alone applies  (AY → Y)            → "XY"
     *   "XAYS" – rule 5 fires first (strip trailing S → "XAY"),
     *            then rule 6 fires        (AY → Y)          → "XY"
     */
    @Test
    void testRule6() {
        // Rule 6 only: trailing "AY" is replaced by "Y"
        assertEquals("XY", fullNysiis.encode("XAY"),
                "Expected trailing AY to be replaced with Y");

        // Rules 5 then 6: trailing S is stripped first, exposing AY which is then replaced with Y
        assertEquals("XY", fullNysiis.encode("XAYS"),
                "Expected trailing S to be removed and then AY to be replaced with Y");
    }
}
