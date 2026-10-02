package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class BCodecTest_testBase64ImpossibleSamplesStrict {

    private static final String[] BASE64_IMPOSSIBLE_CASES = {
            "=?ASCII?B?ZE==?=",
            "=?ASCII?B?ZmC=?=",
            "=?ASCII?B?Zm9vYE==?=",
            "=?ASCII?B?Zm9vYmC=?=",
            "=?ASCII?B?AB==?="
    };

    @Test
    void testBase64ImpossibleSamplesStrict() {
        final BCodec codec = new BCodec(StandardCharsets.UTF_8, CodecPolicy.STRICT);

        assertTrue(codec.isStrictDecoding());
        for (final String encodedWord : BASE64_IMPOSSIBLE_CASES) {
            assertRejectsStrictBase64(codec, encodedWord);
        }
    }

    private void assertRejectsStrictBase64(final BCodec codec, final String encodedWord) {
        assertThrows(DecoderException.class, () -> codec.decode(encodedWord));
    }
}
