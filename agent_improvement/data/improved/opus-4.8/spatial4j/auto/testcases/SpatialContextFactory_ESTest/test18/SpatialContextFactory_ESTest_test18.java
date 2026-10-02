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
public class SpatialContextFactory_ESTest_test18 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * When "shapeFactoryClass" points to a class that lacks a constructor matching
     * the arguments the factory passes (here org.noggit.JSONParser, which has no
     * (SpatialContext, SpatialContextFactory) constructor), building the context
     * must fail with a RuntimeException thrown from SpatialContextFactory itself.
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_withIncompatibleShapeFactoryClass_throwsRuntimeException() throws Throwable {
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("shapeFactoryClass", "org.noggit.JSONParser");
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(args, classLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // org.noggit.JSONParser has no constructor matching the factory's arguments,
            // so makeClassInstance throws a RuntimeException from SpatialContextFactory.
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
