package org.apache.commons.compress.compressors.lzma;

import static org.junit.Assert.assertTrue;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test01 extends LZMAUtils_ESTest_scaffolding {

    /**
     * A signature whose first three bytes match the LZMA header magic
     * ({@code 0x5D, 0x00, 0x00}) should be recognised by {@link LZMAUtils#matches}.
     */
    @Test(timeout = 4000)
    public void matchesReturnsTrueForLzmaHeaderMagic() throws Throwable {
        // First byte is the LZMA magic byte 0x5D (93); remaining bytes stay zero,
        // matching the rest of the header magic.
        byte[] signature = new byte[6];
        signature[0] = (byte) 0x5D;

        boolean matches = LZMAUtils.matches(signature, 3);

        assertTrue(matches);
    }
}
