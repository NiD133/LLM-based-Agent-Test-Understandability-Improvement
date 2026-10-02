package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class BCodecTest_testBase64ImpossibleSamplesLenient {

    private static final String[] BASE64_IMPOSSIBLE_ENCODED_WORDS = {
            "=?ASCII?B?ZE==?=",
            "=?ASCII?B?ZmC=?=",
            "=?ASCII?B?Zm9vYE==?=",
            "=?ASCII?B?Zm9vYmC=?=",
            "=?ASCII?B?AB==?="
    };

    @Test
    void testBase64ImpossibleSamplesLenient() throws DecoderException {
        final BCodec codec = new BCodec(StandardCharsets.UTF_8, CodecPolicy.LENIENT);

        assertFalse(codec.isStrictDecoding());
        for (final String encodedWord : BASE64_IMPOSSIBLE_ENCODED_WORDS) {
            codec.decode(encodedWord);
        }
    }
}
