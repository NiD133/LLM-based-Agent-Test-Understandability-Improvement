package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that a freshly constructed {@link Soundex} instance encodes a name
 * using the default US English mapping.
 */
public class SoundexTest_testNewInstance extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies that the no-argument {@link Soundex} constructor produces a usable
     * encoder that yields the expected Soundex code for a known input.
     *
     * <p>The name {@code "Williams"} encodes to {@code "W452"}: the leading
     * {@code W} is kept verbatim, then {@code l} -&gt; 4, {@code m} -&gt; 5 and
     * {@code s} -&gt; 2 (vowels are dropped).</p>
     *
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-54">CODEC-54</a>
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-56">CODEC-56</a>
     */
    @Test
    void testNewInstance() {
        final Soundex soundex = new Soundex();

        final String expectedCode = "W452";
        final String actualCode = soundex.soundex("Williams");

        assertEquals(expectedCode, actualCode);
    }
}
