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
public class SpatialContextFactory_ESTest_test20 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * A SpatialContext built from the plain SpatialContextFactory cannot be used to
     * build the JTS-specific formats: JtsSpatialContextFactory.makeFormats tries to
     * instantiate JtsGeoJSONWriter, whose constructor expects a JtsSpatialContextFactory,
     * so makeClassInstance fails and wraps the error in a RuntimeException.
     */
    @Test(timeout = 4000)
    public void makeFormatsWithNonJtsContextThrowsRuntimeException() throws Throwable {
        // A default (non-JTS) SpatialContext.
        HashMap<String, String> emptyArgs = new HashMap<String, String>();
        SpatialContext defaultContext = SpatialContextFactory.makeSpatialContext(emptyArgs, (ClassLoader) null);

        JtsSpatialContextFactory jtsFactory = new JtsSpatialContextFactory();

        try {
            jtsFactory.makeFormats(defaultContext);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException expected) {
            // JtsGeoJSONWriter has no constructor accepting the given (SpatialContext, factory) args.
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", expected);
        }
    }
}
