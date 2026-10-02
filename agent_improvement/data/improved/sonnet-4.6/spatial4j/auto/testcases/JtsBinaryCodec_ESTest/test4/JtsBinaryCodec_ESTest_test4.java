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

/**
 * Tests that JtsBinaryCodec throws a RuntimeException with "error reading WKT"
 * when attempting to decode a geometry shape from a malformed (all-zero) byte stream.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test4 extends JtsBinaryCodec_ESTest_scaffolding {

    // TYPE_GEOM = 5: triggers the WKB geometry decoding path in JtsBinaryCodec
    private static final byte TYPE_GEOM = (byte) 5;

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        // Build a default JTS spatial context
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        JtsSpatialContext ctx = factory.newSpatialContext();

        // A 5-byte all-zero array produces invalid WKB — the codec will fail to parse it
        byte[] invalidWkbBytes = new byte[5];
        ByteArrayInputStream byteStream = new ByteArrayInputStream(invalidWkbBytes);
        DataInputStream dataInput = new DataInputStream(byteStream);

        JtsBinaryCodec codec = new JtsBinaryCodec(ctx, factory);

        // Reading a geometry shape (type 5) from invalid WKB must throw RuntimeException
        try {
            codec.readShapeByTypeIfSupported(dataInput, TYPE_GEOM);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException e) {
            // JtsBinaryCodec wraps ParseException as InvalidShapeException("error reading WKT")
            verifyException("org.locationtech.spatial4j.io.jts.JtsBinaryCodec", e);
        }
    }
}
