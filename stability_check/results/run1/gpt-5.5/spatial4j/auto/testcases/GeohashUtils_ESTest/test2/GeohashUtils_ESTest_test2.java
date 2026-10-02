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

    private static final String BASE_GEOHASH = "7zzzzzzzzzzz";
    private static final int BASE32_CHILD_GEOHASH_COUNT = 32;

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        String[] childGeohashes = GeohashUtils.getSubGeohashes(BASE_GEOHASH);

        assertEquals(BASE32_CHILD_GEOHASH_COUNT, childGeohashes.length);
    }
}
