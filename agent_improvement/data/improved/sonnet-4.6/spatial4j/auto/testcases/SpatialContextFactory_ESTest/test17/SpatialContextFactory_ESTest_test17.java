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
public class SpatialContextFactory_ESTest_test17 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that a SpatialContext created from a config map containing a
     * non-boolean value for "normWrapLongitude" (which Boolean.valueOf() coerces
     * to false) still defaults geo=true, since the "geo" key is absent from the map.
     */
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        HashMap<String, String> config = new HashMap<String, String>();
        // "normWrapLongitude" as the value is not "true", so Boolean.valueOf yields false (the default)
        config.put("normWrapLongitude", "normWrapLongitude");

        SpatialContext spatialContext0 = SpatialContextFactory.makeSpatialContext(config, (ClassLoader) null);

        assertTrue(spatialContext0.isGeo());
    }
}
