package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CodecEncodingTest_testCanonicalGetSpecifier {

    // Pack200 defines 115 canonical codecs at indices 1–114; index 0 is reserved.
    private static final int CANONICAL_CODEC_COUNT = 115;

    /**
     * Supplies each valid canonical-codec index (1 through 114) as a test argument.
     */
    static Stream<Arguments> canonicalGetSpecifier() {
        return IntStream.range(1, CANONICAL_CODEC_COUNT).mapToObj(Arguments::of);
    }

    /**
     * Verifies that the round-trip {@code getSpecifier(getCodec(i)) == i} holds for every
     * canonical codec index defined by the Pack200 specification.
     *
     * <p>A canonical codec is identified by a single-byte specifier (1–114). Decoding that
     * specifier via {@link CodecEncoding#getCodec} and then re-encoding the resulting codec
     * via {@link CodecEncoding#getSpecifier} must reproduce the original index.</p>
     */
    @ParameterizedTest(name = "canonical codec index {0}")
    @DisplayName("getSpecifier(getCodec(i)) == i for all canonical codec indices")
    @MethodSource("canonicalGetSpecifier")
    void testCanonicalGetSpecifier(final int i) throws Pack200Exception, IOException {
        assertEquals(i, CodecEncoding.getSpecifier(CodecEncoding.getCodec(i, null, null), null)[0]);
    }
}
