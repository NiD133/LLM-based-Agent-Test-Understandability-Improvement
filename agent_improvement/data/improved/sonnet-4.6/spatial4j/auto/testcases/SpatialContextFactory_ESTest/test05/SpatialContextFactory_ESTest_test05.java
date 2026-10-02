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
public class SpatialContextFactory_ESTest_test05 extends SpatialContextFactory_ESTest_scaffolding {

    // Verifies that makeSpatialContext returns a geographic context with normWrapLongitude disabled
    // even when worldBounds is set to an invalid/non-parseable string value.
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        HashMap<String, String> factoryArgs = new HashMap<String, String>();
        factoryArgs.put("worldBounds", "worldBounds");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(factoryArgs, systemClassLoader);

        assertFalse(spatialContext.isNormWrapLongitude());
        assertTrue(spatialContext.isGeo());
    }
}
