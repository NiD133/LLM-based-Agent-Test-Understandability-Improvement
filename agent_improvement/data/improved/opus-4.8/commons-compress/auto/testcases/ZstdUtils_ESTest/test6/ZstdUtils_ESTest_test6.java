package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test6 extends ZstdUtils_ESTest_scaffolding {

    /**
     * The Zstandard JNI classes are on the test classpath, so the availability
     * check should report that Zstandard compression is available.
     */
    @Test(timeout = 4000)
    public void isZstdCompressionAvailable_returnsTrue_whenZstdClassesOnClasspath() throws Throwable {
        boolean zstdAvailable = ZstdUtils.isZstdCompressionAvailable();

        assertTrue("Zstandard compression should be reported as available", zstdAvailable);
    }
}
