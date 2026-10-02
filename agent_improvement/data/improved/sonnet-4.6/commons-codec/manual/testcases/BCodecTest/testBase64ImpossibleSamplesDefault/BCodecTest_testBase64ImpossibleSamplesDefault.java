package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class BCodecTest_testBase64ImpossibleSamplesDefault {

    // RFC 1522 "encoded-word" headers whose Base64 payload contains bit patterns
    // that are mathematically impossible in valid Base64 (e.g. non-zero padding bits).
    // Under lenient (default) decoding these must be accepted without throwing.
    private static final String[] BASE64_IMPOSSIBLE_CASES = {
        "=?ASCII?B?ZE==?=",
        "=?ASCII?B?ZmC=?=",
        "=?ASCII?B?Zm9vYE==?=",
        "=?ASCII?B?Zm9vYmC=?=",
        "=?ASCII?B?AB==?="
    };

    @Test
    void testBase64ImpossibleSamplesDefault() throws DecoderException {
        // The no-arg constructor uses LENIENT decoding policy by default.
        final BCodec codec = new BCodec();
        assertFalse(codec.isStrictDecoding(),
                "Default BCodec should use lenient (non-strict) decoding");

        // Lenient decoding must silently accept impossible Base64 samples
        // instead of throwing DecoderException.
        for (final String encodedWord : BASE64_IMPOSSIBLE_CASES) {
            codec.decode(encodedWord);
        }
    }
}
