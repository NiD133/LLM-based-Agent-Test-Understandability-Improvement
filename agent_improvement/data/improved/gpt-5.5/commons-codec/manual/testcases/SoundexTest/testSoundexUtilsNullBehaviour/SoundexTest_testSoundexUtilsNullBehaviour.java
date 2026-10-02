package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class SoundexTest_testSoundexUtilsNullBehaviour {

    @Test
    void testSoundexUtilsNullBehaviour() {
        assertNull(SoundexUtils.clean(null), "Cleaning null should preserve the null value");
        assertEquals("", SoundexUtils.clean(""), "Cleaning an empty string should keep it empty");

        assertEquals(0, SoundexUtils.differenceEncoded(null, ""),
                "A null first encoded value should have no matching characters");
        assertEquals(0, SoundexUtils.differenceEncoded("", null),
                "A null second encoded value should have no matching characters");
    }
}
