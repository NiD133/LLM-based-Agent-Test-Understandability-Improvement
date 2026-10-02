package org.locationtech.spatial4j.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GeohashUtils_ESTest_test5 extends GeohashUtils_ESTest_scaffolding {

    /**
     * Encoding a latitude/longitude pair with the default precision (12 characters)
     * should produce the corresponding base-32 geohash string.
     */
    @Test(timeout = 4000)
    public void encodeLatLonWithDefaultPrecisionReturnsTwelveCharGeohash() throws Throwable {
        double latitude = 3.1414031982421875;
        double longitude = 3.1414031982421875;

        String geohash = GeohashUtils.encodeLatLon(latitude, longitude);

        assertEquals("s0d1z7z7zzzz", geohash);
    }
}
