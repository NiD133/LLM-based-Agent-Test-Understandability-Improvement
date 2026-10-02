package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.DataOutput;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.impl.PointImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test6 extends JtsBinaryCodec_ESTest_scaffolding {

    /**
     * Writing a shape to a null DataOutput must fail fast with a
     * NullPointerException raised from the BinaryCodec superclass,
     * which attempts to use the output stream before any JTS-specific
     * handling occurs.
     */
    @Test(timeout = 4000)
    public void writeShapeToNullDataOutputThrowsNullPointerException() throws Throwable {
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext geoContext = JtsSpatialContext.GEO;
        JtsBinaryCodec codec = new JtsBinaryCodec(geoContext, contextFactory);

        PointImpl pointAtOrigin = new PointImpl(0, 0, geoContext);
        DataOutput nullOutput = null;

        try {
            codec.writeShape(nullOutput, pointAtOrigin);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The exception originates in the BinaryCodec superclass and
            // carries no message (getMessage() returns null).
            verifyException("org.locationtech.spatial4j.io.BinaryCodec", e);
        }
    }
}
