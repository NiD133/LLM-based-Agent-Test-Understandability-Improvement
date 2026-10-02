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

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        HashMap<String, String> hashMap0 = new HashMap<String, String>();
        ClassLoader classLoader0 = ClassLoader.getSystemClassLoader();
        hashMap0.put("spatialContextFactory", "org.locationtech.spatial4j.shape.jts.JtsShapeFactory");
        // Undeclared exception!
        try {
            SpatialContextFactory.makeSpatialContext(hashMap0, classLoader0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // java.lang.InstantiationException: org.locationtech.spatial4j.shape.jts.JtsShapeFactory
            //
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
