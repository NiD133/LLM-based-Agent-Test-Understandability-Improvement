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
     * Verifies that writing a point via JtsBinaryCodec does not mutate the
     * point's longitude coordinate. The output stream is backed by a
     * MockPrintStream so no real file I/O is required.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Build a JTS spatial context using default factory settings
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = factory.newSpatialContext();
        JtsBinaryCodec codec = new JtsBinaryCodec(spatialContext, factory);

        // Use a mock output stream as the serialization sink
        MockPrintStream mockOutput = new MockPrintStream("Expected initial read of one byte, not: ");
        ObjectOutputStream objectOutput = new ObjectOutputStream(mockOutput);

        // Create a point at longitude=10.0, latitude=3877.7633
        PointImpl point = new PointImpl(10.0, 3877.7633, spatialContext);

        // Write the point; longitude must remain unchanged after serialization
        codec.writePoint(objectOutput, point);
        assertEquals(10.0, point.getLon(), 0.01);
    }
}
