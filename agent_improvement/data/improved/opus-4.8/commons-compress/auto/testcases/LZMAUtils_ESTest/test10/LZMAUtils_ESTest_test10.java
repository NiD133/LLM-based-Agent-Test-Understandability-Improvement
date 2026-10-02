package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test10 extends LZMAUtils_ESTest_scaffolding {

    /**
     * An empty file name has no LZMA suffix, so it should not be detected
     * as a compressed file name.
     */
    @Test(timeout = 4000)
    public void isCompressedFileNameReturnsFalseForEmptyName() throws Throwable {
        boolean isCompressed = LZMAUtils.isCompressedFileName("");

        assertFalse(isCompressed);
    }
}
