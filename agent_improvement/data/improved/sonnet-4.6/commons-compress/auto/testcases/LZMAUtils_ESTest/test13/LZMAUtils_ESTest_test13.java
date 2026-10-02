package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test13 extends LZMAUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_getUncompressedFileName_returnsInputUnchanged_whenNoLzmaSuffix() throws Throwable {
        // A filename with no LZMA suffix should be returned as-is
        String fileNameWithoutLzmaSuffix = "3JJDsw`@x[";
        String result = LZMAUtils.getUncompressedFileName(fileNameWithoutLzmaSuffix);
        assertEquals(fileNameWithoutLzmaSuffix, result);
    }
}
