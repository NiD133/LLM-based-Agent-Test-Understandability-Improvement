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
public class GeohashUtils_ESTest_test0 extends GeohashUtils_ESTest_scaffolding {

    /**
     * A geohash of length 3 covers roughly 5°×5° (lat×lon) cells, which is the
     * shortest precision whose cell dimensions both fall below 7 degrees.
     * lookupHashLenForWidthHeight returns the minimum hash length such that
     * both the cell width and cell height are less than the given thresholds.
     */
    @Test(timeout = 4000)
    public void test_lookupHashLen_returns3_forWidthAndHeightThresholdOf7Degrees() throws Throwable {
        double lonErrorDegrees = 7;
        double latErrorDegrees = 7;

        int minimumHashLength = GeohashUtils.lookupHashLenForWidthHeight(lonErrorDegrees, latErrorDegrees);

        assertEquals(
            "Hash length 3 should be the shortest precision whose cell dimensions are both < 7 degrees",
            3,
            minimumHashLength
        );
    }
}
