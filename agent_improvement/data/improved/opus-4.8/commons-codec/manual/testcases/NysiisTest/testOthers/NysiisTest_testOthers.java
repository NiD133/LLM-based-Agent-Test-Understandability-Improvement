package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testOthers extends AbstractStringEncoderTest<Nysiis> {

    /** A non-strict encoder, so encodings are not truncated to 6 characters. */
    private final Nysiis fullNysiis = new Nysiis(false);

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that the non-strict NYSIIS encoder maps the given input to the expected encoding.
     *
     * @param input            the String to encode.
     * @param expectedEncoding the NYSIIS code expected for the input.
     */
    private void assertEncoding(final String input, final String expectedEncoding) {
        assertEquals(expectedEncoding, this.fullNysiis.encode(input), "Problem with " + input);
    }

    /**
     * Tests data gathered from around the internets.
     */
    @Test
    void testOthers() {
        assertEncoding("O'Daniel", "ODANAL");
        assertEncoding("O'Donnel", "ODANAL");
        assertEncoding("Cory", "CARY");
        assertEncoding("Corey", "CARY");
        assertEncoding("Kory", "CARY");
        assertEncoding("FUZZY", "FASY");
    }
}
