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
     * Verifies that writing a point through {@link JtsBinaryCodec#writePoint}
     * completes without altering the point's longitude coordinate.
     */
    @Test(timeout = 4000)
    public void writePointLeavesLongitudeUnchanged() throws Throwable {
        // Build a JTS-backed spatial context and its binary codec.
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = contextFactory.newSpatialContext();
        JtsBinaryCodec binaryCodec = new JtsBinaryCodec(spatialContext, contextFactory);

        // Any DataOutput sink works here; an ObjectOutputStream over a mock stream is used.
        MockPrintStream outputSink = new MockPrintStream("Expected initial read of one byte, not: ");
        ObjectOutputStream dataOutput = new ObjectOutputStream(outputSink);

        // A point whose longitude (10.0) we expect to survive the write unchanged.
        double expectedLongitude = 10.0;
        PointImpl point = new PointImpl(expectedLongitude, 3877.7633, spatialContext);

        binaryCodec.writePoint(dataOutput, point);

        assertEquals(expectedLongitude, point.getLon(), 0.01);
    }
}
