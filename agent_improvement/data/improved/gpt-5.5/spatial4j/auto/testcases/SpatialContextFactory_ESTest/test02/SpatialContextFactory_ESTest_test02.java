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
public class SpatialContextFactory_ESTest_test02 extends SpatialContextFactory_ESTest_scaffolding {

    private static final String SHAPE_FACTORY_CLASS_KEY = "shapeFactoryClass";
    private static final String INVALID_SHAPE_FACTORY_CLASS = "shapeFactoryClass";
    private static final String SPATIAL_CONTEXT_FACTORY_CLASS =
            "org.locationtech.spatial4j.context.SpatialContextFactory";

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        HashMap<String, String> factoryArguments = new HashMap<String, String>();
        factoryArguments.put(SHAPE_FACTORY_CLASS_KEY, INVALID_SHAPE_FACTORY_CLASS);
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(factoryArguments, systemClassLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Invalid value 'shapeFactoryClass' on field shapeFactoryClass of type class java.lang.Class
            verifyException(SPATIAL_CONTEXT_FACTORY_CLASS, e);
        }
    }
}
