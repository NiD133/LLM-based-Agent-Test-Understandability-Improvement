package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CodecEncodingTest_testCanonicalEncodings {

    private static final String[] EXPECTED_CANONICAL_CODECS = {
        null,
        "(1,256)",
        "(1,256,1)",
        "(1,256,0,1)",
        "(1,256,1,1)",
        "(2,256)",
        "(2,256,1)",
        "(2,256,0,1)",
        "(2,256,1,1)",
        "(3,256)",
        "(3,256,1)",
        "(3,256,0,1)",
        "(3,256,1,1)",
        "(4,256)",
        "(4,256,1)",
        "(4,256,0,1)",
        "(4,256,1,1)",
        "(5,4)",
        "(5,4,1)",
        "(5,4,2)",
        "(5,16)",
        "(5,16,1)",
        "(5,16,2)",
        "(5,32)",
        "(5,32,1)",
        "(5,32,2)",
        "(5,64)",
        "(5,64,1)",
        "(5,64,2)",
        "(5,128)",
        "(5,128,1)",
        "(5,128,2)",
        "(5,4,0,1)",
        "(5,4,1,1)",
        "(5,4,2,1)",
        "(5,16,0,1)",
        "(5,16,1,1)",
        "(5,16,2,1)",
        "(5,32,0,1)",
        "(5,32,1,1)",
        "(5,32,2,1)",
        "(5,64,0,1)",
        "(5,64,1,1)",
        "(5,64,2,1)",
        "(5,128,0,1)",
        "(5,128,1,1)",
        "(5,128,2,1)",
        "(2,192)",
        "(2,224)",
        "(2,240)",
        "(2,248)",
        "(2,252)",
        "(2,8,0,1)",
        "(2,8,1,1)",
        "(2,16,0,1)",
        "(2,16,1,1)",
        "(2,32,0,1)",
        "(2,32,1,1)",
        "(2,64,0,1)",
        "(2,64,1,1)",
        "(2,128,0,1)",
        "(2,128,1,1)",
        "(2,192,0,1)",
        "(2,192,1,1)",
        "(2,224,0,1)",
        "(2,224,1,1)",
        "(2,240,0,1)",
        "(2,240,1,1)",
        "(2,248,0,1)",
        "(2,248,1,1)",
        "(3,192)",
        "(3,224)",
        "(3,240)",
        "(3,248)",
        "(3,252)",
        "(3,8,0,1)",
        "(3,8,1,1)",
        "(3,16,0,1)",
        "(3,16,1,1)",
        "(3,32,0,1)",
        "(3,32,1,1)",
        "(3,64,0,1)",
        "(3,64,1,1)",
        "(3,128,0,1)",
        "(3,128,1,1)",
        "(3,192,0,1)",
        "(3,192,1,1)",
        "(3,224,0,1)",
        "(3,224,1,1)",
        "(3,240,0,1)",
        "(3,240,1,1)",
        "(3,248,0,1)",
        "(3,248,1,1)",
        "(4,192)",
        "(4,224)",
        "(4,240)",
        "(4,248)",
        "(4,252)",
        "(4,8,0,1)",
        "(4,8,1,1)",
        "(4,16,0,1)",
        "(4,16,1,1)",
        "(4,32,0,1)",
        "(4,32,1,1)",
        "(4,64,0,1)",
        "(4,64,1,1)",
        "(4,128,0,1)",
        "(4,128,1,1)",
        "(4,192,0,1)",
        "(4,192,1,1)",
        "(4,224,0,1)",
        "(4,224,1,1)",
        "(4,240,0,1)",
        "(4,240,1,1)",
        "(4,248,0,1)",
        "(4,248,1,1)"
    };

    static Stream<Arguments> canonicalEncodings() {
        return IntStream.range(1, EXPECTED_CANONICAL_CODECS.length)
                .mapToObj(index -> Arguments.of(index, EXPECTED_CANONICAL_CODECS[index]));
    }

    @ParameterizedTest
    @MethodSource("canonicalEncodings")
    void testCanonicalEncodings(final int index, final String expectedCodec) throws IOException, Pack200Exception {
        assertEquals(expectedCodec, CodecEncoding.getCodec(index, null, null).toString());
    }
}
