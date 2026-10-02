package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.DataOutputStream;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileOutputStream;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.impl.PointImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test2 extends JtsBinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that {@link JtsBinaryCodec#writeShapeByTypeIfSupported} returns {@code true}
     * when asked to write a point. The supplied type byte (5) is not {@code TYPE_GEOM}, so the
     * codec delegates to its superclass, which knows how to serialize a plain point and reports
     * success.
     */
    @Test(timeout = 4000)
    public void writeShapeByTypeReturnsTrueForPoint() throws Throwable {
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = new JtsSpatialContext(contextFactory);
        JtsBinaryCodec binaryCodec = new JtsBinaryCodec(spatialContext, contextFactory);

        // Destination stream that the encoded shape would be written to.
        MockFileOutputStream fileOutputStream =
                new MockFileOutputStream("Expected initial read of one byte, not: ");
        DataOutputStream dataOutput = new DataOutputStream(fileOutputStream);

        PointImpl point = new PointImpl(0, 0, spatialContext);
        byte nonGeomType = (byte) 5;

        boolean wasWritten = binaryCodec.writeShapeByTypeIfSupported(dataOutput, point, nonGeomType);

        assertTrue(wasWritten);
    }
}
