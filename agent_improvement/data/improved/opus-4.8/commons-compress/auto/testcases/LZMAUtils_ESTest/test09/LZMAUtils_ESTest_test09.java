package org.apache.commons.compress.compressors.lzma;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LZMAUtils_ESTest_test09 extends LZMAUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link LZMAUtils#getCompressedFilename(String)} appends the
     * ".lzma" suffix to a file name that does not already carry an LZMA suffix.
     */
    @Test(timeout = 4000)
    public void getCompressedFilenameAppendsLzmaSuffix() throws Throwable {
        String compressedName = LZMAUtils.getCompressedFilename("CACHED_AVAILABLE");

        assertEquals("CACHED_AVAILABLE.lzma", compressedName);
    }
}
