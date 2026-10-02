package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test07 extends SpatialContextFactory_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Configure factory with an empty/invalid writers list (comma-only value)
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("writers", ",");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(config, systemClassLoader);

        // Verify default context properties: geographic mode on, longitude normalization off
        assertFalse(spatialContext.isNormWrapLongitude());
        assertTrue(spatialContext.isGeo());
    }
}
