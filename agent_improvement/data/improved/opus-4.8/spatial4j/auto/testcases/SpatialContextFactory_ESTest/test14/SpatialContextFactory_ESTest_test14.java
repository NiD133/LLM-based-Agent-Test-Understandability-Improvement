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
public class SpatialContextFactory_ESTest_test14 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * When a spatial context is built with only the distance calculator configured
     * ("lawofcosines"), the "normWrapLongitude" option should keep its default of false,
     * since it was never set in the configuration map.
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_doesNotEnableNormWrapLongitudeByDefault() throws Throwable {
        Map<String, String> config = new HashMap<String, String>();
        config.put("distCalculator", "lawofcosines");

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(config, classLoader);

        assertFalse(spatialContext.isNormWrapLongitude());
    }
}
