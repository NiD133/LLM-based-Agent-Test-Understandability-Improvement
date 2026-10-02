package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test04 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * When no configuration keys are supplied, the factory should fall back to its
     * default {@link SpatialContext} settings: geodesic enabled and longitude
     * norm-wrapping disabled.
     */
    @Test(timeout = 4000)
    public void makeSpatialContextWithEmptyArgsUsesDefaults() throws Throwable {
        Map<String, String> emptyConfig = new HashMap<String, String>();
        ClassLoader parentClassLoader = ClassLoader.getSystemClassLoader().getParent();

        SpatialContext context = SpatialContextFactory.makeSpatialContext(emptyConfig, parentClassLoader);

        assertFalse("normWrapLongitude should default to false", context.isNormWrapLongitude());
        assertTrue("geo should default to true", context.isGeo());
    }
}
