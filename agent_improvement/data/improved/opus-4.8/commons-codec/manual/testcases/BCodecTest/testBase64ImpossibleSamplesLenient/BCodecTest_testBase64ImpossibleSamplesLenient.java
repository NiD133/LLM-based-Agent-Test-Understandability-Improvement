package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class BCodecTest_testBase64ImpossibleSamplesLenient {

    /**
     * RFC 1522 "encoded-word" headers whose Base64 payloads carry trailing bits that cannot
     * form a whole byte. A lenient decoder must accept these by discarding the impossible bits,
     * whereas a strict decoder would reject them.
     */
    private static final String[] BASE64_IMPOSSIBLE_CASES = {
        "=?ASCII?B?ZE==?=",
        "=?ASCII?B?ZmC=?=",
        "=?ASCII?B?Zm9vYE==?=",
        "=?ASCII?B?Zm9vYmC=?=",
        "=?ASCII?B?AB==?="
    };

    @Test
    void testBase64ImpossibleSamplesLenient() throws DecoderException {
        final BCodec codec = new BCodec(StandardCharsets.UTF_8, CodecPolicy.LENIENT);

        // A lenient codec reports that it is not using strict decoding.
        assertFalse(codec.isStrictDecoding());

        // Lenient decoding accepts every impossible sample without throwing.
        for (final String impossibleSample : BASE64_IMPOSSIBLE_CASES) {
            codec.decode(impossibleSample);
        }
    }
}
