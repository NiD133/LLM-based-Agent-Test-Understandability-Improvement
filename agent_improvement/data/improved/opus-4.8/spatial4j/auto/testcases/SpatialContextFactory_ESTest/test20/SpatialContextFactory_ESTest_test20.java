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
     * A {@link SpatialContext} built from an empty argument map uses the plain
     * (non-JTS) factory, so it does not carry the JTS-specific format setup.
     * Asking a {@link JtsSpatialContextFactory} to build formats for such a
     * context fails: the JTS writers (e.g. JtsGeoJSONWriter) require a
     * constructor that the plain context cannot satisfy, which surfaces as a
     * RuntimeException thrown from SpatialContextFactory.
     */
    @Test(timeout = 4000)
    public void makeFormatsForNonJtsContextThrowsRuntimeException() throws Throwable {
        HashMap<String, String> emptyArgs = new HashMap<String, String>();
        SpatialContext nonJtsContext =
                SpatialContextFactory.makeSpatialContext(emptyArgs, (ClassLoader) null);

        JtsSpatialContextFactory jtsFactory = new JtsSpatialContextFactory();

        try {
            jtsFactory.makeFormats(nonJtsContext);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // JtsGeoJSONWriter needs a constructor the non-JTS context cannot provide;
            // the failure originates in SpatialContextFactory's reflective instantiation.
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
