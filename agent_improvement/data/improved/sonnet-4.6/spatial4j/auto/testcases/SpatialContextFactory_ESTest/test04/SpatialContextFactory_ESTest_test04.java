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
public class SpatialContextFactory_ESTest_test04 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that creating a SpatialContext with an empty (default) configuration
     * produces a geo-enabled context with longitude normalization disabled,
     * which are the expected out-of-the-box defaults.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Empty config map means all defaults apply
        HashMap<String, String> defaultConfig = new HashMap<String, String>();

        // Use the bootstrap (parent) class loader to load the factory
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader parentClassLoader = systemClassLoader.getParent();

        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(defaultConfig, parentClassLoader);

        // Default context should be geo (geographic coordinate system)
        assertTrue(spatialContext.isGeo());
        // Longitude wrapping normalization is off by default
        assertFalse(spatialContext.isNormWrapLongitude());
    }
}
