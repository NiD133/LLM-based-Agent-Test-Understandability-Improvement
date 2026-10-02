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
public class SpatialContextFactory_ESTest_test07 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * When the "writers" argument is just a comma (an empty, comma-separated list),
     * no writer classes are configured and the factory still builds a valid
     * SpatialContext using its defaults: a geodesic context that does not normalize
     * longitudes by wrapping.
     */
    @Test(timeout = 4000)
    public void makeSpatialContextWithEmptyWritersListUsesDefaults() throws Throwable {
        Map<String, String> args = new HashMap<String, String>();
        args.put("writers", ",");
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        SpatialContext context = SpatialContextFactory.makeSpatialContext(args, classLoader);

        assertFalse("normWrapLongitude should default to false", context.isNormWrapLongitude());
        assertTrue("context should default to geodesic (geo)", context.isGeo());
    }
}
