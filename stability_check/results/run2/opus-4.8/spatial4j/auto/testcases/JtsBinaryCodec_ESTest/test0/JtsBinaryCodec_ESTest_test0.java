package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileOutputStream;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.shape.impl.PointImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test0 extends JtsBinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that writing a point through the JTS binary codec runs to
     * completion and leaves the source point unchanged. The point's longitude
     * is asserted afterwards to confirm the write did not mutate it.
     */
    @Test(timeout = 4000)
    public void writePointDoesNotMutateSourcePoint() throws Throwable {
        // Build a JTS spatial context and the codec under test.
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = contextFactory.newSpatialContext();
        JtsBinaryCodec codec = new JtsBinaryCodec(spatialContext, contextFactory);

        // Destination stream for the encoded point (contents are not inspected).
        MockPrintStream sink = new MockPrintStream("Expected initial read of one byte, not: ");
        ObjectOutputStream outputStream = new ObjectOutputStream(sink);

        // A point at longitude 10.0, latitude 3877.7633.
        double expectedLongitude = 10.0;
        PointImpl point = new PointImpl(expectedLongitude, 3877.7633, spatialContext);

        codec.writePoint(outputStream, point);

        // The source point should be untouched by the write.
        assertEquals(expectedLongitude, point.getLon(), 0.01);
    }
}
