package org.locationtech.spatial4j.io;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Verifies that encoding a precise latitude/longitude to a geohash and decoding
 * it back recovers the original coordinates within an acceptable tolerance.
 */
public class TestGeohashUtils_testDecodePreciseLongitudeLatitude {

    /** Geographic (lat/lon) spatial context used to build decoded points. */
    private final SpatialContext geoContext = SpatialContext.GEO;

    /** Coordinates of a real-world location (Amsterdam) used as the round-trip input. */
    private static final double ORIGINAL_LATITUDE = 52.3738007;
    private static final double ORIGINAL_LONGITUDE = 4.8909347;

    /** Maximum allowed difference between the original and decoded coordinates. */
    private static final double COORDINATE_TOLERANCE = 0.00001D;

    /**
     * Pass condition: lat=52.3738007, lng=4.8909347 should be encoded and then
     * decoded within 0.00001 of the original value.
     */
    @Test
    public void testDecodePreciseLongitudeLatitude() {
        String geohash = GeohashUtils.encodeLatLon(ORIGINAL_LATITUDE, ORIGINAL_LONGITUDE);
        Point decodedPoint = GeohashUtils.decode(geohash, geoContext);

        // Point.getY() is latitude, Point.getX() is longitude.
        assertEquals(ORIGINAL_LATITUDE, decodedPoint.getY(), COORDINATE_TOLERANCE);
        assertEquals(ORIGINAL_LONGITUDE, decodedPoint.getX(), COORDINATE_TOLERANCE);
    }
}
