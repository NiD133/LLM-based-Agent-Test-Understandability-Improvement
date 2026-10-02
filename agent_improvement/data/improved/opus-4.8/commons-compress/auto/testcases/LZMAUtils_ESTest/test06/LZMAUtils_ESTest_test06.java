package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test06 extends LZMAUtils_ESTest_scaffolding {

    /**
     * A file name without any recognized LZMA suffix (such as ".lzma" or
     * "-lzma") should not be reported as a compressed file name.
     */
    @Test(timeout = 4000)
    public void isCompressedFilenameReturnsFalseForNonLzmaName() throws Throwable {
        String nameWithoutLzmaSuffix = "3JJDsw`@x[";

        boolean recognizedAsCompressed = LZMAUtils.isCompressedFilename(nameWithoutLzmaSuffix);

        assertFalse(recognizedAsCompressed);
    }
}
