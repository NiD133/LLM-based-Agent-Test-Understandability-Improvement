package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the NYSIIS (New York State Identification and Intelligence System)
 * phonetic encoding algorithm, focusing on names that share a common NYSIIS code.
 */
public class NysiisTest_testDan extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Asserts that each of the given names encodes to the specified NYSIIS code
     * using the strict (default) encoder.
     */
    private void assertAllEncodeToSameCode(final String[] names, final String expectedCode) {
        for (final String name : names) {
            assertEquals(expectedCode, getStringEncoder().encode(name), "Problem with " + name);
        }
    }

    @Test
    @DisplayName("Names phonetically similar to 'Dan' (Dane, Dean, Dionne) should all encode to 'DAN'")
    void testDan() {
        // These phonetically related names all map to the same NYSIIS code "DAN"
        assertAllEncodeToSameCode(new String[] { "Dane", "Dean", "Dionne" }, "DAN");
    }
}
