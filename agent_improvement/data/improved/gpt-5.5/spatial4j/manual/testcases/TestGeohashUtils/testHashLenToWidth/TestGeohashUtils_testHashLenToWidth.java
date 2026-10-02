package org.locationtech.spatial4j.io;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testHashLenToWidth {

    private static final double DELTA = 0.0001;
    private static final int LAT_HEIGHT_INDEX = 0;
    private static final int LON_WIDTH_INDEX = 1;

    /**
     * See the table at http://en.wikipedia.org/wiki/Geohash.
     */
    @Test
    public void testHashLenToWidth() {
        assertHashLengthSize(3, 1.40625, 1.40625);
        assertHashLengthSize(4, 0.1757, 0.3515);
    }

    private void assertHashLengthSize(int hashLength, double expectedLatHeight, double expectedLonWidth) {
        double[] sizeInDegrees = GeohashUtils.lookupDegreesSizeForHashLen(hashLength);

        assertEquals(expectedLatHeight, sizeInDegrees[LAT_HEIGHT_INDEX], DELTA);
        assertEquals(expectedLonWidth, sizeInDegrees[LON_WIDTH_INDEX], DELTA);
    }
}
