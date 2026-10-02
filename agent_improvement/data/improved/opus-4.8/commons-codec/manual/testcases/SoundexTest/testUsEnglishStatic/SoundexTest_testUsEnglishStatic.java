package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies the shared {@link Soundex#US_ENGLISH} singleton instance.
 */
public class SoundexTest_testUsEnglishStatic extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Encoding a name through the static {@link Soundex#US_ENGLISH} instance must yield the
     * standard four-character Soundex code: the leading letter followed by three digits.
     *
     * <p>For "Williams" the expected code is "W452":
     * the initial "W" is kept, "ll" maps to 4, "ms" maps to 5, and the trailing "s" maps to 2.</p>
     *
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-54">CODEC-54</a>
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-56">CODEC-56</a>
     */
    @Test
    void usEnglishStaticInstanceEncodesNameToSoundexCode() {
        final String soundexCode = Soundex.US_ENGLISH.soundex("Williams");

        assertEquals("W452", soundexCode);
    }
}
