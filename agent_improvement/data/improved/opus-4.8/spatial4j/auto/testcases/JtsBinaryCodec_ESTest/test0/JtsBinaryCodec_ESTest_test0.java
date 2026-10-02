package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ObjectOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.impl.PointImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test0 extends JtsBinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that a point can be written through {@link JtsBinaryCodec#writePoint}
     * without altering the point itself: the longitude read back from the point
     * still matches the value it was constructed with.
     */
    @Test(timeout = 4000)
    public void writePointLeavesPointUnchanged() throws Throwable {
        // Build a JTS-backed spatial context and the codec under test.
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = contextFactory.newSpatialContext();
        JtsBinaryCodec codec = new JtsBinaryCodec(spatialContext, contextFactory);

        // Destination stream the codec writes the point's binary form into.
        MockPrintStream outputFile = new MockPrintStream("Expected initial read of one byte, not: ");
        ObjectOutputStream binaryOutput = new ObjectOutputStream(outputFile);

        // The point to serialize: longitude 10.0, latitude 3877.7633.
        double expectedLongitude = 10.0;
        PointImpl point = new PointImpl(expectedLongitude, 3877.7633, spatialContext);

        codec.writePoint(binaryOutput, point);

        // Writing the point must not mutate it; its longitude is unchanged.
        assertEquals(expectedLongitude, point.getLon(), 0.01);
    }
}
