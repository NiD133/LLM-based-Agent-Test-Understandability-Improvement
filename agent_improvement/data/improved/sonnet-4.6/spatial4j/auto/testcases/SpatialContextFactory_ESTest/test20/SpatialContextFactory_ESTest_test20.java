package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test20 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that {@link JtsSpatialContextFactory#makeFormats} throws a
     * {@link RuntimeException} when given a plain (non-JTS) {@link SpatialContext}.
     *
     * The JTS factory registers writers such as {@code JtsGeoJSONWriter} that
     * require a two-arg constructor {@code (SpatialContext, SpatialContextFactory)}.
     * Because no matching constructor exists for the combination of a plain
     * {@code SpatialContext} and a {@code JtsSpatialContextFactory}, the reflective
     * instantiation inside {@code SpatialContextFactory.makeFormats} fails with a
     * {@code RuntimeException}.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // Build a default SpatialContext from an empty config (geo=true, Haversine, world bounds).
        HashMap<String, String> emptyConfig = new HashMap<String, String>();
        SpatialContext defaultContext = SpatialContextFactory.makeSpatialContext(emptyConfig, (ClassLoader) null);

        // A JtsSpatialContextFactory registers JTS-specific format writers.
        JtsSpatialContextFactory jtsFactory = new JtsSpatialContextFactory();

        // Calling makeFormats with a plain SpatialContext triggers reflective instantiation
        // of JtsGeoJSONWriter, which cannot be constructed without a JtsSpatialContext,
        // so a RuntimeException is expected.
        try {
            jtsFactory.makeFormats(defaultContext);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", e);
        }
    }
}
