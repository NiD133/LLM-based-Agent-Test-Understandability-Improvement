package org.apache.commons.compress.compressors.zstandard;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ZstdUtils_ESTest_test8 extends ZstdUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test8() throws Throwable {
        // The actual cached value depends on the runtime classpath; this test only
        // exercises the package-private accessor, matching the original behavior.
        final ZstdUtils.CachedAvailability cachedAvailability = ZstdUtils.getCachedZstdAvailability();
    }
}
