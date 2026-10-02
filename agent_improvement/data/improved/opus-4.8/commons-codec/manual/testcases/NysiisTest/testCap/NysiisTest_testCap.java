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
     * Encodes every input and verifies that each one produces the expected NYSIIS code.
     *
     * @param expectedEncoding the NYSIIS code every input is expected to encode to.
     * @param inputs           the names to encode.
     */
    private void assertAllEncodeTo(final String expectedEncoding, final String... inputs) {
        for (final String input : inputs) {
            assertEquals(expectedEncoding, getStringEncoder().encode(input), "Problem with " + input);
        }
    }

    @Test
    void testCap() {
        // All of these names share the same NYSIIS code: "CAP".
        assertAllEncodeTo("CAP", "Capp", "Cope", "Copp", "Kipp");
    }
}
