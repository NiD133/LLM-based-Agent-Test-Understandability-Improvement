package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.DataInput;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test7 extends JtsBinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that reading a dimension from a null DataInput fails fast with a
     * NullPointerException. The codec is configured for double precision (the
     * default), so readDim delegates to BinaryCodec.readDim, which dereferences
     * the null input and is therefore where the exception originates.
     */
    @Test(timeout = 4000)
    public void readDimFromNullInputThrowsNullPointerException() throws Throwable {
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        JtsSpatialContext spatialContext = new JtsSpatialContext(contextFactory);
        JtsBinaryCodec binaryCodec = new JtsBinaryCodec(spatialContext, contextFactory);

        try {
            binaryCodec.readDim((DataInput) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The exception carries no message and is raised inside the
            // superclass BinaryCodec, not the JTS subclass.
            verifyException("org.locationtech.spatial4j.io.BinaryCodec", e);
        }
    }
}
