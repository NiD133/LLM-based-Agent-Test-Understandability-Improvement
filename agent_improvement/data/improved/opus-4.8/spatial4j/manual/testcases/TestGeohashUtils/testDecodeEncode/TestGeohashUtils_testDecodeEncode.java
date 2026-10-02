package org.locationtech.spatial4j.io;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Verifies that {@link GeohashUtils} encoding and decoding round-trip
 * consistently, i.e. encoding a coordinate then decoding the resulting geohash
 * yields (approximately) the same coordinate, and vice versa.
 *
 * <p>See https://issues.apache.org/jira/browse/LUCENE-1815 for details.
 */
public class TestGeohashUtils_testDecodeEncode {

    /** Geographic context used to build points/rectangles during decoding. */
    private final SpatialContext geoContext = SpatialContext.GEO;

    /** Tolerance for comparing latitude/longitude values in degrees. */
    private static final double COORDINATE_TOLERANCE = 0.000001d;

    @Test
    public void testDecodeEncode() {
        // A full-precision (12 char) geohash for a known location near Amsterdam.
        final String fullPrecisionGeohash = "u173zq37x014";
        final double amsterdamLatitude = 52.3738007;
        final double amsterdamLongitude = 4.8909347;

        // Encoding the coordinate must reproduce the expected geohash.
        assertEquals(fullPrecisionGeohash,
                GeohashUtils.encodeLatLon(amsterdamLatitude, amsterdamLongitude));

        // Decoding that geohash must return a point close to the original coordinate.
        // Note: GeohashUtils maps latitude to Y and longitude to X.
        Point decodedPoint = GeohashUtils.decode(fullPrecisionGeohash, geoContext);
        assertEquals(52.37380061d, decodedPoint.getY(), COORDINATE_TOLERANCE);
        assertEquals(4.8909343d, decodedPoint.getX(), COORDINATE_TOLERANCE);

        // Re-encoding the decoded point must yield the same geohash (round-trip).
        assertEquals(fullPrecisionGeohash,
                GeohashUtils.encodeLatLon(decodedPoint.getY(), decodedPoint.getX()));

        // For a lower-precision geohash, decode -> encode -> decode must converge:
        // the second decode must produce the same point as the first.
        final String lowPrecisionGeohash = "u173";
        Point firstDecode = GeohashUtils.decode(lowPrecisionGeohash, geoContext);
        String reEncodedGeohash =
                GeohashUtils.encodeLatLon(firstDecode.getY(), firstDecode.getX());
        Point secondDecode = GeohashUtils.decode(reEncodedGeohash, geoContext);

        assertEquals(firstDecode.getY(), secondDecode.getY(), COORDINATE_TOLERANCE);
        assertEquals(firstDecode.getX(), secondDecode.getX(), COORDINATE_TOLERANCE);
    }
}
