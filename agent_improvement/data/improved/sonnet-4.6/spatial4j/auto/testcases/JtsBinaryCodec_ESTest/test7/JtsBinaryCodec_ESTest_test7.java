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

    @Test(timeout = 4000)
    public void readDim_withNullDataInput_throwsNullPointerException() throws Throwable {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        JtsSpatialContext context = new JtsSpatialContext(factory);
        JtsBinaryCodec codec = new JtsBinaryCodec(context, factory);

        try {
            codec.readDim((DataInput) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.locationtech.spatial4j.io.BinaryCodec", e);
        }
    }
}
