package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link SoundexUtils} handles {@code null} and empty inputs.
 */
public class SoundexTest_testSoundexUtilsNullBehaviour extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testSoundexUtilsNullBehaviour() {
        // clean(): null stays null, and an empty string stays empty.
        assertNull(SoundexUtils.clean(null));
        assertEquals("", SoundexUtils.clean(""));

        // differenceEncoded(): a null on either side yields a difference of 0.
        final int noDifferenceWhenFirstIsNull = SoundexUtils.differenceEncoded(null, "");
        final int noDifferenceWhenSecondIsNull = SoundexUtils.differenceEncoded("", null);
        assertEquals(0, noDifferenceWhenFirstIsNull);
        assertEquals(0, noDifferenceWhenSecondIsNull);
    }
}
