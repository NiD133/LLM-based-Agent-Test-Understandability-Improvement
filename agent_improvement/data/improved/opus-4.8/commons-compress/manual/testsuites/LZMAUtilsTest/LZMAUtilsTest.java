/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link LZMAUtils}.
 *
 * <p>
 * Every file-name helper in {@link LZMAUtils} comes in two flavours: a deprecated
 * {@code ...Filename} method and its replacement {@code ...FileName} method. Both are expected
 * to behave identically, so the helper methods in this test always exercise the pair together and
 * assert they agree on the same result.
 * </p>
 */
class LZMAUtilsTest {

    /**
     * Asserts that both the deprecated and the current "compress this name" helpers map
     * {@code originalName} to {@code expectedCompressedName}.
     */
    @SuppressWarnings("deprecation")
    private static void assertCompressedNameIs(final String expectedCompressedName, final String originalName) {
        assertEquals(expectedCompressedName, LZMAUtils.getCompressedFilename(originalName));
        assertEquals(expectedCompressedName, LZMAUtils.getCompressedFileName(originalName));
    }

    /**
     * Asserts that both the deprecated and the current "is this a compressed name" helpers
     * classify {@code fileName} as {@code expectedCompressed}.
     */
    @SuppressWarnings("deprecation")
    private static void assertIsCompressedName(final boolean expectedCompressed, final String fileName) {
        assertEquals(expectedCompressed, LZMAUtils.isCompressedFilename(fileName));
        assertEquals(expectedCompressed, LZMAUtils.isCompressedFileName(fileName));
    }

    /**
     * Asserts that both the deprecated and the current "uncompress this name" helpers map
     * {@code compressedName} to {@code expectedUncompressedName}.
     */
    @SuppressWarnings("deprecation")
    private static void assertUncompressedNameIs(final String expectedUncompressedName, final String compressedName) {
        assertEquals(expectedUncompressedName, LZMAUtils.getUncompressedFilename(compressedName));
        assertEquals(expectedUncompressedName, LZMAUtils.getUncompressedFileName(compressedName));
    }

    @Test
    void testCachingIsEnabledByDefaultAndLZMAIsPresent() {
        assertEquals(LZMAUtils.CachedAvailability.CACHED_AVAILABLE, LZMAUtils.getCachedLZMAAvailability());
        assertTrue(LZMAUtils.isLZMACompressionAvailable());
    }

    @Test
    void testCanTurnOffCaching() {
        try {
            LZMAUtils.setCacheLZMAAvailablity(false);

            // With caching disabled the cache reports DONT_CACHE, but availability is still computed live.
            assertEquals(LZMAUtils.CachedAvailability.DONT_CACHE, LZMAUtils.getCachedLZMAAvailability());
            assertTrue(LZMAUtils.isLZMACompressionAvailable());
        } finally {
            // Restore the default so later tests see a populated cache.
            LZMAUtils.setCacheLZMAAvailablity(true);
        }
    }

    @Test
    void testGetCompressedFilename() {
        // The ".lzma" suffix is appended verbatim, regardless of any existing extension or whitespace.
        assertCompressedNameIs(".lzma", "");
        assertCompressedNameIs("x.lzma", "x");
        assertCompressedNameIs("x.wmf .lzma", "x.wmf ");
        assertCompressedNameIs("x.wmf\n.lzma", "x.wmf\n");
        assertCompressedNameIs("x.wmf.y.lzma", "x.wmf.y");
    }

    @Test
    void testGetUncompressedFilename() {
        // A bare suffix is left untouched...
        assertUncompressedNameIs("", "");
        assertUncompressedNameIs(".lzma", ".lzma");

        // ...a trailing ".lzma" or "-lzma" suffix is stripped...
        assertUncompressedNameIs("x", "x.lzma");
        assertUncompressedNameIs("x", "x-lzma");

        // ...but trailing whitespace or a suffix that is not at the very end is not a match.
        assertUncompressedNameIs("x.lzma ", "x.lzma ");
        assertUncompressedNameIs("x.lzma\n", "x.lzma\n");
        assertUncompressedNameIs("x.lzma.y", "x.lzma.y");
    }

    @Test
    void testIsCompressedFilename() {
        // A suffix on its own is not considered a compressed file name.
        assertIsCompressedName(false, "");
        assertIsCompressedName(false, ".lzma");

        // A name ending in ".lzma" or "-lzma" is recognized.
        assertIsCompressedName(true, "x.lzma");
        assertIsCompressedName(true, "x-lzma");

        // Names that merely contain "lzma" without the proper suffix are not recognized.
        assertIsCompressedName(false, "xxgz");
        assertIsCompressedName(false, "lzmaz");
        assertIsCompressedName(false, "xaz");

        // Trailing whitespace, or a suffix that is not at the very end, breaks the match.
        assertIsCompressedName(false, "x.lzma ");
        assertIsCompressedName(false, "x.lzma\n");
        assertIsCompressedName(false, "x.lzma.y");
    }

    @Test
    void testMatches() {
        final byte[] data = { (byte) 0x5D, 0, 0, };

        // Fewer bytes than the 3-byte magic header cannot match.
        assertFalse(LZMAUtils.matches(data, 2));

        // Lengths at or beyond the magic header length match when the bytes are correct.
        assertTrue(LZMAUtils.matches(data, 3));
        assertTrue(LZMAUtils.matches(data, 4));

        // Corrupting a magic byte breaks the match.
        data[2] = '0';
        assertFalse(LZMAUtils.matches(data, 3));
    }

    @Test
    void testTurningOnCachingReEvaluatesAvailability() {
        try {
            // Disabling caching clears any cached verdict...
            LZMAUtils.setCacheLZMAAvailablity(false);
            assertEquals(LZMAUtils.CachedAvailability.DONT_CACHE, LZMAUtils.getCachedLZMAAvailability());

            // ...and re-enabling it re-evaluates and caches that LZMA is available.
            LZMAUtils.setCacheLZMAAvailablity(true);
            assertEquals(LZMAUtils.CachedAvailability.CACHED_AVAILABLE, LZMAUtils.getCachedLZMAAvailability());
        } finally {
            LZMAUtils.setCacheLZMAAvailablity(true);
        }
    }

}
