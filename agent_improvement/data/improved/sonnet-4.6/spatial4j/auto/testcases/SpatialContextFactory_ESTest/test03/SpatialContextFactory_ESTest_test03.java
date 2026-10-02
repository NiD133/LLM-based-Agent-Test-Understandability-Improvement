package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test03 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that {@link SpatialContextFactory#initField(String)} throws an {@link Error}
     * wrapping {@link NoSuchFieldException} when the given name does not correspond to any
     * declared field on the factory class.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        SpatialContextFactory factory = new SpatialContextFactory();

        try {
            // "BhhavP[EGM<a" is not a valid field name on SpatialContextFactory,
            // so initField must throw an Error wrapping NoSuchFieldException.
            factory.initField("BhhavP[EGM<a");
            fail("Expecting exception: Error");
        } catch (Error e) {
            // Expected: java.lang.NoSuchFieldException: BhhavP[EGM<a
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
