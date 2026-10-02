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
public class SpatialContextFactory_ESTest_test04 extends SpatialContextFactory_ESTest_scaffolding {

    private static final boolean DEFAULT_NORM_WRAP_LONGITUDE = false;
    private static final boolean DEFAULT_GEO = true;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        HashMap<String, String> emptyFactoryConfiguration = new HashMap<String, String>();
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader parentClassLoader = systemClassLoader.getParent();

        SpatialContext context = SpatialContextFactory.makeSpatialContext(
                emptyFactoryConfiguration,
                parentClassLoader);

        assertEquals(DEFAULT_NORM_WRAP_LONGITUDE, context.isNormWrapLongitude());
        assertEquals(DEFAULT_GEO, context.isGeo());
    }
}
