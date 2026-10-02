package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test14 extends SpatialContextFactory_ESTest_scaffolding {

    // Verifies that normWrapLongitude defaults to false even when a distCalculator is explicitly configured
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        HashMap<String, String> config = new HashMap<String, String>();
        config.put("distCalculator", "lawofcosines");

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        SpatialContext context = SpatialContextFactory.makeSpatialContext(config, systemClassLoader);

        assertFalse(context.isNormWrapLongitude());
    }
}
