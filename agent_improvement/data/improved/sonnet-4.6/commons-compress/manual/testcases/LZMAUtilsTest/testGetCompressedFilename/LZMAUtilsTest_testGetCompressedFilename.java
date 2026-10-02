package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LZMAUtilsTest_testGetCompressedFilename {

    /**
     * Test cases: each entry is (input filename, expected compressed filename).
     * The .lzma suffix is always appended directly to the input, including when
     * the input is empty, contains trailing whitespace, or contains a newline.
     */
    static Stream<Arguments> compressedFilenameTestCases() {
        return Stream.of(
            Arguments.of("",         ".lzma"),        // empty input -> bare suffix
            Arguments.of("x",        "x.lzma"),       // simple name
            Arguments.of("x.wmf ",   "x.wmf .lzma"),  // trailing space is preserved
            Arguments.of("x.wmf\n",  "x.wmf\n.lzma"), // embedded newline is preserved
            Arguments.of("x.wmf.y",  "x.wmf.y.lzma")  // multiple extensions
        );
    }

    @SuppressWarnings("deprecation")
    @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
    @MethodSource("compressedFilenameTestCases")
    @DisplayName("getCompressedFilename and getCompressedFileName both append .lzma suffix")
    void getCompressedFilename_appendsLzmaSuffix(String input, String expected) {
        assertAll(
            "deprecated and current API must return the same result",
            () -> assertEquals(expected, LZMAUtils.getCompressedFilename(input),
                    "deprecated getCompressedFilename(\"" + input + "\")"),
            () -> assertEquals(expected, LZMAUtils.getCompressedFileName(input),
                    "current getCompressedFileName(\"" + input + "\")")
        );
    }
}
