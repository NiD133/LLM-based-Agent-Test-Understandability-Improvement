package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test6 extends ZstdUtils_ESTest_scaffolding {

    /**
     * Verifies that the Zstandard compression library is available on the classpath,
     * meaning {@code ZstdUtils.isZstdCompressionAvailable()} returns {@code true}.
     */
    @Test(timeout = 4000)
    public void test_isZstdCompressionAvailable_returnsTrueWhenLibraryIsPresent() throws Throwable {
        boolean zstdAvailable = ZstdUtils.isZstdCompressionAvailable();
        assertTrue("Expected Zstd compression to be available, but it was not", zstdAvailable);
    }
}
