package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that LZMA file names are produced by appending the ".lzma" suffix to
 * the original name.
 *
 * <p>The class offers two equivalent entry points: the deprecated
 * {@code getCompressedFilename} and its replacement {@code getCompressedFileName}.
 * Both must yield the same result, so every case below is checked against both.</p>
 */
public class LZMAUtilsTest_testGetCompressedFilename {

    /**
     * Asserts that both the deprecated and the current API map {@code originalName}
     * to {@code expectedCompressedName}.
     */
    @SuppressWarnings("deprecation")
    private static void assertCompressedNameForBothApis(final String expectedCompressedName,
            final String originalName) {
        assertEquals(expectedCompressedName, LZMAUtils.getCompressedFilename(originalName));
        assertEquals(expectedCompressedName, LZMAUtils.getCompressedFileName(originalName));
    }

    @Test
    void testGetCompressedFilename() {
        // An empty name produces just the suffix.
        assertCompressedNameForBothApis(".lzma", "");

        // A plain name gets the suffix appended.
        assertCompressedNameForBothApis("x.lzma", "x");

        // Trailing whitespace in the name is preserved before the suffix.
        assertCompressedNameForBothApis("x.wmf .lzma", "x.wmf ");

        // A trailing newline in the name is preserved before the suffix.
        assertCompressedNameForBothApis("x.wmf\n.lzma", "x.wmf\n");

        // Existing dots in the name are left untouched; the suffix is simply appended.
        assertCompressedNameForBothApis("x.wmf.y.lzma", "x.wmf.y");
    }
}
