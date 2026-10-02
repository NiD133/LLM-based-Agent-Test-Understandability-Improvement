package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test7 extends ZstdUtils_ESTest_scaffolding {

    /**
     * When caching is disabled, {@link ZstdUtils#isZstdCompressionAvailable()} should fall back to
     * probing the classpath directly. Because the zstd-jni classes are present at test runtime, the
     * availability check is expected to report that Zstandard compression is available.
     */
    @Test(timeout = 4000)
    public void availabilityIsDetectedFromClasspathWhenCachingDisabled() throws Throwable {
        ZstdUtils.setCacheZstdAvailablity(false);

        boolean zstdAvailable = ZstdUtils.isZstdCompressionAvailable();

        assertTrue(zstdAvailable);
    }
}
