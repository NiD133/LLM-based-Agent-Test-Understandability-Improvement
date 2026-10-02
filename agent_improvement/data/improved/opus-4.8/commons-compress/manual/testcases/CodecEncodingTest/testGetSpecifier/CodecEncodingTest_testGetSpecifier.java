package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CodecEncodingTest_testGetSpecifier {

    /**
     * Non-canonical {@link BHSDCodec}s that cannot be represented by a single
     * specifier byte, so {@link CodecEncoding#getSpecifier} must emit the
     * arbitrary-codec form (value 116 followed by two header bytes).
     */
    static Stream<Arguments> specifier() {
        return Stream.of(
                Arguments.of(new BHSDCodec(2, 125, 0, 1)),
                Arguments.of(new BHSDCodec(3, 125, 2, 1)),
                Arguments.of(new BHSDCodec(4, 125)),
                Arguments.of(new BHSDCodec(5, 125, 2, 0)),
                Arguments.of(new BHSDCodec(3, 5, 2, 1)));
    }

    /**
     * Encoding a non-canonical codec to its specifier and decoding it back must
     * yield an equal codec. The specifier is the arbitrary form: index 116 plus
     * two header bytes describing the codec.
     */
    @ParameterizedTest
    @MethodSource("specifier")
    void testGetSpecifier(final Codec originalCodec) throws IOException, Pack200Exception {
        final int[] specifiers = CodecEncoding.getSpecifier(originalCodec, null);

        // 116 marks the arbitrary-codec form, followed by exactly two header bytes.
        assertEquals(3, specifiers.length);
        assertEquals(116, specifiers[0]);

        // Decoding the two header bytes must reconstruct an equal codec.
        final byte[] headerBytes = { (byte) specifiers[1], (byte) specifiers[2] };
        final InputStream in = new ByteArrayInputStream(headerBytes);
        assertEquals(originalCodec, CodecEncoding.getCodec(116, in, null));
    }
}
