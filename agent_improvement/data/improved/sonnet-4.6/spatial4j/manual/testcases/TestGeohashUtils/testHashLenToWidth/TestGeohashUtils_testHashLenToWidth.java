package org.locationtech.spatial4j.io;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Verifies that {@link GeohashUtils#lookupDegreesSizeForHashLen(int)} returns the correct
 * latitude-height and longitude-width (in degrees) for a given geohash precision level.
 *
 * Reference table: http://en.wikipedia.org/wiki/Geohash
 */
public class TestGeohashUtils_testHashLenToWidth {

    // Acceptable floating-point rounding error for degree comparisons
    private static final double DEGREE_DELTA = 0.0001;

    // Index constants matching the layout of the array returned by lookupDegreesSizeForHashLen:
    // [0] = latitude height, [1] = longitude width
    private static final int LAT_HEIGHT_IDX = 0;
    private static final int LON_WIDTH_IDX  = 1;

    /**
     * Geohash precision levels alternate between "odd" and "even" bit-groupings, which produce
     * different aspect ratios. This test covers one odd length (3) and one even length (4) to
     * ensure both patterns are handled correctly.
     *
     * See the reference table at http://en.wikipedia.org/wiki/Geohash
     */
    @Test
    public void testHashLenToWidth() {
        // --- Odd precision (length 3): cells are square (equal lat/lon span) ---
        double[] oddPrecisionDegrees = GeohashUtils.lookupDegreesSizeForHashLen(3);
        double expectedOddLatHeight = 1.40625; // degrees of latitude spanned by a length-3 hash cell
        double expectedOddLonWidth  = 1.40625; // degrees of longitude spanned by a length-3 hash cell
        assertEquals(expectedOddLatHeight, oddPrecisionDegrees[LAT_HEIGHT_IDX], DEGREE_DELTA);
        assertEquals(expectedOddLonWidth,  oddPrecisionDegrees[LON_WIDTH_IDX],  DEGREE_DELTA);

        // --- Even precision (length 4): cells are rectangular (lon span is twice the lat span) ---
        double[] evenPrecisionDegrees = GeohashUtils.lookupDegreesSizeForHashLen(4);
        double expectedEvenLatHeight = 0.1757; // degrees of latitude spanned by a length-4 hash cell
        double expectedEvenLonWidth  = 0.3515; // degrees of longitude spanned by a length-4 hash cell
        assertEquals(expectedEvenLatHeight, evenPrecisionDegrees[LAT_HEIGHT_IDX], DEGREE_DELTA);
        assertEquals(expectedEvenLonWidth,  evenPrecisionDegrees[LON_WIDTH_IDX],  DEGREE_DELTA);
    }
}
