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
public class GeohashUtils_ESTest_test2 extends GeohashUtils_ESTest_scaffolding {

    // The geohash alphabet (BASE_32) has exactly 32 characters, so every geohash
    // has exactly 32 direct children one precision level deeper.
    @Test(timeout = 4000)
    public void test_getSubGeohashes_returnsExactly32Children() throws Throwable {
        String parentGeohash = "7zzzzzzzzzzz";

        String[] childGeohashes = GeohashUtils.getSubGeohashes(parentGeohash);

        assertEquals(32, childGeohashes.length);
    }
}
