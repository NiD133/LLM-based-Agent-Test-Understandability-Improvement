package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BCodec}, when configured with its default (lenient) decoding policy,
 * tolerates RFC 1522 "encoded-word" headers whose Base64 payloads contain trailing bits that
 * cannot form complete bytes. Such payloads are "impossible" under strict decoding, but lenient
 * decoding must accept them by discarding the leftover bits instead of throwing.
 */
public class BCodecTest_testBase64ImpossibleSamplesDefault {

    /**
     * RFC 1522 encoded-word headers ("=?charset?B?payload?=") whose Base64 payloads have leftover
     * bits at the end. A strict decoder would reject these; a lenient decoder must accept them.
     */
    private static final String[] BASE64_IMPOSSIBLE_CASES = {
        "=?ASCII?B?ZE==?=",
        "=?ASCII?B?ZmC=?=",
        "=?ASCII?B?Zm9vYE==?=",
        "=?ASCII?B?Zm9vYmC=?=",
        "=?ASCII?B?AB==?="
    };

    @Test
    void testBase64ImpossibleSamplesDefault() throws DecoderException {
        final BCodec codec = new BCodec();

        // The default decoding policy is lenient, so impossible payloads are tolerated.
        assertFalse(codec.isStrictDecoding(), "default BCodec should use lenient decoding");

        // Each impossible sample must decode without throwing under the lenient policy.
        for (final String impossibleSample : BASE64_IMPOSSIBLE_CASES) {
            codec.decode(impossibleSample);
        }
    }
}
