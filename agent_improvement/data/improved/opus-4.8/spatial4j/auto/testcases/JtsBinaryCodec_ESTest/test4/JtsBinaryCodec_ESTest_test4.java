package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test4 extends JtsBinaryCodec_ESTest_scaffolding {

    /**
     * The shape type byte 5 maps to TYPE_GEOM, so reading routes to the JTS WKB
     * reader. The input is five zero bytes, which is not valid WKB, so the codec
     * is expected to wrap the JTS parse failure in a RuntimeException ("error
     * reading WKT").
     */
    @Test(timeout = 4000)
    public void readShapeWithGeomTypeFromInvalidWkbThrows() throws Throwable {
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = contextFactory.newSpatialContext();
        JtsBinaryCodec codec = new JtsBinaryCodec(spatialContext, contextFactory);

        byte[] invalidWkbBytes = new byte[5];
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(invalidWkbBytes));
        byte geomShapeType = (byte) 5;

        try {
            codec.readShapeByTypeIfSupported(input, geomShapeType);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // error reading WKT
            verifyException("org.locationtech.spatial4j.io.jts.JtsBinaryCodec", e);
        }
    }
}
