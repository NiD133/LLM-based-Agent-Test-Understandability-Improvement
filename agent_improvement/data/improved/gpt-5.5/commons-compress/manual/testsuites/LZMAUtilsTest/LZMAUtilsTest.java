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

    @SuppressWarnings("deprecation")
    @Test
    void testGetCompressedFilename() {
        assertCompressedFileName(".lzma", "");
        assertCompressedFileName("x.lzma", "x");

        assertCompressedFileName("x.wmf .lzma", "x.wmf ");
        assertCompressedFileName("x.wmf\n.lzma", "x.wmf\n");
        assertCompressedFileName("x.wmf.y.lzma", "x.wmf.y");
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetUncompressedFilename() {
        assertUncompressedFileName("", "");
        assertUncompressedFileName(".lzma", ".lzma");

        assertUncompressedFileName("x", "x.lzma");
        assertUncompressedFileName("x", "x-lzma");

        assertUncompressedFileName("x.lzma ", "x.lzma ");
        assertUncompressedFileName("x.lzma\n", "x.lzma\n");
        assertUncompressedFileName("x.lzma.y", "x.lzma.y");
    }

    @SuppressWarnings("deprecation")
    @Test
    void testIsCompressedFilename() {
        assertCompressedFileNameDetection(false, "");
        assertCompressedFileNameDetection(false, ".lzma");

        assertCompressedFileNameDetection(true, "x.lzma");
        assertCompressedFileNameDetection(true, "x-lzma");

        assertCompressedFileNameDetection(false, "xxgz");
        assertCompressedFileNameDetection(false, "lzmaz");
        assertCompressedFileNameDetection(false, "xaz");

        assertCompressedFileNameDetection(false, "x.lzma ");
        assertCompressedFileNameDetection(false, "x.lzma\n");
        assertCompressedFileNameDetection(false, "x.lzma.y");
    }

    @Test
    void testMatches() {
        final byte[] data = { (byte) 0x5D, 0, 0, };
        assertFalse(LZMAUtils.matches(data, 2));
        assertTrue(LZMAUtils.matches(data, 3));
        assertTrue(LZMAUtils.matches(data, 4));
        data[2] = '0';
        assertFalse(LZMAUtils.matches(data, 3));
    }

    @Test
    void testTurningOnCachingReEvaluatesAvailability() {
        try {
            LZMAUtils.setCacheLZMAAvailablity(false);
            assertEquals(LZMAUtils.CachedAvailability.DONT_CACHE, LZMAUtils.getCachedLZMAAvailability());
            LZMAUtils.setCacheLZMAAvailablity(true);
            assertEquals(LZMAUtils.CachedAvailability.CACHED_AVAILABLE, LZMAUtils.getCachedLZMAAvailability());
        } finally {
            LZMAUtils.setCacheLZMAAvailablity(true);
        }
    }

    @SuppressWarnings("deprecation")
    private static void assertCompressedFileName(final String expected, final String fileName) {
        assertEquals(expected, LZMAUtils.getCompressedFilename(fileName));
        assertEquals(expected, LZMAUtils.getCompressedFileName(fileName));
    }

    @SuppressWarnings("deprecation")
    private static void assertCompressedFileNameDetection(final boolean expected, final String fileName) {
        if (expected) {
            assertTrue(LZMAUtils.isCompressedFilename(fileName));
            assertTrue(LZMAUtils.isCompressedFileName(fileName));
        } else {
            assertFalse(LZMAUtils.isCompressedFilename(fileName));
            assertFalse(LZMAUtils.isCompressedFileName(fileName));
        }
    }

    @SuppressWarnings("deprecation")
    private static void assertUncompressedFileName(final String expected, final String fileName) {
        assertEquals(expected, LZMAUtils.getUncompressedFilename(fileName));
        assertEquals(expected, LZMAUtils.getUncompressedFileName(fileName));
    }

}
