package org.locationtech.spatial4j.io;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testEncode {

    private static final double MADRID_LATITUDE = 42.6;
    private static final double MADRID_LONGITUDE = -5.6;
    private static final String MADRID_GEOHASH = "ezs42e44yx96";

    private static final double AALBORG_LATITUDE = 57.64911;
    private static final double AALBORG_LONGITUDE = 10.40744;
    private static final String AALBORG_GEOHASH = "u4pruydqqvj8";

    @Test
    public void testEncode() {
        assertDefaultPrecisionEncoding(MADRID_GEOHASH, MADRID_LATITUDE, MADRID_LONGITUDE);
        assertDefaultPrecisionEncoding(AALBORG_GEOHASH, AALBORG_LATITUDE, AALBORG_LONGITUDE);
    }

    private void assertDefaultPrecisionEncoding(String expectedGeohash, double latitude, double longitude) {
        String actualGeohash = GeohashUtils.encodeLatLon(latitude, longitude);

        assertEquals(expectedGeohash, actualGeohash);
    }
}
