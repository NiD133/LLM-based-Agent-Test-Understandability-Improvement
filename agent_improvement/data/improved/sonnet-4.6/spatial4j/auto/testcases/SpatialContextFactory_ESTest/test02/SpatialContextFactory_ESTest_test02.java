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

    /**
     * Verifies that providing a non-existent class name as the "shapeFactoryClass"
     * argument causes makeSpatialContext to throw a RuntimeException, because the
     * factory cannot load the class when initializing the shapeFactoryClass field.
     */
    @Test(timeout = 4000)
    public void test02_invalidShapeFactoryClassNameThrowsRuntimeException() throws Throwable {
        HashMap<String, String> argsWithInvalidShapeFactoryClass = new HashMap<String, String>();
        argsWithInvalidShapeFactoryClass.put("shapeFactoryClass", "shapeFactoryClass");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(argsWithInvalidShapeFactoryClass, systemClassLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // Invalid value 'shapeFactoryClass' on field shapeFactoryClass of type class java.lang.Class
            //
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
