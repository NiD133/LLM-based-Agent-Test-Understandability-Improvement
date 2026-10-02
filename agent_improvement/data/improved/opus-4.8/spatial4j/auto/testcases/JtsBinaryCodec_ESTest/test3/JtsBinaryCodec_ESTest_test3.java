package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test3 extends JtsBinaryCodec_ESTest_scaffolding {

    /**
     * When {@code readShapeByTypeIfSupported} is given a type byte that does not
     * match the JTS geometry type, the codec delegates to its superclass, which
     * does not recognise the type and therefore returns {@code null}.
     */
    @Test(timeout = 4000)
    public void readShapeWithUnsupportedTypeReturnsNull() throws Throwable {
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = contextFactory.newSpatialContext();
        JtsBinaryCodec binaryCodec = new JtsBinaryCodec(spatialContext, contextFactory);

        // Any input source works here; the unsupported type byte short-circuits
        // before the stream is actually read.
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(new byte[5]));
        byte unsupportedType = (byte) 81;

        Shape shape = binaryCodec.readShapeByTypeIfSupported(input, unsupportedType);

        assertNull(shape);
    }
}
