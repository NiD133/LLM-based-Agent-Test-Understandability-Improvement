package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;

/**
 * Tests for JtsBinaryCodec focusing on null-safety and precision model configuration.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test8 extends JtsBinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that writeDim throws NullPointerException when the DataOutput is null.
     * The codec is configured with FLOATING_SINGLE precision, which causes writeDim
     * to call dataOutput.writeFloat() — dereferencing null and triggering the NPE.
     */
    @Test(timeout = 4000)
    public void test_writeDim_throwsNullPointerException_whenDataOutputIsNull() throws Throwable {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        JtsSpatialContext context = factory.newSpatialContext();
        JtsBinaryCodec codec = new JtsBinaryCodec(context, factory);

        // Undeclared exception!
        try {
            codec.writeDim(/* dataOutput= */ null, /* value= */ 0);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.locationtech.spatial4j.io.jts.JtsBinaryCodec", e);
        }
    }
}
