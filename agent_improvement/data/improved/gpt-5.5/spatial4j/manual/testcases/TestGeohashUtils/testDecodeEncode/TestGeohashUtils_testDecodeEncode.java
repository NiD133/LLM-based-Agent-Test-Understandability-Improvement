package org.locationtech.spatial4j.io;

import org.junit.Test;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;

import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testDecodeEncode {

    private final SpatialContext ctx = SpatialContext.GEO;

    /*
     * see https://issues.apache.org/jira/browse/LUCENE-1815 for details
     */
    @Test
    public void testDecodeEncode() {
        String geoHash = "u173zq37x014";
        assertEquals(geoHash, GeohashUtils.encodeLatLon(52.3738007, 4.8909347));

        Point point = GeohashUtils.decode(geoHash, ctx);
        assertCoordinates(point, 52.37380061d, 4.8909343d);
        assertEquals(geoHash, GeohashUtils.encodeLatLon(point.getY(), point.getX()));

        geoHash = "u173";
        point = GeohashUtils.decode("u173", ctx);
        geoHash = GeohashUtils.encodeLatLon(point.getY(), point.getX());

        final Point point2 = GeohashUtils.decode(geoHash, ctx);
        assertEquals(point.getY(), point2.getY(), 0.000001d);
        assertEquals(point.getX(), point2.getX(), 0.000001d);
    }

    private void assertCoordinates(Point point, double expectedLatitude, double expectedLongitude) {
        assertEquals(expectedLatitude, point.getY(), 0.000001d);
        assertEquals(expectedLongitude, point.getX(), 0.000001d);
    }
}
