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
public class SpatialContextFactory_ESTest_test17 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * The factory should default to a geographic context. Here the args map only
     * supplies a "normWrapLongitude" entry whose value is not "true", so that flag
     * stays at its default of false and the "geo" flag stays at its default of true.
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_defaultsToGeographicContext() throws Throwable {
        Map<String, String> args = new HashMap<String, String>();
        args.put("normWrapLongitude", "normWrapLongitude");

        SpatialContext context =
                SpatialContextFactory.makeSpatialContext(args, (ClassLoader) null);

        assertTrue("Factory should produce a geographic context by default", context.isGeo());
    }
}
