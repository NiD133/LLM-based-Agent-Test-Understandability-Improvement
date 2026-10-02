package org.locationtech.spatial4j.io;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testDecodeImpreciseLongitudeLatitude {

    private static final double LATITUDE  = 84.6;
    private static final double LONGITUDE = 10.5;
    // Geohash encode/decode is lossy; allow up to 0.00001 degrees of round-trip error.
    private static final double DECODE_DELTA = 0.00001D;

    SpatialContext ctx = SpatialContext.GEO;

    /**
     * Verifies that encoding a coordinate as a geohash and then decoding it
     * recovers the original latitude and longitude within the expected precision
     * (DECODE_DELTA degrees). Geohash is inherently imprecise because it maps
     * a continuous coordinate to a discrete hash, so a small round-trip error
     * is acceptable.
     */
    @Test
    public void testDecodeImpreciseLongitudeLatitude() {
        String geohash = GeohashUtils.encodeLatLon(LATITUDE, LONGITUDE);
        Point decodedPoint = GeohashUtils.decode(geohash, ctx);

        double decodedLatitude  = decodedPoint.getY();
        double decodedLongitude = decodedPoint.getX();

        assertEquals("Decoded latitude should match original within tolerance",
                LATITUDE, decodedLatitude, DECODE_DELTA);
        assertEquals("Decoded longitude should match original within tolerance",
                LONGITUDE, decodedLongitude, DECODE_DELTA);
    }
}
