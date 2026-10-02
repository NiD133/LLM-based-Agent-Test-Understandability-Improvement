package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link CodecEncoding#getCodec} for the "arbitrary" meta-encoding.
 *
 * <p>The value {@code 116} tells {@code getCodec} that the codec is not one of the
 * canonical encodings but is instead described by the next two bytes read from the
 * stream. Those two bytes are decoded into the (B, H, S, D) parameters of a
 * {@link BHSDCodec}, whose {@code toString()} renders as {@code "(B,H,S,D)"}
 * (trailing zero parameters are omitted).</p>
 */
public class CodecEncodingTest_testArbitraryCodec {

    /** The meta-encoding value that selects an arbitrary, stream-described codec. */
    private static final int ARBITRARY_CODEC_VALUE = 116;

    /**
     * Each case pairs the two raw header bytes with the {@code BHSDCodec.toString()}
     * representation that {@code getCodec} is expected to produce from them.
     */
    static Stream<Arguments> arbitraryCodec() {
        return Stream.of(
                Arguments.of("(1,256)", new byte[] { 0x00, (byte) 0xFF }),
                Arguments.of("(5,128,2,1)", new byte[] { 0x25, (byte) 0x7F }),
                Arguments.of("(2,128,1,1)", new byte[] { 0x0B, (byte) 0x7F }));
    }

    @ParameterizedTest
    @MethodSource("arbitraryCodec")
    void testArbitraryCodec(final String expected, final byte[] headerBytes) throws IOException, Pack200Exception {
        final ByteArrayInputStream headerStream = new ByteArrayInputStream(headerBytes);

        final Codec decodedCodec = CodecEncoding.getCodec(ARBITRARY_CODEC_VALUE, headerStream, null);

        assertEquals(expected, decodedCodec.toString());
    }
}
