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
public class SpatialContextFactory_ESTest_test06 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * The "writers" argument must be a comma-separated list of ShapeWriter class
     * names. When the value is not a resolvable class name, the factory fails to
     * load the format class and wraps the ClassNotFoundException in a RuntimeException.
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_withUnknownWriterClass_throwsRuntimeException() throws Throwable {
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("writers", "writers"); // not a valid ShapeWriter class name

        try {
            SpatialContextFactory.makeSpatialContext(args, (ClassLoader) null);
            fail("Expecting exception: RuntimeException (unable to find format class)");
        } catch (RuntimeException e) {
            // Thrown by SpatialContextFactory when the writer class cannot be found.
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
