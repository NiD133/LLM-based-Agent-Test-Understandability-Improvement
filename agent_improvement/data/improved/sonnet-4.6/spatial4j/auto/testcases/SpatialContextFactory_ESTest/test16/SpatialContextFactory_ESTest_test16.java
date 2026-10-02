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
public class SpatialContextFactory_ESTest_test16 extends SpatialContextFactory_ESTest_scaffolding {

    /**
     * Verifies that specifying the "cartesian^2" distance calculator does not override
     * the default geo=true setting, so the resulting SpatialContext still reports isGeo()==true.
     */
    @Test(timeout = 4000)
    public void test_cartesianSquaredDistCalc_doesNotDisableGeoFlag() throws Throwable {
        HashMap<String, String> args = new HashMap<String, String>();
        args.put("distCalculator", "cartesian^2");

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        SpatialContext spatialContext = SpatialContextFactory.makeSpatialContext(args, classLoader);

        assertTrue("SpatialContext should remain geo=true when only distCalculator is set",
                spatialContext.isGeo());
    }
}
