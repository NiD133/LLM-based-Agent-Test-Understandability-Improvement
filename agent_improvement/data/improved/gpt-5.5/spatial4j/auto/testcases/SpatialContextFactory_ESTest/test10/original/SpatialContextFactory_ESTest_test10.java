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
public class SpatialContextFactory_ESTest_test10 extends SpatialContextFactory_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        HashMap<String, String> hashMap0 = new HashMap<String, String>();
        hashMap0.put("readers", "readers");
        ClassLoader classLoader0 = ClassLoader.getSystemClassLoader();
        // Undeclared exception!
        try {
            SpatialContextFactory.makeSpatialContext(hashMap0, classLoader0);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // Unable to find format class
            //
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
