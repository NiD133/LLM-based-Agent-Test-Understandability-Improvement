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
 */
class LZMAUtilsTest {

    // --- Helpers: exercise both the deprecated and the current API in one call ---

    /** Asserts the deprecated {@code getCompressedFilename} and current {@code getCompressedFileName} both return {@code expected}. */
    @SuppressWarnings("deprecation")
    private static void assertCompressedFileNameEquals(final String expected, final String input) {
        assertEquals(expected, LZMAUtils.getCompressedFilename(input));
        assertEquals(expected, LZMAUtils.getCompressedFileName(input));
    }

    /** Asserts the deprecated {@code getUncompressedFilename} and current {@code getUncompressedFileName} both return {@code expected}. */
    @SuppressWarnings("deprecation")
    private static void assertUncompressedFileNameEquals(final String expected, final String input) {
        assertEquals(expected, LZMAUtils.getUncompressedFilename(input));
        assertEquals(expected, LZMAUtils.getUncompressedFileName(input));
    }

    /** Asserts the deprecated {@code isCompressedFilename} and current {@code isCompressedFileName} both return {@code true}. */
    @SuppressWarnings("deprecation")
    private static void assertIsCompressed(final String fileName) {
        assertTrue(LZMAUtils.isCompressedFilename(fileName));
        assertTrue(LZMAUtils.isCompressedFileName(fileName));
    }

    /** Asserts the deprecated {@code isCompressedFilename} and current {@code isCompressedFileName} both return {@code false}. */
    @SuppressWarnings("deprecation")
    private static void assertNotCompressed(final String fileName) {
        assertFalse(LZMAUtils.isCompressedFilename(fileName));
        assertFalse(LZMAUtils.isCompressedFileName(fileName));
    }

    // --- Tests ---

    @Test
    void testCachingIsEnabledByDefaultAndLZMAIsPresent() {
        assertEquals(LZMAUtils.CachedAvailability.CACHED_AVAILABLE, LZMAUtils.getCachedLZMAAvailability());
        assertTrue(LZMAUtils.isLZMACompressionAvailable());
    }

    @Test
    void testCanTurnOffCaching() {
        try {
            LZMAUtils.setCacheLZMAAvailablity(false);
            assertEquals(LZMAUtils.CachedAvailability.DONT_CACHE, LZMAUtils.getCachedLZMAAvailability());
            assertTrue(LZMAUtils.isLZMACompressionAvailable());
        } finally {
            LZMAUtils.setCacheLZMAAvailablity(true);
        }
    }

    @Test
    void testGetCompressedFilename() {
        // Empty and plain names simply get ".lzma" appended
        assertCompressedFileNameEquals(".lzma", "");
        assertCompressedFileNameEquals("x.lzma", "x");

        // Trailing whitespace/newline or an extra extension is preserved — ".lzma" is still appended
        assertCompressedFileNameEquals("x.wmf .lzma", "x.wmf ");
        assertCompressedFileNameEquals("x.wmf\n.lzma", "x.wmf\n");
        assertCompressedFileNameEquals("x.wmf.y.lzma", "x.wmf.y");
    }

    @Test
    void testGetUncompressedFilename() {
        // Names without a recognised LZMA suffix are returned unchanged
        assertUncompressedFileNameEquals("", "");
        assertUncompressedFileNameEquals(".lzma", ".lzma"); // bare suffix alone is not a valid compressed name

        // Both LZMA suffixes (".lzma" and "-lzma") are stripped correctly
        assertUncompressedFileNameEquals("x", "x.lzma");
        assertUncompressedFileNameEquals("x", "x-lzma");

        // Suffix must be at the very end with no extra characters — these are returned as-is
        assertUncompressedFileNameEquals("x.lzma ", "x.lzma ");
        assertUncompressedFileNameEquals("x.lzma\n", "x.lzma\n");
        assertUncompressedFileNameEquals("x.lzma.y", "x.lzma.y");
    }

    @Test
    void testIsCompressedFilename() {
        // Empty or bare-suffix names are not considered compressed
        assertNotCompressed("");
        assertNotCompressed(".lzma");

        // Both recognised LZMA suffixes (".lzma" and "-lzma") are detected
        assertIsCompressed("x.lzma");
        assertIsCompressed("x-lzma");

        // Names whose suffix does not match any LZMA pattern are rejected
        assertNotCompressed("xxgz");
        assertNotCompressed("lzmaz");
        assertNotCompressed("xaz");

        // Suffix match is strict — trailing whitespace/newline or a following extension disqualifies
        assertNotCompressed("x.lzma ");
        assertNotCompressed("x.lzma\n");
        assertNotCompressed("x.lzma.y");
    }

    @Test
    void testMatches() {
        // LZMA magic header: { 0x5D, 0x00, 0x00 } — requires exactly 3 bytes to identify
        final byte[] lzmaMagic = { (byte) 0x5D, 0, 0 };

        // Fewer bytes than the magic header length — cannot match
        assertFalse(LZMAUtils.matches(lzmaMagic, 2));

        // Exact magic length and any length beyond it both match
        assertTrue(LZMAUtils.matches(lzmaMagic, 3));
        assertTrue(LZMAUtils.matches(lzmaMagic, 4));

        // Changing a magic byte breaks the match
        lzmaMagic[2] = '0';
        assertFalse(LZMAUtils.matches(lzmaMagic, 3));
    }

    @Test
    void testTurningOnCachingReEvaluatesAvailability() {
        try {
            LZMAUtils.setCacheLZMAAvailablity(false);
            assertEquals(LZMAUtils.CachedAvailability.DONT_CACHE, LZMAUtils.getCachedLZMAAvailability());

            // Re-enabling caching triggers an immediate availability check and caches the result
            LZMAUtils.setCacheLZMAAvailablity(true);
            assertEquals(LZMAUtils.CachedAvailability.CACHED_AVAILABLE, LZMAUtils.getCachedLZMAAvailability());
        } finally {
            LZMAUtils.setCacheLZMAAvailablity(true);
        }
    }

}
