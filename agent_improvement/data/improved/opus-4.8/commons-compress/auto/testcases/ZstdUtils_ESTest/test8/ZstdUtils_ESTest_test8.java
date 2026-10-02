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

    /**
     * Verifies that the package-private {@link ZstdUtils#getCachedZstdAvailability()}
     * accessor can be read after the class is initialized. The static initializer of
     * {@code ZstdUtils} always assigns the cached availability field a non-null
     * {@code CachedAvailability} value, so the accessor must never return {@code null}.
     */
    @Test(timeout = 4000)
    public void getCachedZstdAvailabilityReturnsInitializedValue() throws Throwable {
        ZstdUtils.CachedAvailability cachedAvailability = ZstdUtils.getCachedZstdAvailability();

        assertNotNull(cachedAvailability);
    }
}
