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
     * Verifies that makeSpatialContext throws a RuntimeException when the
     * configured shapeFactoryClass (org.noggit.JSONParser) lacks the required
     * constructor signature (SpatialContext, SpatialContextFactory).
     */
    @Test(timeout = 4000)
    public void test18_makeSpatialContext_throwsWhenShapeFactoryClassLacksRequiredConstructor() throws Throwable {
        HashMap<String, String> contextArgs = new HashMap<String, String>();
        contextArgs.put("shapeFactoryClass", "org.noggit.JSONParser");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(contextArgs, systemClassLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // JSONParser has no constructor matching (SpatialContext, SpatialContextFactory)
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
