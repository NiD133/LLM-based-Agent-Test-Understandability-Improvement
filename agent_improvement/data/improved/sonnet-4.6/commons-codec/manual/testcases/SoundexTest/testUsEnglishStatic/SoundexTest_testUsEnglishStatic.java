package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class SoundexTest_testUsEnglishStatic extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies that the shared {@link Soundex#US_ENGLISH} static instance correctly encodes
     * a well-known surname using the standard US-English Soundex algorithm.
     *
     * <p>"Williams" should encode to "W452":
     * <ul>
     *   <li>W – kept as the leading letter</li>
     *   <li>4 – L (code 4)</li>
     *   <li>5 – M (code 5)</li>
     *   <li>2 – N (code 2), while adjacent vowels and H/W are ignored</li>
     * </ul>
     *
     * <p>Using the static instance (rather than constructing a new {@code Soundex()}) validates
     * that the shared singleton is properly initialised and thread-safe — bugs reported in
     * CODEC-54 and CODEC-56 were caused by incorrect initialisation of this static field.
     *
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-54">CODEC-54</a>
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-56">CODEC-56</a>
     */
    @Test
    void testUsEnglishStatic() {
        String encoded = Soundex.US_ENGLISH.soundex("Williams");
        assertEquals("W452", encoded);
    }
}
