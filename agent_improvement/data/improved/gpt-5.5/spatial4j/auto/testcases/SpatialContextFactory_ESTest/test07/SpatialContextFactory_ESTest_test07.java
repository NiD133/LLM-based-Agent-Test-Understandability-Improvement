package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.impl.ShapeFactoryImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test07 extends SpatialContextFactory_ESTest_scaffolding {

    private static final String WRITERS_CONFIG_KEY = "writers";
    private static final String EMPTY_WRITER_LIST = ",";

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        HashMap<String, String> spatialContextArgs = new HashMap<String, String>();
        spatialContextArgs.put(WRITERS_CONFIG_KEY, EMPTY_WRITER_LIST);

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(spatialContextArgs, systemClassLoader);

        assertFalse(spatialContext.isNormWrapLongitude());
        assertTrue(spatialContext.isGeo());
    }
}
