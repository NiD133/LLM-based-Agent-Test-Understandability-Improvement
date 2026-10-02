package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test02 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * The "shapeFactoryClass" argument must be a fully-qualified Java class name so the
     * factory can load it via the class loader. Here the value is the bogus string
     * "shapeFactoryClass", which is not a resolvable class name, so makeSpatialContext
     * is expected to fail with a RuntimeException reporting the invalid field value.
     */
    @Test(timeout = 4000)
    public void shapeFactoryClassWithNonClassNameValueThrowsRuntimeException() throws Throwable {
        Map<String, String> args = new HashMap<String, String>();
        args.put("shapeFactoryClass", "shapeFactoryClass");
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(args, classLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Invalid value 'shapeFactoryClass' on field shapeFactoryClass of type class java.lang.Class
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
