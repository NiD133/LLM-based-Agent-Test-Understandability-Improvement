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
public class SpatialContextFactory_ESTest_test19 extends SpatialContextFactory_ESTest_scaffolding {

    private static final String FACTORY_PROPERTY = "spatialContextFactory";
    private static final String BASE_FACTORY_CLASS = "org.locationtech.spatial4j.context.SpatialContextFactory";

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        HashMap<String, String> configuration = new HashMap<String, String>();
        configuration.put(FACTORY_PROPERTY, BASE_FACTORY_CLASS);

        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(configuration, (ClassLoader) null);

        assertFalse(spatialContext.isNormWrapLongitude());
        assertTrue(spatialContext.isGeo());
    }
}
