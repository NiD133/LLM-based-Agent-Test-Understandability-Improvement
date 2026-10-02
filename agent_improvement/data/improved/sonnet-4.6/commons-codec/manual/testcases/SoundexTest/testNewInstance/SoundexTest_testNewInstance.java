package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SoundexTest_testNewInstance extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies that a freshly constructed Soundex instance (default no-arg constructor)
     * correctly encodes "Williams" as "W452".
     *
     * Regression for CODEC-54 and CODEC-56, which reported that the default
     * constructor produced incorrect results due to a static-initialisation ordering
     * problem.  The fix ensures new Soundex() works independently of any previously
     * constructed instance.
     *
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-54">CODEC-54</a>
     * @see <a href="https://issues.apache.org/jira/browse/CODEC-56">CODEC-56</a>
     */
    @Test
    @DisplayName("new Soundex() encodes 'Williams' as W452 (regression: CODEC-54, CODEC-56)")
    void testNewInstance() {
        Soundex soundex = new Soundex();
        String encoded = soundex.soundex("Williams");
        assertEquals("W452", encoded,
                "Default Soundex constructor should encode 'Williams' as W452");
    }
}
