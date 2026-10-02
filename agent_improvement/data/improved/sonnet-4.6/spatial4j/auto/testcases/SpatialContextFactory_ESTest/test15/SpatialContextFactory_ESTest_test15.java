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
public class SpatialContextFactory_ESTest_test15 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that a SpatialContext created with the haversine distance calculator
     * has longitude-wrapping normalization disabled by default.
     */
    @Test(timeout = 4000)
    public void test_haversineDistCalc_normWrapLongitudeDefaultsFalse() throws Throwable {
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("distCalculator", "haversine");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(config, systemClassLoader);

        assertFalse(spatialContext.isNormWrapLongitude());
    }
}
