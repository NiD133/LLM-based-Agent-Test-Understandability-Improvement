package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testSnat extends AbstractStringEncoderTest<Nysiis> {

    /** Expected NYSIIS encoding shared by every input name in this test. */
    private static final String EXPECTED_CODE = "SNAT";

    /** Names that should all encode to {@link #EXPECTED_CODE}. */
    private static final String[] NAMES_ENCODING_TO_SNAT = { "Smith", "Schmit" };

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    @Test
    void testSnat() {
        final Nysiis encoder = getStringEncoder();
        for (final String name : NAMES_ENCODING_TO_SNAT) {
            assertEquals(EXPECTED_CODE, encoder.encode(name), "Problem with " + name);
        }
    }
}
