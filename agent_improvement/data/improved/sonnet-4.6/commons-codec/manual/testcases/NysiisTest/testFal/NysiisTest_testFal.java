package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testFal extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that the default (strict) NYSIIS encoder produces {@code expectedEncoding}
     * for every string in {@code inputs}.
     */
    private void encodeAll(final String[] inputs, final String expectedEncoding) {
        for (final String input : inputs) {
            assertEquals(expectedEncoding, getStringEncoder().encode(input), "Problem with " + input);
        }
    }

    /**
     * Verifies that "Phil" encodes to "FAL" under the strict NYSIIS algorithm.
     * The leading "PH" is transcoded to "FF", yielding first key character 'F';
     * the vowel 'i' becomes 'A'; and 'l' remains 'L', giving the three-character code "FAL".
     */
    @Test
    void testFal() {
        encodeAll(new String[] { "Phil" }, "FAL");
    }
}
