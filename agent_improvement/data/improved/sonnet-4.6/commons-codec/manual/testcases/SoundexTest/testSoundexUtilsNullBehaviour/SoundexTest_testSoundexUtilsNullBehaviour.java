package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

public class SoundexTest_testSoundexUtilsNullBehaviour extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void clean_withNullInput_returnsNull() {
        assertNull(SoundexUtils.clean(null));
    }

    @Test
    void clean_withEmptyString_returnsEmptyString() {
        assertEquals("", SoundexUtils.clean(""));
    }

    @Test
    void differenceEncoded_withNullFirstArg_returnsZero() {
        assertEquals(0, SoundexUtils.differenceEncoded(null, ""));
    }

    @Test
    void differenceEncoded_withNullSecondArg_returnsZero() {
        assertEquals(0, SoundexUtils.differenceEncoded("", null));
    }
}
