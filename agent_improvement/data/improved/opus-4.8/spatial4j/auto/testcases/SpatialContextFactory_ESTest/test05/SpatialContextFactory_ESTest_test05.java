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

    /**
     * Builds a SpatialContext from a config map and the system class loader, then
     * verifies the resulting context keeps the factory defaults for the two
     * boolean flags: normWrapLongitude stays false and geo stays true.
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_keepsDefaultGeoAndNormWrapLongitudeFlags() throws Throwable {
        HashMap<String, String> configArgs = new HashMap<String, String>();
        configArgs.put("worldBounds", "worldBounds");
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        SpatialContext spatialContext =
                SpatialContextFactory.makeSpatialContext(configArgs, systemClassLoader);

        assertFalse(spatialContext.isNormWrapLongitude());
        assertTrue(spatialContext.isGeo());
    }
}
