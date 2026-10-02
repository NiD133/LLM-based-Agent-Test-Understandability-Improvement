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

    private static final String UNKNOWN_FIELD_NAME = "BhhavP[EGM<a";
    private static final String SPATIAL_CONTEXT_FACTORY_CLASS =
            "org.locationtech.spatial4j.context.SpatialContextFactory";

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        SpatialContextFactory factory = new SpatialContextFactory();

        try {
            factory.initField(UNKNOWN_FIELD_NAME);
            fail("Expecting exception: Error");
        } catch (Error error) {
            // initField wraps the NoSuchFieldException for the unknown field in an Error.
            verifyException(SPATIAL_CONTEXT_FACTORY_CLASS, error);
        }
    }
}
