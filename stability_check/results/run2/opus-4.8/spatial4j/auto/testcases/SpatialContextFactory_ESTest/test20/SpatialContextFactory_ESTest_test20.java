package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test20 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that building the shape read/write formats fails when the
     * SpatialContext was produced by the base {@link SpatialContextFactory}
     * but the formats are requested through a {@link JtsSpatialContextFactory}.
     *
     * The base factory yields a plain SpatialContext, whereas the JTS factory's
     * default writers (e.g. JtsGeoJSONWriter) require a constructor that accepts
     * the JTS factory itself. No matching constructor is found for the plain
     * context, so {@code makeFormats} throws a RuntimeException.
     */
    @Test(timeout = 4000)
    public void makeFormatsWithMismatchedContextThrowsRuntimeException() throws Throwable {
        // Build a default SpatialContext from an empty configuration.
        Map<String, String> emptyConfig = new HashMap<String, String>();
        SpatialContext defaultContext =
                SpatialContextFactory.makeSpatialContext(emptyConfig, (ClassLoader) null);

        // Ask the JTS factory to create formats for the non-JTS context.
        JtsSpatialContextFactory jtsFactory = new JtsSpatialContextFactory();
        try {
            jtsFactory.makeFormats(defaultContext);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // Thrown because a JTS writer has no constructor matching the plain context.
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
