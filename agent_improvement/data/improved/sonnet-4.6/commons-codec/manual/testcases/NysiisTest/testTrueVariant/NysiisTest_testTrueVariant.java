package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests the NYSIIS encoder in "true variant" (strict) mode, where encoded strings
 * are capped at a maximum length of 6 characters.
 */
public class NysiisTest_testTrueVariant extends AbstractStringEncoderTest<Nysiis> {

    /** Non-strict NYSIIS encoder that produces encodings of arbitrary length. */
    private final Nysiis fullNysiis = new Nysiis(false);

    /**
     * Takes an array of String pairs where each pair's first element is the input and the second element the expected
     * encoding.
     *
     * @param testValues
     *            an array of String pairs where each pair's first element is the input and the second element the
     *            expected encoding.
     */
    private void assertEncodings(final String[]... testValues) {
        for (final String[] arr : testValues) {
            assertEquals(arr[1], this.fullNysiis.encode(arr[0]), "Problem with " + arr[0]);
        }
    }

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    private void encodeAll(final String[] strings, final String expectedEncoding) {
        for (final String string : strings) {
            assertEquals(expectedEncoding, getStringEncoder().encode(string), "Problem with " + string);
        }
    }

    /**
     * Verifies that strict-mode NYSIIS (the "true variant") truncates encoded output to at most
     * 6 characters. The name "WESTERLUND" is used as the test input; its full encoding would
     * exceed 6 characters, so strict mode must cap it at "WASTAR".
     */
    @Test
    void testTrueVariant() {
        // strict=true enables the original NYSIIS form, which limits output to 6 characters
        final Nysiis strictEncoder = new Nysiis(true);

        final String nysiisCode = strictEncoder.encode("WESTERLUND");

        assertTrue(nysiisCode.length() <= 6, "Strict mode must cap encoded length at 6 characters");
        assertEquals("WASTAR", nysiisCode, "WESTERLUND should encode to WASTAR in strict mode");
    }
}
