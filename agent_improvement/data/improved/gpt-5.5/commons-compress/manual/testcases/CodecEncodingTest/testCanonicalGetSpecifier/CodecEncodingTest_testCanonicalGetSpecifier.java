package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CodecEncodingTest_testCanonicalGetSpecifier {

    private static final int FIRST_CANONICAL_SPECIFIER = 1;
    private static final int LAST_SPECIFIER_EXERCISED_BY_THIS_TEST = 114;

    static Stream<Arguments> canonicalGetSpecifier() {
        return IntStream.rangeClosed(FIRST_CANONICAL_SPECIFIER, LAST_SPECIFIER_EXERCISED_BY_THIS_TEST)
                .mapToObj(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("canonicalGetSpecifier")
    void testCanonicalGetSpecifier(final int specifier) throws Pack200Exception, IOException {
        assertEquals(specifier, CodecEncoding.getSpecifier(CodecEncoding.getCodec(specifier, null, null), null)[0]);
    }
}
