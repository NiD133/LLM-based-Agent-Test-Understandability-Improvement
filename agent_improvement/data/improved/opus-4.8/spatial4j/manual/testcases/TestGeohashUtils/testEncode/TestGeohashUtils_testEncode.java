package org.locationtech.spatial4j.io;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Verifies that {@link GeohashUtils#encodeLatLon(double, double)} produces the
 * expected 12-character geohash for known latitude/longitude pairs.
 */
public class TestGeohashUtils_testEncode {

    @Test
    public void encodeLatLon_returnsExpectedGeohash_forKnownPoints() {
        // Point in north-western Spain (lat 42.6, lon -5.6).
        assertEquals("ezs42e44yx96", GeohashUtils.encodeLatLon(42.6, -5.6));

        // Point in Denmark (lat 57.64911, lon 10.40744).
        assertEquals("u4pruydqqvj8", GeohashUtils.encodeLatLon(57.64911, 10.40744));
    }
}
