package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.impl.ShapeFactoryImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test12 extends SpatialContextFactory_ESTest_scaffolding {

    // Setting distCalculator=cartesian does not override the default geo=true or normWrapLongitude=false flags.
    @Test(timeout = 4000)
    public void test_cartesianDistCalculatorPreservesDefaultGeoAndNoNormWrapLongitude() throws Throwable {
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("distCalculator", "cartesian");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(config, systemClassLoader);

        assertTrue(spatialContext.isGeo());
        assertFalse(spatialContext.isNormWrapLongitude());
    }
}
