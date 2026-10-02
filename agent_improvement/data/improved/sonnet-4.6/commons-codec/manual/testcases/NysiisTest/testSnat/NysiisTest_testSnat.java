package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class NysiisTest_testSnat extends AbstractStringEncoderTest<Nysiis> {

    @Override
    protected Nysiis createStringEncoder() {
        return new Nysiis();
    }

    /**
     * Encodes each string and asserts it matches the expected NYSIIS encoding.
     */
    private void encodeAll(final String[] strings, final String expectedEncoding) {
        for (final String string : strings) {
            assertEquals(expectedEncoding, getStringEncoder().encode(string), "Problem with " + string);
        }
    }

    /**
     * Verifies that phonetically similar names with different spellings (e.g. "Smith" and "Schmit")
     * both encode to the same NYSIIS key ("SNAT"), demonstrating the algorithm's ability to
     * group similar-sounding surnames together.
     */
    @Test
    void testSnat() {
        encodeAll(new String[] { "Smith", "Schmit" }, "SNAT");
    }
}
