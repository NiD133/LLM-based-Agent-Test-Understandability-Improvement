package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test20 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that calling makeFormats() on a JtsSpatialContextFactory with a plain
     * (non-JTS) SpatialContext throws a RuntimeException. This happens because the
     * JTS-specific writer classes (e.g. JtsGeoJSONWriter) require a JtsSpatialContext,
     * and no matching constructor is found for the plain SpatialContext instance.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // Create a plain SpatialContext using default (empty) configuration
        HashMap<String, String> defaultConfig = new HashMap<String, String>();
        SpatialContext plainSpatialContext = SpatialContextFactory.makeSpatialContext(defaultConfig, (ClassLoader) null);

        // JtsSpatialContextFactory registers JTS-specific format writers that expect a JtsSpatialContext
        JtsSpatialContextFactory jtsFactory = new JtsSpatialContextFactory();

        // Passing a plain SpatialContext to makeFormats() should fail because JtsGeoJSONWriter
        // does not have a constructor compatible with the plain SpatialContext type
        try {
            jtsFactory.makeFormats(plainSpatialContext);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
