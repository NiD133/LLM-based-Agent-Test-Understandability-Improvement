package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link CodecEncoding#getSpecifier} correctly encodes arbitrary
 * (non-canonical) BHSDCodecs as a 3-byte sequence whose first byte is 116, and
 * that the resulting specifier round-trips back to the original codec via
 * {@link CodecEncoding#getCodec}.
 *
 * <p>In the Pack200 specification, codec specifiers 1–115 identify canonical
 * codecs by a single byte. Specifier byte 116 introduces an arbitrary BHSD codec
 * whose parameters are encoded in the two bytes that follow.</p>
 */
public class CodecEncodingTest_testGetSpecifier {

    /**
     * The Pack200 specifier byte that signals an arbitrary (non-canonical) BHSD
     * codec. The two bytes that follow encode the B, H, S, and D parameters.
     */
    private static final int ARBITRARY_BHSD_CODEC_SPECIFIER = 116;

    /**
     * Non-canonical BHSDCodecs whose H value (125) is not present in the 115
     * canonical codec table. Each must be encoded as a 3-element specifier array
     * starting with {@value #ARBITRARY_BHSD_CODEC_SPECIFIER}.
     */
    static Stream<Arguments> nonCanonicalBHSDCodecs() {
        return Stream.of(
            Arguments.of(new BHSDCodec(2, 125, 0, 1)),
            Arguments.of(new BHSDCodec(3, 125, 2, 1)),
            Arguments.of(new BHSDCodec(4, 125)),
            Arguments.of(new BHSDCodec(5, 125, 2, 0)),
            Arguments.of(new BHSDCodec(3, 5, 2, 1))
        );
    }

    /**
     * Checks that {@code getSpecifier} produces exactly 3 integers for a
     * non-canonical BHSDCodec, that the first integer is the arbitrary-codec
     * marker (116), and that feeding the remaining two bytes back into
     * {@code getCodec(116, ...)} reconstructs the original codec.
     */
    @ParameterizedTest
    @MethodSource("nonCanonicalBHSDCodecs")
    void testGetSpecifier(final Codec codec) throws IOException, Pack200Exception {
        final int[] specifier = CodecEncoding.getSpecifier(codec, null);

        assertEquals(3, specifier.length,
            "Non-canonical BHSD codec specifier must be exactly 3 elements");
        assertEquals(ARBITRARY_BHSD_CODEC_SPECIFIER, specifier[0],
            "First specifier element must be 116 to signal an arbitrary BHSD codec");

        // The two trailing bytes carry the encoded B/H/S/D parameters.
        final byte[] encodedParams = { (byte) specifier[1], (byte) specifier[2] };
        final InputStream paramStream = new ByteArrayInputStream(encodedParams);

        assertEquals(codec,
            CodecEncoding.getCodec(ARBITRARY_BHSD_CODEC_SPECIFIER, paramStream, null),
            "Round-trip: codec decoded from specifier bytes must equal the original codec");
    }
}
