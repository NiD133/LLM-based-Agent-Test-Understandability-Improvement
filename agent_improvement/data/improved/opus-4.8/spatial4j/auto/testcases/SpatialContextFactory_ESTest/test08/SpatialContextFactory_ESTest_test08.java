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
public class SpatialContextFactory_ESTest_test08 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * The legacy "wktShapeParserClass" argument is interpreted as a fully-qualified
     * class name for a ShapeReader. When that name ("readers" here) cannot be resolved
     * to a real class, makeSpatialContext must fail with a RuntimeException whose message
     * is "Unable to find format class".
     */
    @Test(timeout = 4000)
    public void makeSpatialContextThrowsWhenWktShapeParserClassCannotBeFound() throws Throwable {
        Map<String, String> args = new HashMap<String, String>();
        args.put("wktShapeParserClass", "readers");
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        try {
            SpatialContextFactory.makeSpatialContext(args, classLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Thrown by SpatialContextFactory.initFormats: "Unable to find format class"
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
