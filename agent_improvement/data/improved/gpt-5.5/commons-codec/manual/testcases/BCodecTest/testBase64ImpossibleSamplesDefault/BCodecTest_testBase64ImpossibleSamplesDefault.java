package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class BCodecTest_testBase64ImpossibleSamplesDefault {

    private static final String[] RFC_1522_BASE64_SAMPLES_WITH_IMPOSSIBLE_TRAILING_BITS = {
        "=?ASCII?B?ZE==?=",
        "=?ASCII?B?ZmC=?=",
        "=?ASCII?B?Zm9vYE==?=",
        "=?ASCII?B?Zm9vYmC=?=",
        "=?ASCII?B?AB==?="
    };

    @Test
    void testBase64ImpossibleSamplesDefault() throws DecoderException {
        final BCodec codec = new BCodec();

        assertFalse(codec.isStrictDecoding());
        for (final String encodedWord : RFC_1522_BASE64_SAMPLES_WITH_IMPOSSIBLE_TRAILING_BITS) {
            codec.decode(encodedWord);
        }
    }
}
