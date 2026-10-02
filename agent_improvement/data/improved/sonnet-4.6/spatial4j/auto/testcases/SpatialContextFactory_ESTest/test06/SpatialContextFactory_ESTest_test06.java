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
public class SpatialContextFactory_ESTest_test06 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that providing a non-existent class name as the "writers" format
     * causes makeSpatialContext to throw a RuntimeException with the message
     * "Unable to find format class", because Class.forName("writers") will fail.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        HashMap<String, String> configWithInvalidWriterClass = new HashMap<String, String>();
        // "writers" is not a valid fully-qualified class name, so initFormats() will
        // throw ClassNotFoundException, wrapped as RuntimeException("Unable to find format class")
        configWithInvalidWriterClass.put("writers", "writers");

        try {
            SpatialContextFactory.makeSpatialContext(configWithInvalidWriterClass, (ClassLoader) null);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // Unable to find format class
            //
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
