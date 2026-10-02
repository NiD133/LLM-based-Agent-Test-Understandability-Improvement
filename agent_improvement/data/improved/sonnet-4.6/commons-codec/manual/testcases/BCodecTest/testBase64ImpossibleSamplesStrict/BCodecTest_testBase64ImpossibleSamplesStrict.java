package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class BCodecTest_testBase64ImpossibleSamplesStrict {

    /**
     * RFC 1522 encoded-word headers whose Base64 payload contains non-zero trailing
     * bits that are not part of a valid Base64 encoding.  Under STRICT decoding
     * policy these must be rejected with a DecoderException rather than silently
     * decoded (lenient behaviour).
     */
    private static final String[] BASE64_IMPOSSIBLE_CASES = {
        "=?ASCII?B?ZE==?=",       // 1 data byte  – invalid padding bits in last quantum
        "=?ASCII?B?ZmC=?=",       // 2 data bytes – invalid padding bits in last quantum
        "=?ASCII?B?Zm9vYE==?=",   // 4 data bytes – invalid padding bits in last quantum
        "=?ASCII?B?Zm9vYmC=?=",   // 5 data bytes – invalid padding bits in last quantum
        "=?ASCII?B?AB==?="        // 1 data byte  – non-zero trailing bits
    };

    @Test
    void testBase64ImpossibleSamplesStrict() {
        // Create a BCodec that enforces strict Base64 validation on decode.
        final BCodec codec = new BCodec(StandardCharsets.UTF_8, CodecPolicy.STRICT);
        assertTrue(codec.isStrictDecoding(),
            "Codec created with STRICT policy must report isStrictDecoding() == true");

        // Every encoded-word in BASE64_IMPOSSIBLE_CASES has trailing bits that are
        // non-zero and therefore cannot represent a valid byte sequence.  Strict mode
        // must reject them all.
        for (final String encodedWord : BASE64_IMPOSSIBLE_CASES) {
            assertThrows(DecoderException.class,
                () -> codec.decode(encodedWord),
                "Expected DecoderException for impossible Base64 case: " + encodedWord);
        }
    }
}
