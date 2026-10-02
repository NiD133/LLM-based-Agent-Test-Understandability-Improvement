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
public class SpatialContextFactory_ESTest_test13 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that selecting the "vincentySphere" distance calculator still
     * yields a geodesic (geo) spatial context. The "geo" flag defaults to true,
     * so configuring only the distance calculator must not change that default.
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_withVincentySphereCalculator_isGeo() throws Throwable {
        // Configure the factory to use the Vincenty sphere distance calculator.
        HashMap<String, String> factoryConfig = new HashMap<String, String>();
        factoryConfig.put("distCalculator", "vincentysphere");

        // Build the context using the default class loader (null).
        SpatialContext spatialContext =
                SpatialContextFactory.makeSpatialContext(factoryConfig, (ClassLoader) null);

        // The geo flag defaults to true and is unaffected by the calculator choice.
        assertTrue(spatialContext.isGeo());
    }
}
