package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test00 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * The "spatialContextFactory" argument names the class that makeSpatialContext
     * should instantiate as the factory. Here we point it at JtsShapeFactory, which is
     * NOT a SpatialContextFactory and has no usable no-arg constructor. The reflective
     * instantiation therefore fails with an InstantiationException, which the factory
     * rethrows wrapped in a RuntimeException.
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_withNonInstantiableFactoryClass_throwsRuntimeException() throws Throwable {
        Map<String, String> args = new HashMap<String, String>();
        args.put("spatialContextFactory", "org.locationtech.spatial4j.shape.jts.JtsShapeFactory");
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(args, classLoader);
            fail("Expected a RuntimeException because JtsShapeFactory cannot be instantiated as a factory");
        } catch (RuntimeException e) {
            // Wraps a java.lang.InstantiationException thrown inside SpatialContextFactory.
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
