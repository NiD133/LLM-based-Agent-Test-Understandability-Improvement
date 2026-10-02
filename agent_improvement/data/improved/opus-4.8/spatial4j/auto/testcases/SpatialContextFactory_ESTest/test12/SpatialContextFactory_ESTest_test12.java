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

    /**
     * Building a SpatialContext while only overriding the distance calculator
     * should leave the unrelated settings at their defaults: the context is
     * still geodetic ("geo") and longitude normalization/wrapping stays off.
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_withCartesianCalculator_keepsGeoDefaults() throws Throwable {
        // Configure only the distance calculator; every other setting is left to its default.
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("distCalculator", "cartesian");

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(config, classLoader);

        // "geo" defaults to true and was not overridden.
        assertTrue(spatialContext.isGeo());
        // "normWrapLongitude" defaults to false and was not overridden.
        assertFalse(spatialContext.isNormWrapLongitude());
    }
}
