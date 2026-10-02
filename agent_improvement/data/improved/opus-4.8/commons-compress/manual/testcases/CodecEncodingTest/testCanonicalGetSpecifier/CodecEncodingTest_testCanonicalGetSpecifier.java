package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies the round-trip between {@link CodecEncoding#getCodec} and
 * {@link CodecEncoding#getSpecifier} for the canonical codec encodings.
 */
public class CodecEncodingTest_testCanonicalGetSpecifier {

    /**
     * Supplies every canonical encoding index. The Pack200 specification defines
     * canonical encodings for indices 1..115; this exercises 1..114.
     */
    static Stream<Arguments> canonicalEncodingIndexes() {
        return IntStream.range(1, 115).mapToObj(Arguments::of);
    }

    /**
     * Looking up the codec for a canonical index and then asking for that codec's
     * specifier must return the same index it started from.
     */
    @ParameterizedTest
    @MethodSource("canonicalEncodingIndexes")
    void testCanonicalGetSpecifier(final int canonicalIndex) throws Pack200Exception, IOException {
        final Codec canonicalCodec = CodecEncoding.getCodec(canonicalIndex, null, null);
        final int[] specifier = CodecEncoding.getSpecifier(canonicalCodec, null);

        assertEquals(canonicalIndex, specifier[0]);
    }
}
