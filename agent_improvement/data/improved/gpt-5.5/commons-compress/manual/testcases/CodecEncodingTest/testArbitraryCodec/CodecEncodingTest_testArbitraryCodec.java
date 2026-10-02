package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CodecEncodingTest_testArbitraryCodec {

    private static final int ARBITRARY_BHSD_CODEC_ENCODING = 116;

    static Stream<Arguments> arbitraryCodec() {
        return Stream.of(
                Arguments.of("(1,256)", new byte[] { 0x00, (byte) 0xFF }),
                Arguments.of("(5,128,2,1)", new byte[] { 0x25, (byte) 0x7F }),
                Arguments.of("(2,128,1,1)", new byte[] { 0x0B, (byte) 0x7F }));
    }

    @ParameterizedTest
    @MethodSource("arbitraryCodec")
    void testArbitraryCodec(final String expected, final byte[] bytes) throws IOException, Pack200Exception {
        assertEquals(expected, CodecEncoding.getCodec(ARBITRARY_BHSD_CODEC_ENCODING, new ByteArrayInputStream(bytes), null).toString());
    }
}
