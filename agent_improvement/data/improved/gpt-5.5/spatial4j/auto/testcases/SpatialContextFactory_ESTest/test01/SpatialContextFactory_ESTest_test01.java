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
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        Class<ShapeFactoryImpl> shapeFactoryClass = ShapeFactoryImpl.class;
        factory.shapeFactoryClass = shapeFactoryClass;

        try {
            factory.newSpatialContext();
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException exception) {
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", exception);
        }
    }
}
