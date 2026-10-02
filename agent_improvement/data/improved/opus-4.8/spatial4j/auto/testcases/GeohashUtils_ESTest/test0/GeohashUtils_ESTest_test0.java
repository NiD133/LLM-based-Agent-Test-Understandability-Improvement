package org.locationtech.spatial4j.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GeohashUtils_ESTest_test0 extends GeohashUtils_ESTest_scaffolding {

    /**
     * Verifies that lookupHashLenForWidthHeight returns the shortest geohash length
     * whose cell width and height are both strictly smaller than the requested error
     * bounds. For a tolerance of 7 degrees in both longitude and latitude, the
     * shortest such geohash length is 3.
     */
    @Test(timeout = 4000)
    public void lookupHashLenForSevenDegreeBoundsReturnsThree() throws Throwable {
        double lonErrorDegrees = 7;
        double latErrorDegrees = 7;

        int hashLength = GeohashUtils.lookupHashLenForWidthHeight(lonErrorDegrees, latErrorDegrees);

        assertEquals(3, hashLength);
    }
}
