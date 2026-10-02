package org.locationtech.spatial4j.io;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Verifies that {@link GeohashUtils#lookupDegreesSizeForHashLen} returns the
 * correct geographic cell dimensions (latitude height and longitude width in
 * degrees) for both odd and even geohash lengths.
 *
 * Expected values are taken from the Wikipedia Geohash precision table:
 * http://en.wikipedia.org/wiki/Geohash
 */
public class TestGeohashUtils_testHashLenToWidth {

    /** Index of the latitude-height element in the array returned by lookupDegreesSizeForHashLen. */
    private static final int LAT_HEIGHT_IDX = 0;

    /** Index of the longitude-width element in the array returned by lookupDegreesSizeForHashLen. */
    private static final int LON_WIDTH_IDX = 1;

    /** Tolerance used for floating-point comparisons. */
    private static final double DELTA = 0.0001;

    /**
     * Odd hash lengths produce square cells (equal latitude height and longitude width).
     * Even hash lengths produce rectangular cells (longitude width is twice the latitude height).
     */
    @Test
    public void testHashLenToWidth() {
        // Hash length 3 (odd): cell should be square — both dimensions are 1.40625 degrees
        double[] oddLenDimensions = GeohashUtils.lookupDegreesSizeForHashLen(3);
        assertEquals("Latitude height for hash length 3",  1.40625, oddLenDimensions[LAT_HEIGHT_IDX], DELTA);
        assertEquals("Longitude width for hash length 3",  1.40625, oddLenDimensions[LON_WIDTH_IDX],  DELTA);

        // Hash length 4 (even): cell is rectangular — longitude width is roughly twice the latitude height
        double[] evenLenDimensions = GeohashUtils.lookupDegreesSizeForHashLen(4);
        assertEquals("Latitude height for hash length 4",  0.1757,  evenLenDimensions[LAT_HEIGHT_IDX], DELTA);
        assertEquals("Longitude width for hash length 4",  0.3515,  evenLenDimensions[LON_WIDTH_IDX],  DELTA);
    }
}
