package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the NYSIIS encoder follows the lesser-used "special" transcoding
 * branches of its algorithm (e.g. the EV/H/W rules and trailing-character clean-up).
 */
public class NysiisTest_testSpecialBranches extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that the default (strict) NYSIIS encoder maps {@code input} to {@code expectedCode}.
     *
     * @param input        the name to encode.
     * @param expectedCode the NYSIIS code the encoder is expected to produce.
     */
    private void assertNysiisCode(final String input, final String expectedCode) {
        assertEquals(expectedCode, getStringEncoder().encode(input), "Problem with " + input);
    }

    @Test
    void testSpecialBranches() {
        assertNysiisCode("Kobwick", "CABWAC");
        assertNysiisCode("Kocher", "CACAR");
        assertNysiisCode("Fesca", "FASC");
        assertNysiisCode("Shom", "SAN");
        assertNysiisCode("Ohlo", "OL");
        assertNysiisCode("Uhu", "UH");
        assertNysiisCode("Um", "UN");
    }
}
