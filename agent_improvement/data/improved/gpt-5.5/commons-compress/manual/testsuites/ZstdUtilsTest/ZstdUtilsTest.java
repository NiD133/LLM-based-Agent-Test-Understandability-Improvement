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

class ZstdUtilsTest {

    private static final int FULL_MAGIC_LENGTH = 4;
    private static final int TOO_SHORT_MAGIC_LENGTH = 3;
    private static final int LONGER_THAN_MAGIC_LENGTH = 5;

    @Test
    void testMatchesSkippableFrame() {
        final byte[] skippableFrameSignature = { 0, (byte) 0x2A, (byte) 0x4D, (byte) 0x18, };

        assertFalse(ZstdUtils.matches(skippableFrameSignature, FULL_MAGIC_LENGTH));
        for (byte frameType = (byte) 0x50; frameType < 0x60; frameType++) {
            skippableFrameSignature[0] = frameType;
            assertTrue(ZstdUtils.matches(skippableFrameSignature, FULL_MAGIC_LENGTH));
        }
        assertFalse(ZstdUtils.matches(skippableFrameSignature, TOO_SHORT_MAGIC_LENGTH));
        assertTrue(ZstdUtils.matches(skippableFrameSignature, LONGER_THAN_MAGIC_LENGTH));
    }

    @Test
    void testMatchesZstandardFrame() {
        final byte[] zstandardFrameSignature = { (byte) 0x28, (byte) 0xB5, (byte) 0x2F, (byte) 0xFD, };

        assertFalse(ZstdUtils.matches(zstandardFrameSignature, TOO_SHORT_MAGIC_LENGTH));
        assertTrue(ZstdUtils.matches(zstandardFrameSignature, FULL_MAGIC_LENGTH));
        assertTrue(ZstdUtils.matches(zstandardFrameSignature, LONGER_THAN_MAGIC_LENGTH));
        zstandardFrameSignature[3] = '0';
        assertFalse(ZstdUtils.matches(zstandardFrameSignature, FULL_MAGIC_LENGTH));
    }
}
