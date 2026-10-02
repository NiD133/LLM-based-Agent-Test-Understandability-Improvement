package org.locationtech.spatial4j.io;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGeohashUtils_testHashLenToWidth {

    private static final int LAT_HEIGHT_INDEX = 0;
    private static final int LON_WIDTH_INDEX = 1;
    private static final double ACCEPTABLE_ERROR = 0.0001;

    /**
     * Verifies the degree height and width values from the geohash precision table.
     */
    @Test
    public void testHashLenToWidth() {
        double[] threeCharacterHashSize = GeohashUtils.lookupDegreesSizeForHashLen(3);
        assertEquals(1.40625, threeCharacterHashSize[LAT_HEIGHT_INDEX], ACCEPTABLE_ERROR);
        assertEquals(1.40625, threeCharacterHashSize[LON_WIDTH_INDEX], ACCEPTABLE_ERROR);

        double[] fourCharacterHashSize = GeohashUtils.lookupDegreesSizeForHashLen(4);
        assertEquals(0.1757, fourCharacterHashSize[LAT_HEIGHT_INDEX], ACCEPTABLE_ERROR);
        assertEquals(0.3515, fourCharacterHashSize[LON_WIDTH_INDEX], ACCEPTABLE_ERROR);
    }
}
