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

    /**
     * The "readers" argument must be a comma-separated list of fully-qualified
     * {@link org.locationtech.spatial4j.io.ShapeReader} class names. Here it is
     * set to the literal value "readers", which is not a resolvable class name.
     * The factory therefore fails to load the format class and wraps the
     * underlying ClassNotFoundException in a RuntimeException
     * ("Unable to find format class").
     */
    @Test(timeout = 4000)
    public void makeSpatialContext_withUnresolvableReaderClass_throwsRuntimeException() throws Throwable {
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("readers", "readers"); // not a valid ShapeReader class name
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(args, classLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // "Unable to find format class" thrown from SpatialContextFactory.initFormats
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
