package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test3 extends JtsBinaryCodec_ESTest_scaffolding {

    // 81 (0x51) is not a recognized shape type in JtsBinaryCodec or its BinaryCodec superclass,
    // so readShapeByTypeIfSupported should return null rather than attempt to decode it.
    private static final byte UNRECOGNIZED_SHAPE_TYPE = (byte) 81;

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // Build a JTS spatial context with default factory settings
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = factory.newSpatialContext();

        // Provide a minimal backing stream (5 zero bytes) — content is irrelevant
        // because the unrecognized type causes an early-return before any bytes are read
        byte[] emptyData = new byte[5];
        ByteArrayInputStream byteStream = new ByteArrayInputStream(emptyData);
        DataInputStream dataInput = new DataInputStream(byteStream);

        JtsBinaryCodec codec = new JtsBinaryCodec(spatialContext, factory);

        // An unrecognized shape type must yield null
        Shape result = codec.readShapeByTypeIfSupported(dataInput, UNRECOGNIZED_SHAPE_TYPE);
        assertNull(result);
    }
}
