package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test11 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * The "distCalculator" argument must name one of the known distance calculators
     * (e.g. haversine, cartesian). An unrecognized name should make
     * makeSpatialContext fail with a RuntimeException ("Unknown calculator: ...").
     */
    @Test(timeout = 4000)
    public void unknownDistanceCalculatorThrowsRuntimeException() throws Throwable {
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("distCalculator", "distCalculator"); // not a valid calculator name

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(args, classLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Thrown by SpatialContextFactory.initCalculator: "Unknown calculator: distCalculator"
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
