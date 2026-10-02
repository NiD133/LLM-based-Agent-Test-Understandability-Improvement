package org.locationtech.spatial4j.io;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GeohashUtils_ESTest_test1 extends GeohashUtils_ESTest_scaffolding {

    /**
     * When the required latitude error is 0.0, no geohash length can ever produce
     * a latitude height strictly smaller than 0.0, so the lookup falls through the
     * loop and returns the maximum supported precision (MAX_PRECISION = 24).
     */
    @Test(timeout = 4000)
    public void lookupHashLenForZeroLatErrReturnsMaxPrecision() throws Throwable {
        double requiredLonWidth = 3147.26;
        double requiredLatHeight = 0.0;

        int hashLen = GeohashUtils.lookupHashLenForWidthHeight(requiredLonWidth, requiredLatHeight);

        assertEquals(GeohashUtils.MAX_PRECISION, hashLen);
        assertEquals(24, hashLen);
    }
}
