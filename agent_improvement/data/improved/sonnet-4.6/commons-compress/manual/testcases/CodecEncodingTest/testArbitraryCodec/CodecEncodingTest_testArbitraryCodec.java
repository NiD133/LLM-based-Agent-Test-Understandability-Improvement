package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link CodecEncoding#getCodec} correctly decodes the "arbitrary codec" escape
 * (specifier value 116). When value 116 is read, the next two bytes from the stream encode a
 * BHSDCodec: the first byte packs d (bit 0), s (bits 1-2) and b-1 (bits 3-5); the second byte
 * encodes h-1.
 */
public class CodecEncodingTest_testArbitraryCodec {

    /**
     * Pack200 spec value that signals an arbitrary (non-canonical) BHSDCodec follows in the stream.
     */
    private static final int ARBITRARY_CODEC_SPECIFIER = 116;

    /**
     * Provides (expectedToString, encodedBytes) pairs. Each two-byte sequence encodes a BHSDCodec
     * as defined in the Pack200 spec section 6.7.3:
     * <pre>
     *   byte[0]: d = bit0, s = bits1-2, b = bits3-5 + 1
     *   byte[1]: h = value + 1
     * </pre>
     */
    static Stream<Arguments> arbitraryCodec() {
        return Stream.of(
            // 0x00 → d=0, s=0, b=1 ; 0xFF → h=256  ⇒ BHSDCodec(1,256)
            Arguments.of("(1,256)",     new byte[] { 0x00, (byte) 0xFF }),

            // 0x25 → d=1, s=2, b=5 ; 0x7F → h=128  ⇒ BHSDCodec(5,128,2,1)
            Arguments.of("(5,128,2,1)", new byte[] { 0x25, (byte) 0x7F }),

            // 0x0B → d=1, s=1, b=2 ; 0x7F → h=128  ⇒ BHSDCodec(2,128,1,1)
            Arguments.of("(2,128,1,1)", new byte[] { 0x0B, (byte) 0x7F })
        );
    }

    /**
     * Verifies that decoding an arbitrary-codec byte sequence (prefixed by specifier 116) produces
     * the expected BHSDCodec string representation.
     */
    @ParameterizedTest
    @MethodSource("arbitraryCodec")
    void testArbitraryCodec(final String expectedCodecString, final byte[] encodedBytes)
            throws IOException, Pack200Exception {
        String actualCodecString = CodecEncoding
                .getCodec(ARBITRARY_CODEC_SPECIFIER, new ByteArrayInputStream(encodedBytes), null)
                .toString();
        assertEquals(expectedCodecString, actualCodecString);
    }
}
