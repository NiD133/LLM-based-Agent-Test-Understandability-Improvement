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

package org.apache.commons.compress.compressors.zstandard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ZstdUtilsTest {

    // Zstandard frame magic: 4 bytes representing 0xFD2FB528 in little-endian order (per the Zstandard spec)
    private static final byte[] ZSTANDARD_FRAME_MAGIC = { (byte) 0x28, (byte) 0xB5, (byte) 0x2F, (byte) 0xFD };

    @Test
    @DisplayName("Skippable frame: first byte must have upper nibble 0x5 (range 0x50–0x5F), "
            + "followed by magic bytes 0x2A 0x4D 0x18; requires at least 4 bytes")
    void testMatchesSkippableFrame() {
        // Build a skippable-frame candidate: first byte 0x00 (invalid), then the 3 shared magic bytes
        final byte[] data = { 0, (byte) 0x2A, (byte) 0x4D, (byte) 0x18, };

        // First byte 0x00 has upper nibble 0x0 — not a valid skippable-frame prefix
        assertFalse(ZstdUtils.matches(data, 4));

        // Every byte in 0x50–0x5F has upper nibble 0x5 and is a valid skippable-frame prefix
        for (byte b = (byte) 0x50; b < 0x60; b++) {
            data[0] = b;
            assertTrue(ZstdUtils.matches(data, 4));
        }

        // 3 bytes is not enough to confirm the magic, even with a valid first byte (data[0] is 0x5F from the loop)
        assertFalse(ZstdUtils.matches(data, 3));

        // Extra bytes beyond the 4-byte magic are accepted
        assertTrue(ZstdUtils.matches(data, 5));
    }

    @Test
    @DisplayName("Zstandard frame: must begin with magic bytes 0x28 0xB5 0x2F 0xFD; requires at least 4 bytes")
    void testMatchesZstandardFrame() {
        final byte[] data = { (byte) 0x28, (byte) 0xB5, (byte) 0x2F, (byte) 0xFD, };

        // 3 bytes is not enough to identify the 4-byte Zstandard magic
        assertFalse(ZstdUtils.matches(data, 3));

        // All 4 magic bytes present — matches
        assertTrue(ZstdUtils.matches(data, 4));

        // Extra bytes beyond the 4-byte magic are accepted
        assertTrue(ZstdUtils.matches(data, 5));

        // Corrupt the 4th magic byte: 0xFD → ASCII '0' (0x30)
        data[3] = '0';
        assertFalse(ZstdUtils.matches(data, 4));
    }
}
