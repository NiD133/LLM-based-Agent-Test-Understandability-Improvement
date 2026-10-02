package org.locationtech.spatial4j.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GeohashUtils_ESTest_test3 extends GeohashUtils_ESTest_scaffolding {

    /** Tolerance (in degrees) allowed when comparing decoded coordinates. */
    private static final double COORDINATE_TOLERANCE = 0.01;

    /**
     * Decoding a geohash should return the centre point of the cell it
     * represents. Here we decode the geohash "dQ;Z" in the standard
     * geographic context and verify the resulting longitude and latitude.
     */
    @Test(timeout = 4000)
    public void decodeGeohashReturnsCellCentrePoint() throws Throwable {
        SpatialContext geoContext = SpatialContext.GEO;

        Point decodedPoint = GeohashUtils.decode("dQ;Z", geoContext);

        double expectedLongitude = -74.70703125;
        double expectedLatitude = 37.880859375;
        assertEquals(expectedLongitude, decodedPoint.getLon(), COORDINATE_TOLERANCE);
        assertEquals(expectedLatitude, decodedPoint.getLat(), COORDINATE_TOLERANCE);
    }
}
