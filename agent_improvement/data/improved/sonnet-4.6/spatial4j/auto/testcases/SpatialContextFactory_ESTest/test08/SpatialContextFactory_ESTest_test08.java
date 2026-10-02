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
public class SpatialContextFactory_ESTest_test08 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * When the deprecated "wktShapeParserClass" argument names a class that does not exist,
     * makeSpatialContext should throw a RuntimeException with the message "Unable to find format class".
     * The value "readers" is used here because it is not a valid Java class name.
     */
    @Test(timeout = 4000)
    public void test08_invalidWktShapeParserClass_throwsRuntimeException() throws Throwable {
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("wktShapeParserClass", "readers");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(args, systemClassLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // Unable to find format class
            //
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
