package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testRule2 extends AbstractStringEncoderTest<Nysiis> {

    /** Non-strict encoder so results are not truncated to the strict 6-character limit. */
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that encoding {@code input} with the non-strict NYSIIS encoder yields {@code expected}.
     */
    private void assertEncodesTo(final String input, final String expected) {
        assertEquals(expected, this.fullNysiis.encode(input), "Problem with " + input);
    }

    /**
     * Tests rule 2: translate the last characters of a name.
     * <ul>
     *   <li>2a. EE, IE             &rarr; Y</li>
     *   <li>2b. DT, RT, RD, NT, ND &rarr; D</li>
     * </ul>
     */
    @Test
    void testRule2() {
        // 2a. trailing EE / IE collapse to Y
        assertEncodesTo("XEE", "XY");
        assertEncodesTo("XIE", "XY");

        // 2b. trailing DT / RT / RD / NT / ND collapse to D
        assertEncodesTo("XDT", "XD");
        assertEncodesTo("XRT", "XD");
        assertEncodesTo("XRD", "XD");
        assertEncodesTo("XNT", "XD");
        assertEncodesTo("XND", "XD");
    }
}
