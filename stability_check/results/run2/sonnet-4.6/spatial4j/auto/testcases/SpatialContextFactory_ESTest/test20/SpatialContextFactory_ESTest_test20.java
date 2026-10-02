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
public class SpatialContextFactory_ESTest_test20 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that calling makeFormats on a JtsSpatialContextFactory with a plain (non-JTS)
     * SpatialContext throws a RuntimeException, because JTS-specific writers (e.g. JtsGeoJSONWriter)
     * require a constructor compatible with JtsSpatialContext, not the base SpatialContext.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        HashMap<String, String> emptyArgs = new HashMap<String, String>();
        SpatialContext plainSpatialContext = SpatialContextFactory.makeSpatialContext(emptyArgs, (ClassLoader) null);

        JtsSpatialContextFactory jtsFactory = new JtsSpatialContextFactory();

        // JtsGeoJSONWriter has no constructor accepting a plain SpatialContext + JtsSpatialContextFactory
        try {
            jtsFactory.makeFormats(plainSpatialContext);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
