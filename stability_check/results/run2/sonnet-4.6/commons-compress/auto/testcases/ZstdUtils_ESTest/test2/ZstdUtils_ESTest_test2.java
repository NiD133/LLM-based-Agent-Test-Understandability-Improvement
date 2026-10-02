package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test2 extends ZstdUtils_ESTest_scaffolding {

    // Skippable frame magic bytes are: first byte in range 0x50-0x5F, then 0x2A, 0x4D, 0x18
    // 0x53 has upper nibble 0x50, qualifying the first byte as a potential skippable frame header.
    // The second byte 0x2A matches the first skippable magic byte, but bytes 2-3 remain zero,
    // so the full signature does not match and matches() must return false.
    @Test(timeout = 4000)
    public void test_matchesReturnsFalse_whenSignatureIsPartialSkippableFrameHeader() throws Throwable {
        byte[] signature = new byte[8];
        signature[0] = (byte) 0x53; // upper nibble 0x50 → triggers skippable frame check
        signature[1] = (byte) 0x2A; // matches first skippable frame magic byte, but rest are zero
        int length = 42;             // well above the 4-byte minimum required by matches()

        boolean result = ZstdUtils.matches(signature, length);

        assertFalse(result);
    }
}
