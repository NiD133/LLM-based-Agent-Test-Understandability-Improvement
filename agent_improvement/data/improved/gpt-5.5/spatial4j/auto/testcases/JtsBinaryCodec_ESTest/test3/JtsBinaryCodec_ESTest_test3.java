package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileOutputStream;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test3 extends JtsBinaryCodec_ESTest_scaffolding {

    private static final int EMPTY_INPUT_LENGTH = 5;
    private static final byte UNSUPPORTED_SHAPE_TYPE = 81;

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = contextFactory.newSpatialContext();
        byte[] encodedShapeBytes = new byte[EMPTY_INPUT_LENGTH];
        ByteArrayInputStream encodedShapeStream = new ByteArrayInputStream(encodedShapeBytes);
        DataInputStream shapeInput = new DataInputStream(encodedShapeStream);
        JtsBinaryCodec codec = new JtsBinaryCodec(spatialContext, contextFactory);

        Shape decodedShape = codec.readShapeByTypeIfSupported(shapeInput, UNSUPPORTED_SHAPE_TYPE);

        assertNull(decodedShape);
    }
}
