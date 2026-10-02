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
public class JtsBinaryCodec_ESTest_test2 extends JtsBinaryCodec_ESTest_scaffolding {

    // TYPE_GEOM (value 5) is the codec type that routes to JTS WKB geometry serialization.
    // When writeShapeByTypeIfSupported receives this type, it must write the geometry and return true.
    private static final byte TYPE_GEOM = (byte) 5;

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Set up the spatial context and codec under test
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = new JtsSpatialContext(factory);
        JtsBinaryCodec codec = new JtsBinaryCodec(spatialContext, factory);

        // Wrap a mock file output stream in a DataOutputStream for binary writing
        MockFileOutputStream mockFileOut = new MockFileOutputStream("Expected initial read of one byte, not: ");
        DataOutputStream dataOut = new DataOutputStream(mockFileOut);

        // A simple point at the origin (0, 0) as the shape to serialize
        PointImpl originPoint = new PointImpl(0, 0, spatialContext);

        // Writing with TYPE_GEOM should delegate to JTS WKB geometry serialization and return true
        boolean wasHandled = codec.writeShapeByTypeIfSupported(dataOut, originPoint, TYPE_GEOM);
        assertTrue(wasHandled);
    }
}
