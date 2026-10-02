package org.locationtech.spatial4j.io;

import org.junit.Test;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;

import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testDecodePreciseLongitudeLatitude {

    private static final double EXPECTED_LATITUDE = 52.3738007;
    private static final double EXPECTED_LONGITUDE = 4.8909347;
    private static final double COORDINATE_TOLERANCE = 0.00001D;

    private final SpatialContext spatialContext = SpatialContext.GEO;

    /**
     * Amsterdam coordinates should survive an encode/decode round trip within
     * the original tolerance used by this regression test.
     */
    @Test
    public void testDecodePreciseLongitudeLatitude() {
        String hash = GeohashUtils.encodeLatLon(EXPECTED_LATITUDE, EXPECTED_LONGITUDE);

        Point decodedPoint = GeohashUtils.decode(hash, spatialContext);

        assertEquals("decoded latitude", EXPECTED_LATITUDE, decodedPoint.getY(), COORDINATE_TOLERANCE);
        assertEquals("decoded longitude", EXPECTED_LONGITUDE, decodedPoint.getX(), COORDINATE_TOLERANCE);
    }
}
