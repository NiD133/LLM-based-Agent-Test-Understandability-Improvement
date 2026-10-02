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
public class GeohashUtils_ESTest_test3 extends GeohashUtils_ESTest_scaffolding {

    // Geohash string containing a non-standard character (';') and an uppercase letter ('Z'),
    // exercising the decoder's character-normalisation path.
    private static final String GEOHASH_WITH_NON_STANDARD_CHARS = "dQ;Z";

    private static final double EXPECTED_LONGITUDE = -74.70703125;
    private static final double EXPECTED_LATITUDE  =  37.880859375;
    private static final double COORDINATE_DELTA   =   0.01;

    @Test(timeout = 4000)
    public void test_decodeGeohashWithNonStandardChars_returnsExpectedCoordinates() throws Throwable {
        SpatialContext geoContext = SpatialContext.GEO;

        // Decode a geohash that includes an uppercase letter and a non-base32 character
        Point decodedPoint = GeohashUtils.decode(GEOHASH_WITH_NON_STANDARD_CHARS, geoContext);

        assertEquals(EXPECTED_LONGITUDE, decodedPoint.getLon(), COORDINATE_DELTA);
        assertEquals(EXPECTED_LATITUDE,  decodedPoint.getLat(), COORDINATE_DELTA);
    }
}
