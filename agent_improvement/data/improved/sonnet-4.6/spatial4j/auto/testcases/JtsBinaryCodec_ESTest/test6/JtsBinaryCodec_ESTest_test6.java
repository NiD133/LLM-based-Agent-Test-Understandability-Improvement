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

    @Test(timeout = 4000)
    public void writeShape_withNullDataOutput_throwsNullPointerException() throws Throwable {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        JtsSpatialContext geoContext = JtsSpatialContext.GEO;
        PointImpl originPoint = new PointImpl(0, 0, geoContext);
        JtsBinaryCodec codec = new JtsBinaryCodec(geoContext, factory);

        try {
            codec.writeShape((DataOutput) null, originPoint);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.locationtech.spatial4j.io.BinaryCodec", e);
        }
    }
}
