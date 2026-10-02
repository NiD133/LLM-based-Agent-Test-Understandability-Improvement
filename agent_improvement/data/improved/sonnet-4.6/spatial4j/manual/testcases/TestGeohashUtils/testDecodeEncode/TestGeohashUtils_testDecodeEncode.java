package org.locationtech.spatial4j.io;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests for GeohashUtils encode/decode round-trip correctness.
 * See https://issues.apache.org/jira/browse/LUCENE-1815 for details.
 */
public class TestGeohashUtils_testDecodeEncode {

    private static final double COORDINATE_DELTA = 0.000001d;

    private final SpatialContext ctx = SpatialContext.GEO;

    /**
     * Verifies that a known 12-character geohash encodes and decodes correctly:
     * - encodeLatLon produces the expected hash string
     * - decode recovers coordinates within tolerance
     * - re-encoding the decoded point produces the same hash
     */
    @Test
    public void testKnownGeohashEncodeAndDecode() {
        String expectedHash = "u173zq37x014";
        double originalLat = 52.3738007;
        double originalLon = 4.8909347;

        assertEquals(expectedHash, GeohashUtils.encodeLatLon(originalLat, originalLon));

        Point decodedPoint = GeohashUtils.decode(expectedHash, ctx);
        assertEquals(52.37380061d, decodedPoint.getY(), COORDINATE_DELTA);
        assertEquals(4.8909343d, decodedPoint.getX(), COORDINATE_DELTA);

        assertEquals(expectedHash, GeohashUtils.encodeLatLon(decodedPoint.getY(), decodedPoint.getX()));
    }

    /**
     * Verifies round-trip consistency for a short (4-character) geohash:
     * decoding "u173" and re-encoding the result should decode to the same coordinates.
     */
    @Test
    public void testShortGeohashRoundTrip() {
        Point firstDecoded = GeohashUtils.decode("u173", ctx);
        String reEncoded = GeohashUtils.encodeLatLon(firstDecoded.getY(), firstDecoded.getX());
        Point secondDecoded = GeohashUtils.decode(reEncoded, ctx);

        assertEquals(firstDecoded.getY(), secondDecoded.getY(), COORDINATE_DELTA);
        assertEquals(firstDecoded.getX(), secondDecoded.getX(), COORDINATE_DELTA);
    }
}
