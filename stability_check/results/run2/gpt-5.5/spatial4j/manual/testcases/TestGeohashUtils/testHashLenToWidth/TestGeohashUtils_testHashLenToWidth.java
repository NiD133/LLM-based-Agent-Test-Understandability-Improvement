package org.locationtech.spatial4j.io;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testHashLenToWidth {

    private static final double ACCEPTABLE_DELTA = 0.0001;
    private static final int LATITUDE_HEIGHT_INDEX = 0;
    private static final int LONGITUDE_WIDTH_INDEX = 1;

    /**
     * See the table at http://en.wikipedia.org/wiki/Geohash.
     */
    @Test
    public void testHashLenToWidth() {
        assertDegreesSizeForHashLength(3, 1.40625, 1.40625);
        assertDegreesSizeForHashLength(4, 0.1757, 0.3515);
    }

    private void assertDegreesSizeForHashLength(
            int hashLength,
            double expectedLatitudeHeight,
            double expectedLongitudeWidth) {
        double[] sizeByDimension = GeohashUtils.lookupDegreesSizeForHashLen(hashLength);

        assertEquals(expectedLatitudeHeight, sizeByDimension[LATITUDE_HEIGHT_INDEX], ACCEPTABLE_DELTA);
        assertEquals(expectedLongitudeWidth, sizeByDimension[LONGITUDE_WIDTH_INDEX], ACCEPTABLE_DELTA);
    }
}
