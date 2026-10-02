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

    /**
     * Verifies that explicitly specifying the base SpatialContextFactory class in the args map
     * produces a SpatialContext with default settings: geographic mode enabled and longitude
     * normalization disabled.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // Configure args to use the base factory class explicitly (rather than a subclass)
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("spatialContextFactory", "org.locationtech.spatial4j.context.SpatialContextFactory");

        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(args, (ClassLoader) null);

        // Default factory should produce a geographic context with longitude normalization off
        assertTrue(spatialContext.isGeo());
        assertFalse(spatialContext.isNormWrapLongitude());
    }
}
