package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class BCodecTest_testBase64ImpossibleSamplesStrict {

    /**
     * RFC 1522 "encoded-word" headers whose Base64 payload has trailing bits that
     * cannot form a whole byte. Under {@link CodecPolicy#STRICT} decoding, each of
     * these must be rejected rather than silently truncated.
     */
    private static final String[] BASE64_IMPOSSIBLE_ENCODED_WORDS = {
        "=?ASCII?B?ZE==?=",
        "=?ASCII?B?ZmC=?=",
        "=?ASCII?B?Zm9vYE==?=",
        "=?ASCII?B?Zm9vYmC=?=",
        "=?ASCII?B?AB==?="
    };

    @Test
    void testBase64ImpossibleSamplesStrict() {
        final BCodec strictCodec = new BCodec(StandardCharsets.UTF_8, CodecPolicy.STRICT);

        assertTrue(strictCodec.isStrictDecoding(), "Codec should report strict decoding");

        for (final String impossibleEncodedWord : BASE64_IMPOSSIBLE_ENCODED_WORDS) {
            assertThrows(DecoderException.class, () -> strictCodec.decode(impossibleEncodedWord),
                () -> "Strict decoding must reject impossible Base64: " + impossibleEncodedWord);
        }
    }
}
