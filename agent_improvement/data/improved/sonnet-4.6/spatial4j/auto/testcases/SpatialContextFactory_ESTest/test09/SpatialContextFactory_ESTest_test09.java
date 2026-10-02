package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test09 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * A "readers" value of "," splits into only empty tokens, so no reader class is registered.
     * The factory falls back to its defaults: geo=true and normWrapLongitude=false.
     */
    @Test(timeout = 4000)
    public void test09_readersArgWithOnlyComma_createsDefaultGeoContext() throws Throwable {
        // "," splits into empty tokens only; the readers loop is effectively a no-op
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("readers", ",");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(args, systemClassLoader);

        // Default context should be geographic with longitude wrapping disabled
        assertFalse(spatialContext.isNormWrapLongitude());
        assertTrue(spatialContext.isGeo());
    }
}
