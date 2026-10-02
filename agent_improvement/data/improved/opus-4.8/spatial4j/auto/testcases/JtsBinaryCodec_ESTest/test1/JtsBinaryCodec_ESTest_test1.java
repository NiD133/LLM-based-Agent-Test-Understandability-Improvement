package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.assertFalse;

import java.io.DataOutputStream;
import java.io.PipedOutputStream;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test1 extends JtsBinaryCodec_ESTest_scaffolding {

    /**
     * writeShapeByTypeIfSupported() should report that it did NOT write the shape
     * when the requested type is not the JTS geometry type. Here type 0 is not
     * TYPE_GEOM, so the codec delegates to the superclass, which does not handle
     * the (null) shape and returns false.
     */
    @Test(timeout = 4000)
    public void writeShapeWithUnsupportedTypeReturnsFalse() throws Throwable {
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = new JtsSpatialContext(contextFactory);
        JtsBinaryCodec binaryCodec = new JtsBinaryCodec(spatialContext, contextFactory);

        DataOutputStream output = new DataOutputStream(new PipedOutputStream());
        byte unsupportedType = (byte) 0;

        boolean shapeWasWritten =
                binaryCodec.writeShapeByTypeIfSupported(output, (Shape) null, unsupportedType);

        assertFalse(shapeWasWritten);
    }
}
