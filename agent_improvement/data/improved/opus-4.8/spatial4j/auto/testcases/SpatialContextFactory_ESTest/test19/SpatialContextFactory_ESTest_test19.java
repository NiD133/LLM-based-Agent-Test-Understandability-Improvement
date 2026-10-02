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
public class SpatialContextFactory_ESTest_test19 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * When the args map names {@link SpatialContextFactory} itself as the
     * "spatialContextFactory" implementation, the factory should build a
     * default {@link SpatialContext}: geodesic, without longitude normalization.
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_withDefaultFactoryClass_buildsGeoContext() throws Throwable {
        Map<String, String> args = new HashMap<String, String>();
        args.put("spatialContextFactory",
                "org.locationtech.spatial4j.context.SpatialContextFactory");

        // A null ClassLoader makes the factory fall back to its own class loader.
        SpatialContext context = SpatialContextFactory.makeSpatialContext(args, (ClassLoader) null);

        assertFalse("longitude wrapping should default to off", context.isNormWrapLongitude());
        assertTrue("context should default to geodesic", context.isGeo());
    }
}
