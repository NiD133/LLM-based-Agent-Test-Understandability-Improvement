package org.locationtech.spatial4j.io;

import org.junit.Test;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;

import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testDecodeImpreciseLongitudeLatitude {

    private static final SpatialContext GEO_CONTEXT = SpatialContext.GEO;
    private static final double ORIGINAL_LATITUDE = 84.6;
    private static final double ORIGINAL_LONGITUDE = 10.5;
    private static final double COORDINATE_TOLERANCE = 0.00001D;

    @Test
    public void testDecodeImpreciseLongitudeLatitude() {
        String geohash = GeohashUtils.encodeLatLon(ORIGINAL_LATITUDE, ORIGINAL_LONGITUDE);

        Point decodedPoint = GeohashUtils.decode(geohash, GEO_CONTEXT);

        assertDecodedPointMatchesOriginalCoordinates(decodedPoint);
    }

    private void assertDecodedPointMatchesOriginalCoordinates(Point decodedPoint) {
        assertEquals(ORIGINAL_LATITUDE, decodedPoint.getY(), COORDINATE_TOLERANCE);
        assertEquals(ORIGINAL_LONGITUDE, decodedPoint.getX(), COORDINATE_TOLERANCE);
    }
}
