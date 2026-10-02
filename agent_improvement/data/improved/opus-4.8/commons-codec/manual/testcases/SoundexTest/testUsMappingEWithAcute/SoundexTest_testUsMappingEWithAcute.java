package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the default US English Soundex mapping does not support accented
 * ("fancy") characters such as e-acute.
 *
 * @see <a href="https://issues.apache.org/jira/browse/CODEC-30">CODEC-30</a>
 */
public class SoundexTest_testUsMappingEWithAcute extends AbstractStringEncoderTest<Soundex> {

    /** The lowercase letter e-acute (é), an accented character outside the A-Z mapping. */
    private static final String E_WITH_ACUTE = "é";

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testUsMappingEWithAcute() {
        final Soundex soundex = getStringEncoder();

        // A plain ASCII letter is encoded normally.
        assertEquals("E000", soundex.encode("e"));

        // The accented character is not part of the US mapping.
        if (Character.isLetter('é')) {
            // When treated as a letter, encoding it fails because it has no mapping.
            assertThrows(IllegalArgumentException.class, () -> soundex.encode(E_WITH_ACUTE));
        } else {
            // Otherwise it is stripped out, leaving an empty result.
            assertEquals("", soundex.encode(E_WITH_ACUTE));
        }
    }
}
