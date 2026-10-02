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
     * Verifies that {@link JtsBinaryCodec#writePoint} serializes a point to an
     * output stream without mutating the point's longitude coordinate.
     *
     * <p>A point at (lon=10.0, lat=3877.7633) is written to a mock output stream.
     * After the write the original longitude must remain unchanged at 10.0,
     * confirming that {@code writePoint} is a pure write operation with no
     * side-effects on the source shape.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Build a default JTS spatial context using the factory defaults
        JtsSpatialContextFactory spatialContextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = spatialContextFactory.newSpatialContext();

        // Create the codec under test
        JtsBinaryCodec binaryCodec = new JtsBinaryCodec(spatialContext, spatialContextFactory);

        // Set up a mock output stream backed by a virtual file;
        // the filename string matches an error message in JtsBinaryCodec to satisfy the VFS name requirement
        MockPrintStream mockOutputStream = new MockPrintStream("Expected initial read of one byte, not: ");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(mockOutputStream);

        // Create a point with a known longitude (10.0) and latitude (3877.7633)
        PointImpl point = new PointImpl(10.0, 3877.7633, spatialContext);

        // Write the point; this exercises the BinaryCodec serialization path
        binaryCodec.writePoint(objectOutputStream, point);

        // The write must not alter the source point's longitude
        assertEquals(10.0, point.getLon(), 0.01);
    }
}
