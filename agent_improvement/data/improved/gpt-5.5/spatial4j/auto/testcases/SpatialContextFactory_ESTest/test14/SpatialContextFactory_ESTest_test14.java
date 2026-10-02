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
public class SpatialContextFactory_ESTest_test14 extends SpatialContextFactory_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        HashMap<String, String> spatialContextOptions = new HashMap<String, String>();
        spatialContextOptions.put("distCalculator", "lawofcosines");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(spatialContextOptions, systemClassLoader);

        assertFalse(spatialContext.isNormWrapLongitude());
    }
}
