package org.locationtech.spatial4j.io;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testEncode {

    // León, Spain — interior Iberian Peninsula coordinate
    private static final double LEON_LAT = 42.6;
    private static final double LEON_LON = -5.6;
    private static final String LEON_GEOHASH = "ezs42e44yx96";

    // Skagen, Denmark — northernmost tip of Jutland
    private static final double SKAGEN_LAT = 57.64911;
    private static final double SKAGEN_LON = 10.40744;
    private static final String SKAGEN_GEOHASH = "u4pruydqqvj8";

    @Test
    public void testEncodeLeónSpain() {
        String hash = GeohashUtils.encodeLatLon(LEON_LAT, LEON_LON);
        assertEquals(LEON_GEOHASH, hash);
    }

    @Test
    public void testEncodeSkagenDenmark() {
        String hash = GeohashUtils.encodeLatLon(SKAGEN_LAT, SKAGEN_LON);
        assertEquals(SKAGEN_GEOHASH, hash);
    }
}
