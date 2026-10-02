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

    private static final double LATITUDE = 3.1414031982421875;
    private static final double LONGITUDE = 3.1414031982421875;
    private static final String EXPECTED_DEFAULT_PRECISION_GEOHASH = "s0d1z7z7zzzz";

    @Test(timeout = 4000)
    public void testDefaultPrecisionEncodingForSameLatitudeAndLongitude() throws Throwable {
        String encodedGeohash = GeohashUtils.encodeLatLon(LATITUDE, LONGITUDE);

        assertEquals(EXPECTED_DEFAULT_PRECISION_GEOHASH, encodedGeohash);
    }
}
