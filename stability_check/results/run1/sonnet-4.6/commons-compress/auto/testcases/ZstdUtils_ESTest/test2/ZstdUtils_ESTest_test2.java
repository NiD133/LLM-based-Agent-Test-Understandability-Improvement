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

    /**
     * Verifies that a signature whose first byte (0x53) has the skippable-frame high nibble (0x5_)
     * but whose remaining bytes do not match the skippable frame magic is rejected.
     *
     * Zstandard skippable frame magic requires bytes [1..3] to be { 0x2A, 0x4D, 0x18 }.
     * Here byte[1] = 0x2A matches but byte[2] = 0x00 does not match 0x4D, so matches() must return false.
     */
    @Test(timeout = 4000)
    public void test_matchesReturnsFalse_whenSkippableFrameMagicIsIncomplete() throws Throwable {
        // Build an 8-byte signature whose first byte (83 = 0x53) has the skippable-frame
        // high nibble but whose subsequent bytes do not satisfy the full skippable magic.
        byte[] incompleteSkippableSignature = new byte[8];
        incompleteSkippableSignature[0] = (byte) 83;  // 0x53 — high nibble 0x5 triggers skippable-frame path
        incompleteSkippableSignature[1] = (byte) 42;  // 0x2A — matches SKIPPABLE_FRAME_MAGIC[0], but bytes[2..] are 0x00

        // Length 42 is well above the minimum 4 bytes required, so the length guard is not the reason for rejection.
        boolean matchesZstdFormat = ZstdUtils.matches(incompleteSkippableSignature, (byte) 42);

        assertFalse(matchesZstdFormat);
    }
}
