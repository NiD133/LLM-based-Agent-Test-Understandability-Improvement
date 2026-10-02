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

    // Geohash uses base-32 encoding, so each cell has exactly 32 sub-cells one level deeper.
    private static final int BASE_32_ALPHABET_SIZE = 32;

    @Test(timeout = 4000)
    public void test_getSubGeohashes_returnsOneChildPerBase32Character() throws Throwable {
        String parentGeohash = "7zzzzzzzzzzz";

        String[] subGeohashes = GeohashUtils.getSubGeohashes(parentGeohash);

        assertEquals(BASE_32_ALPHABET_SIZE, subGeohashes.length);
    }
}
