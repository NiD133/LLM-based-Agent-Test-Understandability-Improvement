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
public class SpatialContextFactory_ESTest_test00 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that makeSpatialContext throws a RuntimeException when the
     * "spatialContextFactory" config entry names a class that cannot be
     * instantiated as a SpatialContextFactory (JtsShapeFactory is a shape
     * factory, not a context factory, so newInstance() raises
     * InstantiationException which is wrapped in RuntimeException).
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        HashMap<String, String> config = new HashMap<String, String>();
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        config.put("spatialContextFactory", "org.locationtech.spatial4j.shape.jts.JtsShapeFactory");

        try {
            SpatialContextFactory.makeSpatialContext(config, systemClassLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // JtsShapeFactory cannot be instantiated as a SpatialContextFactory;
            // the resulting InstantiationException is wrapped in a RuntimeException.
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
