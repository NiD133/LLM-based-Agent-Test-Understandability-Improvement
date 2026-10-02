package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test10 extends LZMAUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void emptyFileNameIsNotRecognizedAsCompressed() throws Throwable {
        final String emptyFileName = "";

        final boolean isCompressedFileName = LZMAUtils.isCompressedFileName(emptyFileName);

        assertFalse(isCompressedFileName);
    }
}
