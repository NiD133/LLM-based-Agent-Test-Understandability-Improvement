package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests that BCodec with LENIENT policy silently accepts Base64 encoded-word
 * headers whose trailing bits are not valid Base64 padding (i.e. "impossible"
 * samples that would be rejected under STRICT policy).
 */
public class BCodecTest_testBase64ImpossibleSamplesLenient {

    /**
     * RFC 1522 encoded-word headers whose Base64 payload contains impossible
     * trailing bits — bit combinations that cannot result from encoding real
     * bytes. A lenient decoder must not throw for any of these.
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
        // LENIENT policy: impossible trailing bits are silently ignored instead of
        // throwing a DecoderException.
        final BCodec codec = new BCodec(StandardCharsets.UTF_8, CodecPolicy.LENIENT);
        assertFalse(codec.isStrictDecoding(), "Codec created with LENIENT policy must not be strict-decoding");

        for (final String encodedWord : BASE64_IMPOSSIBLE_CASES) {
            // Must complete without throwing despite the invalid trailing bits.
            codec.decode(encodedWord);
        }
    }
}
