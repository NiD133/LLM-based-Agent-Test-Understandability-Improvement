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
public class SpatialContextFactory_ESTest_test09 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * When the "readers" argument holds only a lone comma, splitting it on commas yields no
     * class names, so no ShapeReader classes are loaded and the factory builds a context using
     * the default settings (geographic, longitude not wrap-normalized).
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_withCommaOnlyReadersArg_usesDefaults() throws Throwable {
        Map<String, String> config = new HashMap<String, String>();
        config.put("readers", ",");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        SpatialContext context = SpatialContextFactory.makeSpatialContext(config, systemClassLoader);

        assertFalse("normWrapLongitude defaults to false", context.isNormWrapLongitude());
        assertTrue("geo defaults to true", context.isGeo());
    }
}
