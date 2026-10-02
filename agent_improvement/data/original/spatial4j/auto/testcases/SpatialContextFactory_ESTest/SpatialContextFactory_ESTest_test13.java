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
public class SpatialContextFactory_ESTest_test13 extends SpatialContextFactory_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        HashMap<String, String> hashMap0 = new HashMap<String, String>();
        hashMap0.put("distCalculator", "vincentysphere");
        SpatialContext spatialContext0 = SpatialContextFactory.makeSpatialContext(hashMap0, (ClassLoader) null);
        assertTrue(spatialContext0.isGeo());
    }
}
