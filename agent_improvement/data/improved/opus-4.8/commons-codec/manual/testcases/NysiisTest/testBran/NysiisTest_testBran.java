package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that several distinct names which sound alike all collapse to the same
 * NYSIIS code "BRAN".
 */
public class NysiisTest_testBran extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that every supplied name encodes to {@code expectedEncoding}.
     *
     * @param expectedEncoding the NYSIIS code every name is expected to produce.
     * @param names            the names that should all share the same encoding.
     */
    private void assertAllEncodeTo(final String expectedEncoding, final String... names) {
        for (final String name : names) {
            assertEquals(expectedEncoding, getStringEncoder().encode(name), "Problem with " + name);
        }
    }

    @Test
    void testBran() {
        assertAllEncodeTo("BRAN", "Brian", "Brown", "Brun");
    }
}
