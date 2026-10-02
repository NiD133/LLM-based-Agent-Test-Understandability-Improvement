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
public class SpatialContextFactory_ESTest_test03 extends SpatialContextFactory_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        SpatialContextFactory spatialContextFactory0 = new SpatialContextFactory();
        // Undeclared exception!
        try {
            spatialContextFactory0.initField("BhhavP[EGM<a");
            fail("Expecting exception: Error");
        } catch (Error e) {
            //
            // java.lang.NoSuchFieldException: BhhavP[EGM<a
            //
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
