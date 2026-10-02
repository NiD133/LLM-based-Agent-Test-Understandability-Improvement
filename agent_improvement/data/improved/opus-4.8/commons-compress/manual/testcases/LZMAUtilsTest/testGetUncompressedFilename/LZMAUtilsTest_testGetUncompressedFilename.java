package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Verifies how {@link LZMAUtils} maps a (possibly LZMA-compressed) file name back to its
 * uncompressed name.
 *
 * <p>Each case is checked against both the deprecated {@code getUncompressedFilename} and the
 * current {@code getUncompressedFileName}; the two methods are expected to behave identically.</p>
 */
public class LZMAUtilsTest_testGetUncompressedFilename {

    /**
     * Provides {@code (inputFileName, expectedUncompressedName)} pairs.
     *
     * <p>The ".lzma" and "-lzma" suffixes are stripped; everything else (including trailing
     * whitespace, newlines, or a suffix that is not at the end) is returned unchanged.</p>
     */
    static Stream<Arguments> fileNameCases() {
        return Stream.of(
                Arguments.of("", ""),                       // empty stays empty
                Arguments.of(".lzma", ".lzma"),             // bare suffix, no base name -> unchanged
                Arguments.of("x.lzma", "x"),                // ".lzma" suffix stripped
                Arguments.of("x-lzma", "x"),                // "-lzma" suffix stripped
                Arguments.of("x.lzma ", "x.lzma "),         // trailing space -> not a suffix
                Arguments.of("x.lzma\n", "x.lzma\n"),       // trailing newline -> not a suffix
                Arguments.of("x.lzma.y", "x.lzma.y"));      // suffix not at the end -> unchanged
    }

    @SuppressWarnings("deprecation")
    @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
    @MethodSource("fileNameCases")
    void mapsToUncompressedFileName(final String input, final String expected) {
        assertEquals(expected, LZMAUtils.getUncompressedFilename(input));
        assertEquals(expected, LZMAUtils.getUncompressedFileName(input));
    }
}
