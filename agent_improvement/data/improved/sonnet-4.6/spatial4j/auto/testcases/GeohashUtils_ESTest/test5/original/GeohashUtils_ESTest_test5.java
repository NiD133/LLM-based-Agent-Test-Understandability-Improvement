package org.locationtech.spatial4j.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GeohashUtils_ESTest_test5 extends GeohashUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        String string0 = GeohashUtils.encodeLatLon(3.1414031982421875, 3.1414031982421875);
        assertEquals("s0d1z7z7zzzz", string0);
    }
}
