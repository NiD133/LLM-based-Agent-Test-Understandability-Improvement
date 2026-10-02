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
public class SpatialContextFactory_ESTest_test13 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that creating a SpatialContext with the "vincentysphere" distance
     * calculator (case-insensitive match for "vincentySphere") results in a
     * context that is geographic (geo=true by default).
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Arrange: configure the factory to use the Vincenty sphere distance calculator
        HashMap<String, String> factoryArgs = new HashMap<String, String>();
        factoryArgs.put("distCalculator", "vincentysphere");

        // Act: build the SpatialContext using the given configuration (no custom ClassLoader)
        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(factoryArgs, (ClassLoader) null);

        // Assert: the resulting context should be geographic (geo defaults to true)
        assertTrue(spatialContext.isGeo());
    }
}
