package org.locationtech.spatial4j.io;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Verifies that {@link GeohashUtils} performs a round-trip (encode then decode)
 * for a latitude/longitude pair without losing meaningful precision.
 */
public class TestGeohashUtils_testDecodeImpreciseLongitudeLatitude {

    /** Geographic spatial context used to build the decoded point. */
    private final SpatialContext geoContext = SpatialContext.GEO;

    /**
     * Encoding a coordinate to a geohash and decoding it back should recover the
     * original latitude and longitude to within the tolerated rounding error.
     */
    @Test
    public void testDecodeImpreciseLongitudeLatitude() {
        // The original coordinate to round-trip through the geohash codec.
        final double originalLatitude = 84.6;   // Y axis
        final double originalLongitude = 10.5;  // X axis

        // Largest acceptable difference between the original and decoded values.
        final double tolerance = 0.00001D;

        // Encode the coordinate, then decode the resulting hash back to a point.
        String geohash = GeohashUtils.encodeLatLon(originalLatitude, originalLongitude);
        Point decodedPoint = GeohashUtils.decode(geohash, geoContext);

        // The decoded point should match the original coordinate within tolerance.
        assertEquals(originalLatitude, decodedPoint.getY(), tolerance);
        assertEquals(originalLongitude, decodedPoint.getX(), tolerance);
    }
}
