package org.locationtech.spatial4j.io.jts;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JtsBinaryCodec_ESTest_test5 extends JtsBinaryCodec_ESTest_scaffolding {

    private static final byte JTS_GEOMETRY_TYPE = 5;

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        JtsSpatialContext spatialContext = JtsSpatialContext.GEO;
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        JtsBinaryCodec codec = new JtsBinaryCodec(spatialContext, factory);

        byte actualType = codec.typeForShape((Shape) null);

        assertEquals(JTS_GEOMETRY_TYPE, actualType);
    }
}
