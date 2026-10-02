package org.locationtech.spatial4j.io;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testDecodePreciseLongitudeLatitude {

    private static final double LATITUDE  = 52.3738007;
    private static final double LONGITUDE =  4.8909347;
    private static final double PRECISION_DELTA = 0.00001;

    SpatialContext ctx = SpatialContext.GEO;

    /**
     * Verifies that encoding a (latitude, longitude) pair to a geohash and
     * decoding it back reproduces both coordinates within {@value #PRECISION_DELTA} degrees.
     */
    @Test
    public void testDecodePreciseLongitudeLatitude() {
        String geohash = GeohashUtils.encodeLatLon(LATITUDE, LONGITUDE);
        Point decodedPoint = GeohashUtils.decode(geohash, ctx);

        assertEquals("decoded latitude should match original within delta",
                LATITUDE, decodedPoint.getY(), PRECISION_DELTA);
        assertEquals("decoded longitude should match original within delta",
                LONGITUDE, decodedPoint.getX(), PRECISION_DELTA);
    }
}
