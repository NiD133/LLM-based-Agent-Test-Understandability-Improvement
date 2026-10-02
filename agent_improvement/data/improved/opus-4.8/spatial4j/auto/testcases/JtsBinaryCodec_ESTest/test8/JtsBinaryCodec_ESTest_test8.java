package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.DataOutput;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test8 extends JtsBinaryCodec_ESTest_scaffolding {

    /**
     * writeDim() must dereference the supplied DataOutput in order to write the
     * dimension value. Passing a null DataOutput therefore triggers a
     * NullPointerException from inside JtsBinaryCodec.
     */
    @Test(timeout = 4000)
    public void writeDimWithNullOutputThrowsNullPointerException() throws Throwable {
        // Build a codec that uses single-precision floats for dimension values.
        JtsSpatialContextFactory contextFactory = new JtsSpatialContextFactory();
        contextFactory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        JtsSpatialContext context = contextFactory.newSpatialContext();
        JtsBinaryCodec codec = new JtsBinaryCodec(context, contextFactory);

        try {
            codec.writeDim((DataOutput) null, 0);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The exception originates in JtsBinaryCodec and carries no message.
            verifyException("org.locationtech.spatial4j.io.jts.JtsBinaryCodec", e);
        }
    }
}
