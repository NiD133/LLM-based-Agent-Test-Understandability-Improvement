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
public class SpatialContextFactory_ESTest_test15 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Builds a SpatialContext configured with the haversine distance calculator and
     * verifies that "normWrapLongitude" stays at its default (false), since it was
     * never set in the configuration map.
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_withHaversineCalculator_leavesNormWrapLongitudeDefaultFalse() throws Throwable {
        Map<String, String> config = new HashMap<String, String>();
        config.put("distCalculator", "haversine");
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(config, systemClassLoader);

        assertFalse(spatialContext.isNormWrapLongitude());
    }
}
