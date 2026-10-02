package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test13 extends LZMAUtils_ESTest_scaffolding {

    /**
     * A file name that carries no recognised LZMA suffix should be returned
     * unchanged by {@link LZMAUtils#getUncompressedFileName(String)}.
     */
    @Test(timeout = 4000)
    public void getUncompressedFileNameReturnsNameUnchangedWhenNoLzmaSuffix() throws Throwable {
        String fileNameWithoutLzmaSuffix = "3JJDsw`@x[";

        String uncompressedFileName =
                LZMAUtils.getUncompressedFileName(fileNameWithoutLzmaSuffix);

        assertEquals(fileNameWithoutLzmaSuffix, uncompressedFileName);
    }
}
