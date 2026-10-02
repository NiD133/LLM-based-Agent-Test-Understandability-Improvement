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
public class SpatialContextFactory_ESTest_test01 extends SpatialContextFactory_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        JtsSpatialContextFactory jtsSpatialContextFactory0 = new JtsSpatialContextFactory();
        Class<ShapeFactoryImpl> class0 = ShapeFactoryImpl.class;
        jtsSpatialContextFactory0.shapeFactoryClass = class0;
        // Undeclared exception!
        try {
            jtsSpatialContextFactory0.newSpatialContext();
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // org.evosuite.runtime.mock.java.lang.MockThrowable: java.lang.reflect.InvocationTargetException
            //
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
