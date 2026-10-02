package org.locationtech.spatial4j.io;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Verifies {@link GeohashUtils#lookupDegreesSizeForHashLen(int)}, which maps a
 * geohash length to the size in degrees of the cell it represents.
 *
 * <p>The returned array is [latitudeHeight, longitudeWidth]. Because geohash
 * characters alternately refine longitude and latitude, cells at odd lengths are
 * square while cells at even lengths are wider than they are tall. Expected values
 * come from the reference table at http://en.wikipedia.org/wiki/Geohash.
 */
public class TestGeohashUtils_testHashLenToWidth {

    /** Index of the latitude height within the array returned by the lookup. */
    private static final int LAT_HEIGHT = 0;
    /** Index of the longitude width within the array returned by the lookup. */
    private static final int LON_WIDTH = 1;

    /** Tolerance for comparing the degree measurements. */
    private static final double TOLERANCE = 0.0001;

    SpatialContext ctx = SpatialContext.GEO;

    @Test
    public void testHashLenToWidth() {
        // Odd length (3): cell is square, so height and width are equal.
        double[] oddLengthCell = GeohashUtils.lookupDegreesSizeForHashLen(3);
        assertEquals(1.40625, oddLengthCell[LAT_HEIGHT], TOLERANCE);
        assertEquals(1.40625, oddLengthCell[LON_WIDTH], TOLERANCE);

        // Even length (4): cell is wider than it is tall.
        double[] evenLengthCell = GeohashUtils.lookupDegreesSizeForHashLen(4);
        assertEquals(0.1757, evenLengthCell[LAT_HEIGHT], TOLERANCE);
        assertEquals(0.3515, evenLengthCell[LON_WIDTH], TOLERANCE);
    }
}
