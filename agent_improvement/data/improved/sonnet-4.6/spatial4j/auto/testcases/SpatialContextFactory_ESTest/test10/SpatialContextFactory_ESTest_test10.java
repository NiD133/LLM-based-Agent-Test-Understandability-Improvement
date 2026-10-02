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
public class SpatialContextFactory_ESTest_test10 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * When the "readers" config param is set to a non-existent class name, makeSpatialContext
     * should throw a RuntimeException with "Unable to find format class".
     */
    @Test(timeout = 4000)
    public void test10_invalidReaderClassNameThrowsRuntimeException() throws Throwable {
        HashMap<String, String> configArgs = new HashMap<String, String>();
        configArgs.put("readers", "readers"); // "readers" is not a valid class name

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(configArgs, systemClassLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // Unable to find format class
            //
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
