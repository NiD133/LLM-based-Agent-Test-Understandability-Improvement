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
public class SpatialContextFactory_ESTest_test16 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that configuring a non-default distance calculator
     * ("cartesian^2") does not change the geo flag: a SpatialContext built
     * without an explicit "geo" entry stays geodetic (the factory default).
     */
    @Test(timeout = 4000)
    public void buildingContextWithCartesianSquaredCalculatorKeepsGeoDefault() throws Throwable {
        Map<String, String> config = new HashMap<String, String>();
        config.put("distCalculator", "cartesian^2");

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        SpatialContext context = SpatialContextFactory.makeSpatialContext(config, classLoader);

        assertTrue("SpatialContext should remain geodetic when only the distance calculator is set",
                context.isGeo());
    }
}
