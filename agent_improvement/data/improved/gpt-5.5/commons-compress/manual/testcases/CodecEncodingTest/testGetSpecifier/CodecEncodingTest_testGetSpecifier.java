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

    private static final int ARBITRARY_BHSD_ENCODING = 116;
    private static final int EXPECTED_ARBITRARY_SPECIFIER_LENGTH = 3;

    static Stream<Arguments> specifier() {
        return Stream.of(
                Arguments.of(new BHSDCodec(2, 125, 0, 1)),
                Arguments.of(new BHSDCodec(3, 125, 2, 1)),
                Arguments.of(new BHSDCodec(4, 125)),
                Arguments.of(new BHSDCodec(5, 125, 2, 0)),
                Arguments.of(new BHSDCodec(3, 5, 2, 1)));
    }

    @ParameterizedTest
    @MethodSource("specifier")
    void testGetSpecifier(final Codec codec) throws IOException, Pack200Exception {
        final int[] specifiers = CodecEncoding.getSpecifier(codec, null);

        assertEquals(EXPECTED_ARBITRARY_SPECIFIER_LENGTH, specifiers.length);
        assertEquals(ARBITRARY_BHSD_ENCODING, specifiers[0]);

        final byte[] codecHeader = { (byte) specifiers[1], (byte) specifiers[2] };
        final InputStream in = new ByteArrayInputStream(codecHeader);
        assertEquals(codec, CodecEncoding.getCodec(ARBITRARY_BHSD_ENCODING, in, null));
    }
}
