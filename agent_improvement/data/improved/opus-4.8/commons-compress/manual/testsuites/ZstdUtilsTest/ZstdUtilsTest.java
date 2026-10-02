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

import org.junit.jupiter.api.Test;

/**
 * Tests {@link ZstdUtils#matches(byte[], int)}, the signature detector that
 * recognises both the Zstandard frame and the "skippable" frame magic bytes.
 *
 * <p>
 * A Zstandard frame starts with the four magic bytes {@code 28 B5 2F FD}. A
 * skippable frame starts with a marker byte in the range {@code 0x50..0x5F}
 * followed by the three shared magic bytes {@code 2A 4D 18}. In both cases at
 * least four bytes must be available for {@code matches} to inspect.
 * </p>
 */
class ZstdUtilsTest {

    /** The fixed number of magic bytes a (Zstandard or skippable) frame begins with. */
    private static final int MAGIC_LENGTH = 4;

    /** The three magic bytes shared by every skippable frame, following the marker byte. */
    private static final byte SKIPPABLE_MAGIC_1 = (byte) 0x2A;
    private static final byte SKIPPABLE_MAGIC_2 = (byte) 0x4D;
    private static final byte SKIPPABLE_MAGIC_3 = (byte) 0x18;

    /** The four magic bytes that identify a Zstandard frame. */
    private static final byte ZSTANDARD_MAGIC_1 = (byte) 0x28;
    private static final byte ZSTANDARD_MAGIC_2 = (byte) 0xB5;
    private static final byte ZSTANDARD_MAGIC_3 = (byte) 0x2F;
    private static final byte ZSTANDARD_MAGIC_4 = (byte) 0xFD;

    /** Inclusive lower bound of the skippable-frame marker byte range. */
    private static final byte SKIPPABLE_MARKER_FIRST = (byte) 0x50;
    /** Exclusive upper bound of the skippable-frame marker byte range. */
    private static final byte SKIPPABLE_MARKER_END = (byte) 0x60;

    @Test
    void testMatchesSkippableFrame() {
        // Marker byte 0x00 is outside the skippable range, so this is not a frame.
        final byte[] data = { 0x00, SKIPPABLE_MAGIC_1, SKIPPABLE_MAGIC_2, SKIPPABLE_MAGIC_3 };
        assertFalse(ZstdUtils.matches(data, MAGIC_LENGTH),
                "Marker byte 0x00 is outside the skippable range");

        // Every marker byte in 0x50..0x5F turns the buffer into a valid skippable frame.
        for (byte marker = SKIPPABLE_MARKER_FIRST; marker < SKIPPABLE_MARKER_END; marker++) {
            data[0] = marker;
            assertTrue(ZstdUtils.matches(data, MAGIC_LENGTH),
                    "Marker byte in 0x50..0x5F should be recognised as a skippable frame");
        }

        // Fewer than four bytes is never a match, even with a valid marker.
        assertFalse(ZstdUtils.matches(data, MAGIC_LENGTH - 1),
                "Fewer than four bytes can never match");
        // Extra available bytes beyond the magic do not affect the match.
        assertTrue(ZstdUtils.matches(data, MAGIC_LENGTH + 1),
                "Trailing bytes after the magic should still match");
    }

    @Test
    void testMatchesZstandardFrame() {
        final byte[] data = { ZSTANDARD_MAGIC_1, ZSTANDARD_MAGIC_2, ZSTANDARD_MAGIC_3, ZSTANDARD_MAGIC_4 };

        // Fewer than four bytes is too short to hold the Zstandard magic.
        assertFalse(ZstdUtils.matches(data, MAGIC_LENGTH - 1),
                "Fewer than four bytes can never match");
        // Exactly the magic bytes match, as do buffers with extra trailing bytes.
        assertTrue(ZstdUtils.matches(data, MAGIC_LENGTH),
                "The four Zstandard magic bytes should match");
        assertTrue(ZstdUtils.matches(data, MAGIC_LENGTH + 1),
                "Trailing bytes after the magic should still match");

        // Corrupting the last magic byte breaks the match.
        data[3] = '0';
        assertFalse(ZstdUtils.matches(data, MAGIC_LENGTH),
                "A wrong final magic byte must not match");
    }
}
