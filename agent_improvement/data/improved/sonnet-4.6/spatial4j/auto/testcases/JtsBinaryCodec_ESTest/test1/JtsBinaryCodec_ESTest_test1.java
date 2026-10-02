package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
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
     * Verifies that {@code writeShapeByTypeIfSupported} returns {@code false} when the given
     * type byte is not {@code TYPE_GEOM} (the JTS geometry marker). In that case the codec
     * delegates to the superclass, which does not recognise type 0 either and returns false.
     * The shape argument is null because the type check short-circuits before the shape is used.
     */
    @Test(timeout = 4000)
    public void test_writeShapeByTypeIfSupported_returnsFalse_whenTypeIsNotTypeGeom() throws Throwable {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        JtsSpatialContext context = new JtsSpatialContext(factory);
        JtsBinaryCodec codec = new JtsBinaryCodec(context, factory);

        PipedOutputStream pipedOutput = new PipedOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(pipedOutput);

        // type byte 0 is not TYPE_GEOM, so the codec does not handle it
        boolean handled = codec.writeShapeByTypeIfSupported(dataOutput, (Shape) null, (byte) 0);

        assertFalse(handled);
    }
}
