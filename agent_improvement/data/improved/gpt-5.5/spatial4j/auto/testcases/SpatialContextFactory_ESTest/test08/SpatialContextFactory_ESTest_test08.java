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
public class SpatialContextFactory_ESTest_test08 extends SpatialContextFactory_ESTest_scaffolding {

    private static final String WKT_PARSER_CLASS_KEY = "wktShapeParserClass";
    private static final String UNKNOWN_READER_CLASS = "readers";

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        HashMap<String, String> configuration = new HashMap<String, String>();
        configuration.put(WKT_PARSER_CLASS_KEY, UNKNOWN_READER_CLASS);

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        // The deprecated WKT parser setting still tries to load the configured reader class.
        try {
            SpatialContextFactory.makeSpatialContext(configuration, classLoader);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            //
            // Unable to find format class
            //
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
