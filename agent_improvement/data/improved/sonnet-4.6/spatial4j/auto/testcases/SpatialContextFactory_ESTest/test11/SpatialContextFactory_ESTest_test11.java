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
public class SpatialContextFactory_ESTest_test11 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that {@link SpatialContextFactory#makeSpatialContext} throws a
     * {@link RuntimeException} when the "distCalculator" argument is set to an
     * unrecognised calculator name.  Valid values are haversine, lawOfCosines,
     * vincentySphere, cartesian, and cartesian^2; anything else should be rejected.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Use an invalid calculator name (the key itself) to trigger the error path
        HashMap<String, String> argsWithInvalidCalculator = new HashMap<String, String>();
        argsWithInvalidCalculator.put("distCalculator", "distCalculator");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(argsWithInvalidCalculator, systemClassLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Expected: "Unknown calculator: distCalculator"
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
