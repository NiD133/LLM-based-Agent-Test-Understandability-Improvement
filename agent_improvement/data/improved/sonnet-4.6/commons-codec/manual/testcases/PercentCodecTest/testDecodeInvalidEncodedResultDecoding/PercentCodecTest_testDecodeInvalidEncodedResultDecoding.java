package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class PercentCodecTest_testDecodeInvalidEncodedResultDecoding {

    @Test
    void testDecodeInvalidEncodedResultDecoding() throws Exception {
        // Greek letters alpha (α) and beta (β) produce multi-byte UTF-8 sequences
        // that get percent-encoded into sequences of 3 bytes each (e.g. %CE%B1).
        final String greekLetters = "αβ";
        final PercentCodec percentCodec = new PercentCodec();
        final byte[] encoded = percentCodec.encode(greekLetters.getBytes(StandardCharsets.UTF_8));

        // Drop the last byte to break the final percent-encoded triplet (%XX → %X),
        // leaving an incomplete escape sequence that the decoder cannot read.
        final byte[] truncatedEncoded = Arrays.copyOf(encoded, encoded.length - 1);

        // Decoding an incomplete percent-encoded sequence must raise DecoderException
        // whose root cause is ArrayIndexOutOfBoundsException from reading past the buffer end.
        final DecoderException exception = assertThrows(DecoderException.class,
                () -> percentCodec.decode(truncatedEncoded));
        assertInstanceOf(ArrayIndexOutOfBoundsException.class, exception.getCause());
    }
}
