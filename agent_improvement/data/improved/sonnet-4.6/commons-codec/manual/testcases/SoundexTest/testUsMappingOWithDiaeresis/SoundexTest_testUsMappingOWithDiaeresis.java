package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testUsMappingOWithDiaeresis extends AbstractStringEncoderTest<Soundex> {

    // U+00F6: Latin small letter o with diaeresis (ö), a "fancy" character outside the A-Z range
    private static final String O_WITH_DIAERESIS = "ö";

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * The default US Soundex mapping only covers the 26 ASCII letters (A-Z).
     * Characters outside that range, such as o-with-diaeresis (ö, U+00F6), are
     * not mapped and cause an IllegalArgumentException when they are classified
     * as letters by the JVM.
     *
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-30">CODEC-30</a>
     */
    @Test
    void testUsMappingOWithDiaeresis() {
        // Plain ASCII 'o' encodes normally to its Soundex representation
        assertEquals("O000", getStringEncoder().encode("o"));

        if (Character.isLetter(O_WITH_DIAERESIS.charAt(0))) {
            // ö is recognised as a letter, so the encoder attempts to look it up
            // in the US mapping table and throws because it has no entry there.
            assertThrows(IllegalArgumentException.class,
                    () -> getStringEncoder().encode(O_WITH_DIAERESIS));
        } else {
            // ö is not recognised as a letter, so it is stripped during cleaning
            // and the encoder returns an empty string.
            assertEquals("", getStringEncoder().encode(O_WITH_DIAERESIS));
        }
    }
}
